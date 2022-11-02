package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysPostDao;
import com.zy_admin.sys.entity.SysPost;
import com.zy_admin.sys.service.SysPostService;
import org.springframework.stereotype.Service;

/**
 * 岗位信息表(SysPost)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysPostService")
public class SysPostServiceImpl extends ServiceImpl<SysPostDao, SysPost> implements SysPostService {

}

