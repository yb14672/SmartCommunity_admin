package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.dto.LoginInForExcelDto;
import com.zy_admin.sys.entity.SysLogininfor;
import com.zy_admin.util.Result;

import com.zy_admin.common.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统访问记录(SysLogininfor)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
public interface SysLogininforService extends IService<SysLogininfor> {

    /**
     * 查询登录日志
     * @param sysLogininfor
     * @param pageable
     * @return
     */
    Result queryLoginInfor(SysLogininfor sysLogininfor, Pageable pageable, String startTime, String endTime);

    /**
     * 删除日志
     * @param ids
     * @return
     */
    Result deleteByIds(int[] ids);

    /**
     * 清空日志
     * @return
     */
    Result EmptyLogininfor();

    /**
     * 查询导出数据
     * @param infoIds
     * @return
     */
    List<LoginInForExcelDto> queryLogininfor(ArrayList<Integer> infoIds);
}

