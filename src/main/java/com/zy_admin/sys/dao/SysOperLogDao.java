package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
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
    *
    * @param sysOperLog
    * @return
    */
   long count(@Param("sysOperLog") SysOperLog sysOperLog,@Param("startTime") String startTime,@Param("endTime") String endTime );

    /**
     * 条件搜索加分页
     * @param sysOperLog
     * @param pageable
     * @return
     */
    List<SysOperLog> queryAllByLimit(@Param("sysOperLog") SysOperLog sysOperLog, @Param("pageable") Pageable pageable, @Param("startTime") String startTime, @Param("endTime") String endTime);

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
     * 根据id删除日志
     * @param logids
     */
    void deleteOperLogById(@Param("logids") List<Integer> logids);

    /**
     * 删除所有日志
     * @return
     */
    int deleteLogs();

    /**
     * 导出全部的操作日志
     * @return
     */
    List<SysOperLog> getOperLogList();

}

