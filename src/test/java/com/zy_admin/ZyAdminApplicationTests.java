package com.zy_admin;

import com.zy_admin.common.Pageable;
import com.zy_admin.community.dao.ZyUnitDao;
import com.zy_admin.community.dto.UnitListDto;
import com.zy_admin.community.entity.ZyUnit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class ZyAdminApplicationTests {

    @Resource
    ZyUnitDao zyUnitDao;
    @Test
    void contextLoads() {
        List<UnitListDto> unitListDtos = zyUnitDao.queryAllByLimit(new ZyUnit(), new Pageable());
        System.out.println(unitListDtos);
    }

}
