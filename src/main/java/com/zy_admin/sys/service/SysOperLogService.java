package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.SysOperLogDto;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.util.Result;

/**
 * 操作日志记录(SysOperLog)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysOperLogService extends IService<SysOperLog> {

    Result getOperLogList(SysOperLog sysOperLog, Pageable pageable,String startTime, String endTime);

}

