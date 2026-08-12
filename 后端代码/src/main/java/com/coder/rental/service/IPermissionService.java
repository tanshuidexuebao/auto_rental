package com.coder.rental.service;

import com.coder.rental.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author teacher_shi
 * @since 2026-08-03
 */
public interface IPermissionService extends IService<Permission> {
    List<Permission> selectPermissionListByUserId(Integer userId);

}
