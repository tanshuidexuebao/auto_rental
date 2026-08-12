package com.coder.rental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coder.rental.entity.Dept;
import com.coder.rental.mapper.DeptMapper;
import com.coder.rental.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coder.rental.utils.DeptTreeUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author teacher_shi
 * @since 2026-08-03
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {
    @Override
    public List<Dept> selectList(Dept dept) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(dept != null && StrUtil.isNotEmpty(dept.getDeptName()),
                        "dept_name",
                        dept != null ? dept.getDeptName() : null)
                    .orderByAsc("order_num");
        // 查询匹配的部门
        List<Dept> depts = baseMapper.selectList(queryWrapper);
        // 搜索场景：将每个匹配到的部门作为根节点，附带其子孙部门构建子树，
        // 避免因父级未匹配导致整棵子树被丢弃
        if (dept != null && StrUtil.isNotEmpty(dept.getDeptName())) {
            List<Dept> result = new ArrayList<>();
            for (Dept item : depts) {
                Dept root = new Dept();
                root.setId(item.getId()).setPid(item.getPid())
                        .setDeptName(item.getDeptName()).setPhone(item.getPhone())
                        .setAddress(item.getAddress()).setParentName(item.getParentName())
                        .setOrderNum(item.getOrderNum()).setDeleted(item.getDeleted());
                root.setChildren(DeptTreeUtils.buildDeptTree(depts, item.getId()));
                result.add(root);
            }
            return result;
        }
        // 无搜索条件：构建完整的部门树
        return DeptTreeUtils.buildDeptTree(depts, 0);
    }

    @Override
    public List<Dept> selectTree() {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_num");
        List<Dept> depts = baseMapper.selectList(queryWrapper);
        Dept dept = new Dept();
        dept.setDeptName("所有部门").setId(0).setPid(-1);
        depts.add(dept);
        return DeptTreeUtils.buildDeptTree(depts, -1);
    }

    @Override
    public boolean hasChildren(Integer id) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
        return baseMapper.selectCount(queryWrapper) > 0;
    }
}
