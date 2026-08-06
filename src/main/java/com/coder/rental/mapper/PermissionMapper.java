package com.coder.rental.mapper;

import com.coder.rental.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author teacher_shi
 * @since 2026-08-03
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectPermissionListByUserId(Integer userId);
}
