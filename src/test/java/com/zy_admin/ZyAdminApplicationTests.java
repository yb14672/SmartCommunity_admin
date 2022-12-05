package com.zy_admin;

import com.zy_admin.community.dao.ZyOwnerParkDao;
import com.zy_admin.community.dao.ZyOwnerRoomDao;
import com.zy_admin.community.dto.OwnerRoomDto;
import com.zy_admin.community.dto.ZyOwnerParkRecordDto;
import com.zy_admin.util.SnowflakeManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class ZyAdminApplicationTests {

    @Resource
    ZyOwnerParkDao ownerRoomDao;


    @Test
    void contextLoads() {
        List<ZyOwnerParkRecordDto> zyOwnerParkRecordDtos = ownerRoomDao.queryOwnerParkRecordByOwnerId("1709701013516193802");
        System.out.println(zyOwnerParkRecordDtos);
    }


}
