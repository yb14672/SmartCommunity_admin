package com.zy_admin;

import com.zy_admin.community.dao.ZyCommunityDao;
import com.zy_admin.community.dao.ZyOwnerRoomDao;
import com.zy_admin.community.dto.OwnerRoomDto;
import com.zy_admin.community.entity.ZyCommunity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class ZyAdminApplicationTests {

    @Resource
    ZyOwnerRoomDao ownerRoomDao;

    @Test
    void contextLoads() {
        List<OwnerRoomDto> ownerRoomDtos = ownerRoomDao.selectOwnerRoomByOwnerId("1708134700616388618");
        System.out.println(ownerRoomDtos);
    }

}
