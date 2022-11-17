package com.zy_admin;

import com.zy_admin.community.dao.ZyCommunityDao;
import com.zy_admin.community.entity.ZyCommunity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class ZyAdminApplicationTests {

    @Resource
    ZyCommunityDao communityDao;

    @Test
    void contextLoads() {
        ZyCommunity zyCommunity = communityDao.selectById(1338423709557272577L);
        System.out.println(zyCommunity);
    }

}
