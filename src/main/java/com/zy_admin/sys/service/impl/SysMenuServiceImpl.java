package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysMenuDao;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.sys.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

}

