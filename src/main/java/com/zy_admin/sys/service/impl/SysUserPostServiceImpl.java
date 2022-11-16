package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysUserPostDao;
import com.zy_admin.sys.entity.SysUserPost;
import com.zy_admin.sys.service.SysUserPostService;
import org.springframework.stereotype.Service;

/**
 * 用户与岗位关联表(SysUserPost)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Service("sysUserPostService")
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostDao, SysUserPost> implements SysUserPostService {

}

