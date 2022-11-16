package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.SysOperLog;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志记录(SysOperLog)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysOperLogDao extends BaseMapper<SysOperLog> {
    /**
     * 新增操作日志
     * @param sysOperLog
     */
    void addOperlog(SysOperLog sysOperLog);

    /**
     * 导出选中的操作日志
     * @param operLogIds
     * @return
     */
    List<SysOperLog> getOperLogById(@Param("list") ArrayList<Integer> operLogIds);

    /**
     * 导出全部的操作日志
     * @return
     */
    List<SysOperLog> getOperLogList();
}

