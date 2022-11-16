package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysOperLogDao;
import com.zy_admin.sys.dto.SysOperLogDto;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.service.SysOperLogService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志记录(SysOperLog)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysOperLogService")
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogDao, SysOperLog> implements SysOperLogService {

    /**
     * 分页并查询
     * @param sysOperLog
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Result selectOperLogByLimit(SysOperLog sysOperLog, Pageable pageable, String startTime, String endTime) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //满足条件的总数据
        long total = this.baseMapper.count(sysOperLog, startTime, endTime);
        long pages = 0;
        if (total > 0) {
            //计算出总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<SysOperLog> sysOperLogList = this.baseMapper.selectOperLogByLimit(sysOperLog, pageable, startTime, endTime);
//        封装一个dto，把对象和分页放进去
        SysOperLogDto sysOperLogDto = new SysOperLogDto(sysOperLogList, startTime, endTime, pageable);
//        存到data数据里面
        result.setData(sysOperLogDto);
//        返回信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 新增操作日志
     * @param sysOperLog
     */
    @Override
    public void addOperlog(SysOperLog sysOperLog) {
        this.baseMapper.addOperlog(sysOperLog);
    }

    /**
     * 选中id导出操作日志
     * @param operLogIds
     * @return
     */
    @Override
    public List<SysOperLog> getOperLogById(ArrayList<Integer> operLogIds) {
//        判断有没有选中，没有选中就导出全部的
        if (operLogIds!=null){
            operLogIds = operLogIds.size() == 0 ? null :operLogIds;
        }
        return this.baseMapper.getOperLogById(operLogIds);
    }

    /**
     * 导出所有操作日志
     * @return
     */
    @Override
    public List<SysOperLog> getOperLogList() {
        return this.baseMapper.getOperLogList();
    }
}

