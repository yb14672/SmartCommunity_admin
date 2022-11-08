package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysRoleMenuDao;
import com.zy_admin.sys.entity.SysRoleMenu;
import com.zy_admin.sys.service.SysRoleMenuService;
import com.zy_admin.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {
    @Resource
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public Result insertRoleMenuByRoleId(List<SysRoleMenu> sysRoleMenus, int id) {

//      sysRoleMenuDao.insert(sysRoleMenus,id)

        return  null;
    }

}

