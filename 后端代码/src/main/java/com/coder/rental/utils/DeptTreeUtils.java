package com.coder.rental.utils;

import com.coder.rental.entity.Dept;

import java.util.ArrayList;
import java.util.List;

public class DeptTreeUtils {
    public static List<Dept> buildDeptTree(List<Dept> deptList,
                                           int pid) {
        List<Dept> deptTree = new ArrayList<>();
        deptList.stream()
                .filter(dept -> dept.getPid() == pid)
                .forEach(dept -> {
                    dept.setChildren(buildDeptTree(deptList, dept.getId()));
                    deptTree.add(dept);
                });
        return deptTree;
    }
}
