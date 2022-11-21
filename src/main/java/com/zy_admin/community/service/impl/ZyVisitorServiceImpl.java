package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyVisitorDao;
import com.zy_admin.community.entity.ZyVisitor;
import com.zy_admin.community.service.ZyVisitorService;
import org.springframework.stereotype.Service;

/**
 * 访客邀请 (ZyVisitor)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:04
 */
@Service("zyVisitorService")
public class ZyVisitorServiceImpl extends ServiceImpl<ZyVisitorDao, ZyVisitor> implements ZyVisitorService {

}

