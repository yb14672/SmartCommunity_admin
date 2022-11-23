package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.common.core.Result.Result;
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
     * 分页查询所有操作日志数据
     * @param sysOperLog 查询操作日志对象
     * @param pageable 分页对象
     * @param startTime 开始时间对象
     * @param endTime 结束时间对象
     * @param orderByColumn 按列排序对象
     * @param isAsc 正序排序对象
     * @return 查询的操作日志结果集
     */
    Result getOperLogList(SysOperLog sysOperLog, Pageable pageable, String startTime, String endTime, String orderByColumn, String isAsc);
    /**
     * 删除操作日志
     * @param logIds 操作日志主键
     * @return 删除结果
     */
    Result deleteById(List<Integer> logIds);
    /**
     * 删除操作日志
     * @return 删除操作日志结果集
     */
    Result deleteLogs();
    /**
     * 新增操作日志
     * @param sysOperLog 操作日志对象
     */
    void addOperlog(SysOperLog sysOperLog);
    /**
     * 选中导出操作日志
     * @param operLogIds 操作日志的主键数组
     * @return 导出操作日志结果集
     */
    List<SysOperLog> getOperLogById(@Param("list") ArrayList<Integer> operLogIds);
    /**
     * 导出所有操作日志
     * @return 导出操作日志结果集
     */
    List<SysOperLog> getOperLogList();
}

