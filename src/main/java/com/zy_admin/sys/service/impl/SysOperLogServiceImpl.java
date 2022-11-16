package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysOperLogDao;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.service.SysOperLogService;
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

