package com.zy_admin.sys.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.dto.LoginInForExcelDto;
import com.zy_admin.sys.entity.SysLogininfor;
import com.zy_admin.common.core.Result.Result;

import com.zy_admin.common.Pageable;

import java.util.ArrayList;
import java.util.List;
/**
 * 系统访问记录(SysLogininfor)表服务接口
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
public interface SysLogininforService extends IService<SysLogininfor> {
    /**
     * 查询登录日志
     * @param sysLogininfor 登录日志对象
     * @param pageable 分页对象
     * @param startTime 开始时间对象
     * @param endTime 结束时间对象
     * @return 所查询的登录日志结果集
     */
    Result queryLoginInfor(SysLogininfor sysLogininfor, Pageable pageable, String startTime, String endTime);
    /**
     * 删除数据
     * @param ids 登录日志id数组
     * @return 删除登录日志结果集
     */
    Result deleteByIds(int[] ids);
    /**
     * 清空日志
     * @return 成功或失败的结果集
     */
    Result EmptyLogininfor();
    /**
     * 查询导出数据
     * @param infoIds 登录日志id的数组
     * @return 登录日志导出集合
     */
    List<LoginInForExcelDto> queryLogininfor(ArrayList<Integer> infoIds);
}

