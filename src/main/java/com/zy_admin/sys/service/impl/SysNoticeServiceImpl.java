package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysNoticeDao;
import com.zy_admin.sys.entity.SysNotice;
import com.zy_admin.sys.service.SysNoticeService;
import org.springframework.stereotype.Service;

/**
 * 通知公告表(SysNotice)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@Service("sysNoticeService")
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeDao, SysNotice> implements SysNoticeService {

}

