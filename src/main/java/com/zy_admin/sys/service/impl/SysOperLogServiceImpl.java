package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysOperLogDao;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.service.SysOperLogService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 操作日志记录(SysOperLog)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysOperLogService")
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogDao, SysOperLog> implements SysOperLogService {
    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param sysOperLog 查询实体
     * @return 所有数据
     */
    @Override
    public Result getOperLogs(Page page, SysOperLog sysOperLog,String startTime,String endTime) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //新建查询条件对象
        LambdaQueryWrapper<SysOperLog> queryWrapper = new LambdaQueryWrapper<>();
        //StringUtils.isNotEmpty(xxx)--当dictType不为空时，执行这个行sql
        //SysDictData::getDictType--查询哪个字段
        //sysDictData.getDictType()--查询条件
        queryWrapper.like(StringUtils.isNotEmpty(sysOperLog.getTitle()), SysOperLog::getTitle, sysOperLog.getTitle());
        queryWrapper.like(StringUtils.isNotEmpty(sysOperLog.getOperName()), SysOperLog::getOperName, sysOperLog.getOperName());
        queryWrapper.eq(StringUtils.isNotNull(sysOperLog.getBusinessType()), SysOperLog::getBusinessType, sysOperLog.getBusinessType());
        queryWrapper.eq(StringUtils.isNotNull(sysOperLog.getStatus()), SysOperLog::getStatus, sysOperLog.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(sysOperLog.getOperTime()), SysOperLog::getOperTime, startTime);
        queryWrapper.le(StringUtils.isNotEmpty(sysOperLog.getOperTime()), SysOperLog::getOperTime, endTime);
        Page page1 = this.baseMapper.selectPage(page, queryWrapper);
        if (page1.getSize() > 0) {
            result.setData(page1);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
}

