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
  *  查询总数据
  * @param sysOperLog 系统操作日志对象
  * @param startTime  开始时间
  * @param endTime    结束时间
  * @return 数据总条数
  */
 long count(@Param("sysOperLog") SysOperLog sysOperLog,@Param("startTime") String startTime,@Param("endTime") String endTime );
 /**
  * 分页查询数据
  * @param sysOperLog 系统日志操作日志
  * @param pageable   分页对象
  * @param startTime  开始时间
  * @param endTime    结束时间
  * @return {@code List<SysOperLog>}
  */
 List<SysOperLog> queryAllByLimit(@Param("sysOperLog") SysOperLog sysOperLog, @Param("pageable") Pageable pageable, @Param("startTime") String startTime, @Param("endTime") String endTime);

 /**
  * 新增操作日志
  * @param sysOperLog 系统操作日志对象
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
     * @param logids 操作日志id
     */
    void deleteOperLogById(@Param("logids") List<Integer> logids);
    /**
     * 删除所有日志
     * @return 日志条数
     */
    int deleteLogs();
    /**
     * 导出全部的操作日志
     * @return 操作日志集合
     */
    List<SysOperLog> getOperLogList();

}

