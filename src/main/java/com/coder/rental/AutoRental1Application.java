package com.coder.rental;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.coder.rental.mapper")
public class AutoRental1Application {

    public static void main(String[] args) {
        SpringApplication.run(AutoRental1Application.class, args);
    }

}
