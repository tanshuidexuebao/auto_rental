package com.coder.rental.service.impl;

import com.coder.rental.entity.Permission;
import com.coder.rental.mapper.PermissionMapper;
import com.coder.rental.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author teacher_shi
 * @since 2026-08-03
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {


    @Override
    public List<Permission> selectPermissionListByUserId(Integer userId) {
        return baseMapper.selectPermissionListByUserId(userId);
    }
}
