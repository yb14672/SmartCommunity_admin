package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysJobDao;
import com.zy_admin.sys.entity.SysJob;
import com.zy_admin.sys.service.SysJobService;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度表(SysJob)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@Service("sysJobService")
public class SysJobServiceImpl extends ServiceImpl<SysJobDao, SysJob> implements SysJobService {

}

