package com.coder.rental.service;

import com.coder.rental.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author teacher_shi
 * @since 2026-08-03
 */
public interface IUserService extends IService<User> {
   User getUserByName(String name);
   List<String> selectRoleNameByUserId(Integer userId);
}
