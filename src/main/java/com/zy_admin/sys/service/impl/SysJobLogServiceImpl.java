package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysJobLogDao;
import com.zy_admin.sys.entity.SysJobLog;
import com.zy_admin.sys.service.SysJobLogService;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度日志表(SysJobLog)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@Service("sysJobLogService")
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogDao, SysJobLog> implements SysJobLogService {

}

