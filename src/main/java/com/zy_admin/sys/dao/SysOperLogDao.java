package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.SysOperLog;

/**
 * 操作日志记录(SysOperLog)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysOperLogDao extends BaseMapper<SysOperLog> {
    void addOperlog(SysOperLog sysOperLog);
}

