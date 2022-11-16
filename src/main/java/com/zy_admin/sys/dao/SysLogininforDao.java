package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.dto.LoginInForExcelDto;
import com.zy_admin.sys.entity.SysLogininfor;
import org.apache.ibatis.annotations.Param;

import com.zy_admin.common.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统访问记录(SysLogininfor)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
public interface SysLogininforDao extends BaseMapper<SysLogininfor> {

    /**
     * 分页查询加条件搜索
     * @param sysLogininfor
     * @param pageable
     * @return
     */
    List<SysLogininfor> queryLogininfor(@Param("loginInFor") SysLogininfor sysLogininfor,@Param("pageable") Pageable pageable,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 统计总数据量
     * @param sysLogininfor
     * @param startTime
     * @param endTime
     * @return
     */
    Long count(@Param("loginInFor") SysLogininfor sysLogininfor,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 刪除日志
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") int[] ids);

    /**
     * 清空日志
     * @return
     */
    int EmptyLog();

    /**
     * 查询导出日志
     * @param infoIds
     * @return
     */
    List<LoginInForExcelDto> queryLoginInFor(@Param("list") ArrayList<Integer> infoIds);
}

