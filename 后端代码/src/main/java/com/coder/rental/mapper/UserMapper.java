package com.coder.rental.mapper;

import com.coder.rental.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author teacher_shi
 * @since 2026-08-03
 */
public interface UserMapper extends BaseMapper<User> {
    List<String> selectRoleNameByUserId(Integer userId);
}
