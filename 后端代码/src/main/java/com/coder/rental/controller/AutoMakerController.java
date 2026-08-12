package com.coder.rental.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoMaker;
import com.coder.rental.service.IAutoMakerService;
import com.coder.rental.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author teacher_shi
 * @since 2026-08-03
 */
@RestController
@RequestMapping("/rental/autoMaker")
public class AutoMakerController {
    @Resource
    private IAutoMakerService autoMakerService;

    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start,
                         @PathVariable int size,
                         @RequestBody(required = false) AutoMaker autoMaker) {
        Page<AutoMaker> page = autoMakerService.search(start, size,
                autoMaker);
        return Result.success().setData(page);
    }


}
