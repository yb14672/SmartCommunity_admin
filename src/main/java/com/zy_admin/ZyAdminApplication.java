package com.zy_admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author admin
 */
@MapperScan({"com.zy_admin.sys.dao","com.zy_admin.community.dao"})
@SpringBootApplication
@EnableTransactionManagement
public class ZyAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZyAdminApplication.class, args);
    }
}
