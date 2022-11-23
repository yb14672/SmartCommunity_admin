package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.dto.LoginInForExcelDto;
import com.zy_admin.sys.entity.SysLogininfor;
import org.apache.ibatis.annotations.Param;

import com.zy_admin.common.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统访问记录(SysLogininfor)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@Repository
public interface SysLogininforDao extends BaseMapper<SysLogininfor> {
    /**
     * 分页查询加条件搜索
     * @param sysLogininfor 登录日志对象
     * @param pageable      分页对象
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return 登录日志集合
     */
    List<SysLogininfor> queryLogininfor(@Param("loginInFor") SysLogininfor sysLogininfor, @Param("pageable") Pageable pageable, @Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 统计总数据量
     * @param sysLogininfor 登录日志对象
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return 登录日志统计条数
     */
    Long count(@Param("loginInFor") SysLogininfor sysLogininfor, @Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 由ids刪除日志
     * @param ids 登录日志id
     * @return 删除登录日志条数
     */
    int deleteByIds(@Param("ids") int[] ids);
    /**
     * 清空日志
     * @return 清空日志条数
     */
    int EmptyLog();
    /**
     * 查询导出日志
     * @param infoIds 登录日志id
     * @return 登录日志导出集合
     */
    List<LoginInForExcelDto> queryLoginInFor(@Param("list") ArrayList<Integer> infoIds);
}

