package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Override
    public SysUser login(SysUser user) {
        return this.baseMapper.login(user);
    }

    @Override
    public SysUser getUserById(String id){
        return this.baseMapper.getUserById(id);
    }

    @Override
    public int updateUser(SysUser user) {
        return baseMapper.updateUser(user);
    }

    @Override
    public int resetPwd(SysUser user) {
        return baseMapper.resetPwd(user);
    }
}

