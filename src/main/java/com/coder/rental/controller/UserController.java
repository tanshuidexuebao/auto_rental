package com.coder.rental.controller;

import com.coder.rental.entity.User;
import com.coder.rental.service.IUserService;
import com.coder.rental.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author teacher_shi
 * @since 2026-08-03
 */
@RestController
@RequestMapping("/rental/user")
public class UserController {
    @Resource
 private IUserService userService;
@GetMapping
    public Result<List<User>> List(){
        return Result.success(userService.list());
    }

}
