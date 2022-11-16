package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.entity.SysPost;
import org.apache.ibatis.annotations.Param;

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
    *
    * @param sysOperLog
    * @param pageable
    * @return
    */
   List<SysOperLog> queryAllByLimit(@Param("sysOperLog") SysOperLog sysOperLog, @Param("pageable") Pageable pageable,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("orderByColumn") String orderByColumn,@Param("isAsc") String isAsc);
}

