package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysRoleDao;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {
    @Resource
    SysRoleDao sysRoleDao;

    @Override
    public List<SysRole> queryRoleById(ArrayList<Integer> roleIds) {
        if (roleIds != null) {
            roleIds = roleIds.size() == 0 ? null : roleIds;
        }
        return sysRoleDao.queryRoleById(roleIds);
    }

    @Override
    public List<SysRole> getRoleLists() {
        return sysRoleDao.getRoleLists();
    }
}

