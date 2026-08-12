package com.coder.rental.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
/*
* redis工具类*/
/*
Redis：
Java
 ↓
StringRedisTemplate
 ↓
Redis
*/
@Component
public class RedisUtils {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key,String value,Long outime){
        stringRedisTemplate.opsForValue().set(key,value,outime, TimeUnit.SECONDS);
    }
    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
    public void del(String key){
        stringRedisTemplate.delete(key);
    }

}
