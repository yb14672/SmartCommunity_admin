package com.zy_admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zy_admin.**.dao")
@SpringBootApplication
public class ZyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZyAdminApplication.class, args);
    }

}
