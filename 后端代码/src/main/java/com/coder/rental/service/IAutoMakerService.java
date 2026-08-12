package com.coder.rental.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoMaker;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author teacher_shi
 * @since 2026-08-03
 */
public interface IAutoMakerService extends IService<AutoMaker> {

   public Page<AutoMaker> search(Integer start, Integer size, AutoMaker autoMaker);
}
