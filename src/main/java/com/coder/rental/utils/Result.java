package com.coder.rental.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.logging.LogRecord;

// 返回结果
@Data
@Accessors(chain = true)
public class Result<T>{
    private Integer code;
    private String message;

    private Boolean success;
    private T data;

    private  Result(){

    }
    /*
    操作成功的方法
    */
    public static <T> Result<T> success(){
        return new Result<T>().setSuccess(true)
                .setCode(ResultCode.SUCCESS)
                .setMessage("成功");
    }
    public static <T> Result<T> success(T data){
        return new Result<T>().setSuccess(true)
                .setCode(ResultCode.SUCCESS)
                .setMessage("成功").setData(data);
    }
    /*
    操作失败
    */
    public static <T> Result<T> fail(){
        return new Result<T>().setSuccess(false)
                .setCode(ResultCode.ERROR)
                .setMessage("失败");
    }

}
