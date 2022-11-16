package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.util.Result;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志记录(SysOperLog)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysOperLogService extends IService<SysOperLog> {

    /**
     * 分页并查询
     * @param sysOperLog
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    Result selectOperLogByLimit(SysOperLog sysOperLog, Pageable pageable, String startTime, String endTime);

    /**
     * 新增操作日志
     * @param sysOperLog
     */
    void addOperlog(SysOperLog sysOperLog);

    /**
     * 选中导出操作日志
     * @param operLogIds
     * @return
     */
    List<SysOperLog> getOperLogById(@Param("list") ArrayList<Integer> operLogIds);

    /**
     * 导出所有操作日志
     * @return
     */
    List<SysOperLog> getOperLogList();
}

