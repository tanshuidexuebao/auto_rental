package com.coder.rental.utils;
import com.coder.rental.entity.Permission;
import com.coder.rental.vo.RouteVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class RouteTreeUtils {
    /**
     * 基于权限列表和父权限ID构建路由树。
     *
     * @param permissionList 权限列表，包含所有路由的权限信息。
     * @param pid 父权限ID，用于筛选特定父级下的路由。
     * @return 返回一个路由树的列表，每个路由包含路径、名称、组件等信息，以及可能的子路
    由。
     */
    public static List<RouteVo> buildRouteTree(List<Permission>
                                                       permissionList, int pid) {
        List<RouteVo> routeVoList = new ArrayList<>();
// 从权限列表中筛选出父ID为pid的权限，并为每个权限构建一个RouteVo实例
        Optional.ofNullable(permissionList).orElse(new ArrayList<>())
                .stream()
                .filter(permission -> permission != null &&
                        permission.getPid() == pid)
                .forEach(permission -> {
                    // 递归构建该权限下的子路由树
                    List<RouteVo> children = buildRouteTree(permissionList,
                            permission.getId());
                    // 判断是否有子菜单
                    boolean hasChildren = children != null && !children.isEmpty();

                    RouteVo routeVo = new RouteVo();
                    routeVo.setPath(permission.getRoutePath());// 设置路由路径
                    routeVo.setName(permission.getRouteName());// 设置路由名称
// 判断是否为根路径菜单，并设置相应的组件和显示属性
                    if (permission.getPid() == 0 && hasChildren) {
                        // 有子菜单：作为Layout组
                        routeVo.setComponent("Layout");
                        routeVo.setAlwaysShow(true);
                    } else {
                        // 无子菜单或子菜单：直接使用路由地址
                        routeVo.setComponent(permission.getRouteUrl());
                        routeVo.setAlwaysShow(false);
                    }
// 设置路由的元信息，包括权限标签、图标和权限代码
                    routeVo.setMeta(routeVo.new
                            Meta(permission.getPermissionLable(),
                            permission.getIcon(),
                            permission.getPermissionCode().split(",")));
                    routeVo.setChildren(children);
                    routeVoList.add(routeVo);
                });
        return routeVoList;
    }
}