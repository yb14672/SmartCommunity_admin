package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.util.Result;

/**
 * 操作日志记录(SysOperLog)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysOperLogService extends IService<SysOperLog> {

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param sysOperLog 查询实体
     * @return 所有数据
     */
    Result getOperLogs(Page page, SysOperLog sysOperLog,String startTime,String endTime);
}

