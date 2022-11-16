package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysOperLogDao;
import com.zy_admin.sys.dto.PostDto;
import com.zy_admin.sys.dto.SysOperLogDto;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.entity.SysPost;
import com.zy_admin.sys.service.SysOperLogService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志记录(SysOperLog)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysOperLogService")
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogDao, SysOperLog> implements SysOperLogService {

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
            pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<SysOperLog> sysOperLogs = this.baseMapper.queryAllByLimit(sysOperLog, pageable, startTime, endTime,orderByColumn,isAsc);
        SysOperLogDto sysOperLogDto = new SysOperLogDto(sysOperLogs,pageable,startTime,endTime,orderByColumn,isAsc);
        result.setData(sysOperLogDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;

    }
}

