import { constantRoutes } from '@/router'
import { getMenuList } from '@/api/user'
import Layout from '@/layout'
import { resetRouter } from '@/router'

/**
 * 将后端返回的 RouteVo 菜单数据，递归转换为 Vue Router 路由对象
 *
 * @param {Array} routeVoList  后端返回的菜单列表，每个元素结构:
 *   {
 *     path: '/user',          // 路由路径
 *     component: 'Layout' 或 '/user/UserList',  // 组件标识
 *     name: 'UserList',       // 路由名称
 *     alwaysShow: true,       // 是否始终显示根菜单
 *     meta: { title, icon, roles },  // 元数据
 *     children: [...]         // 子菜单
 *   }
 * @returns {Array} Vue Router 路由对象数组
 */
function convertRoutes(routeVoList) {
  // 如果 children 是 null 或 undefined，说明是叶子节点（具体页面）
  // 直接返回空数组，不生成子路由
  if (!routeVoList || routeVoList.length === 0) {
    return []
  }

  return routeVoList.map(routeVo => {
    const route = {
      path: routeVo.path,
      name: routeVo.name,
      hidden: false
    }

    // 处理组件
    if (routeVo.component) {
      if (routeVo.component === 'Layout') {
        // 父级菜单：使用 Layout 布局组件
        route.component = Layout
      } else {
        // 叶子菜单：动态加载对应页面。component 值如 '/user/UserList'
        // require 会将 @/views/user/UserList 解析为实际文件路径
        route.component = (resolve) => require([`@/views${routeVo.component}`], resolve)
      }
    }

    // alwaysShow：菜单只有一个子节点时是否显示父级
    if (routeVo.alwaysShow) {
      route.alwaysShow = routeVo.alwaysShow
    }

    // 元数据
    if (routeVo.meta) {
      route.meta = {
        title: routeVo.meta.title || '',
        icon: routeVo.meta.icon || ''
      }
      // 保留 roles 用于后续权限判断
      if (routeVo.meta.roles) {
        route.meta.roles = routeVo.meta.roles
      }
    }

    // 递归处理子菜单
    if (routeVo.children && routeVo.children.length > 0) {
      route.children = convertRoutes(routeVo.children)
      route.redirect = routeVo.children[0].path // 默认重定向到第一个子菜单
    }

    return route
  })
}

const state = {
  routes: [],      // 最终路由 = 基础路由 + 动态路由
  addRoutes: []    // 动态添加的路由
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  /**
   * 从后端获取当前用户的菜单，生成动态路由
   * @param {Object} context  Vuex 上下文
   * @param {Array} roles     用户权限码列表（如 ['admin', 'editor']），保留用于兼容
   */
  generateRoutes({ commit }, roles) {
    return new Promise((resolve, reject) => {
      // 每次生成新菜单前，先清空之前动态添加的路由，防止切换账号后菜单残留
      resetRouter()

      getMenuList().then(response => {
        // response 是请求拦截器返回的 response.data
        // 结构: { success: true, code: 200, msg: '...', data: [RouteVo, ...] }
        if (response.code === 200) {
          const routeVoList = response.data       // 后端返回的菜单列表
          const accessedRoutes = convertRoutes(routeVoList)  // 转换为路由
          commit('SET_ROUTES', accessedRoutes)
          resolve(accessedRoutes)
        } else {
          reject(response.msg)
        }
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
