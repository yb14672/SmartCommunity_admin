package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysOperLogDao;
import com.zy_admin.sys.dto.SysOperLogDto;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.service.SysOperLogService;
import com.zy_admin.util.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统日志服务impl操作日志
 * 操作日志记录(SysOperLog)表服务实现类
 * @author makejava
 * @date 2022/11/22
 * @since 2022-11-01 19:49:40
 */
@Service("sysOperLogService")
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogDao, SysOperLog> implements SysOperLogService {
    /**
     * 获得打开日志操作列表
     * @param sysOperLog    日志操作对象
     * @param pageable      分页对象
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param orderByColumn 命令列
     * @param isAsc         是asc
     * @return 日志操作结果集
     */
    @Override
    public Result getOperLogList(SysOperLog sysOperLog, Pageable pageable,String startTime, String endTime,String orderByColumn,String isAsc) {
        Result result =new Result();
        long total = this.baseMapper.count(sysOperLog,startTime,endTime);
        long pages = 0;
        if (total > 0) {
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(Math.min(pageable.getPageNum(), pages));
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<SysOperLog> sysOperLogs = this.baseMapper.queryAllByLimit(sysOperLog, pageable, startTime, endTime);
        SysOperLogDto sysOperLogDto = new SysOperLogDto(sysOperLogs,pageable,startTime,endTime,orderByColumn,isAsc);
        result.setData(sysOperLogDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 通过id删除操作日志
     * @param logids 操作日志主键
     * @return 删除日志操作结果集
     */
    @Override
    public Result deleteById(List<Integer> logids) {
      Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            this.baseMapper.deleteOperLogById(logids);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }
        return result;
    }
    /**
     * 删除日志
     * @return 删除日志操作结果集
     */
    @Override
    public Result deleteLogs() {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        int i = this.baseMapper.deleteLogs();
        if (i > 0) {
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
    /**
     * 添加操作结果集
     * @param sysOperLog 操作日志对象
     */
    @Override
    public void addOperlog(SysOperLog sysOperLog) {
        this.baseMapper.addOperlog(sysOperLog);
    }
    /**
     * 打开日志通过id
     * @param operLogIds 打开日志主键
     * @return 日志操作集合
     */
    @Override
    public List<SysOperLog> getOperLogById(ArrayList<Integer> operLogIds) {
        //判断有没有选中，没有选中就导出全部的
        if (operLogIds!=null){
            operLogIds = operLogIds.size() == 0 ? null :operLogIds;
        }
        return this.baseMapper.getOperLogById(operLogIds);
    }
    /**
     * 获得打开日志列表
     * @return 日志操作集合结果集
     */
    @Override
    public List<SysOperLog> getOperLogList() {
        return this.baseMapper.getOperLogList();
    }
}

