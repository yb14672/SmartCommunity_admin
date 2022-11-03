package com.zy_admin;

import com.zy_admin.sys.dao.SysMenuDao;
import com.zy_admin.sys.entity.MenuTree;
import com.zy_admin.sys.entity.SysMenu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class ZyAdminApplicationTests {
    @Resource
    SysMenuDao sysMenuDao;
    @Test
    void contextLoads() {
        List<MenuTree> allMenu = sysMenuDao.getAllMenu();
        System.out.println(allMenu);
    }

}
