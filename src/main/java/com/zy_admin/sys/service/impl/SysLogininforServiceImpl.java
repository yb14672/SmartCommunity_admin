package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysLogininforDao;
import com.zy_admin.sys.dto.LoginInForDto;
import com.zy_admin.sys.dto.LoginInForExcelDto;
import com.zy_admin.sys.entity.SysLogininfor;
import com.zy_admin.sys.service.SysLogininforService;
import com.zy_admin.util.Result;

import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 系统访问记录(SysLogininfor)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@Service("sysLogininforService")
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforDao, SysLogininfor> implements SysLogininforService {

    /**
     * 查询导出数据
     * @param infoIds
     * @return
     */
    @Override
    public List<LoginInForExcelDto> queryLogininfor(ArrayList<Integer> infoIds) {
        return this.baseMapper.queryLoginInFor(infoIds);
    }

    /**
     * 日志分页搜索
     * @param sysLogininfor
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Result queryLoginInfor(SysLogininfor sysLogininfor, Pageable pageable, String startTime, String endTime) {
        Result result = new Result(null, ResultTool.fail(ResultCode.LOGIN_LOG_GET_FAIL));
        try {
            long total = this.baseMapper.count(sysLogininfor, startTime, endTime);
            pageable.setTotal(total);
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

            List<SysLogininfor> logininforList = this.baseMapper.queryLogininfor(sysLogininfor, pageable, startTime, endTime);
            result.setData(new LoginInForDto(logininforList, pageable, startTime, endTime));
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public Result deleteByIds(int[] ids) {
        int i = this.baseMapper.deleteByIds(ids);
        Result result = new Result(null, ResultTool.fail(ResultCode.LOG_DELETE_FAIL));
        if (i > 0) {
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        }
        return result;
    }

    @Override
    public Result EmptyLogininfor() {
        Result result = new Result(null,ResultTool.fail(ResultCode.LOG_EMPTY_FAIL));
        int i = this.baseMapper.EmptyLog();
        if (i>0){
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        }
        return result;
    }

}

