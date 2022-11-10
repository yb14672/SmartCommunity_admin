package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysDeptDao;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.service.SysDeptService;
import com.zy_admin.util.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门表(SysDept)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDept> implements SysDeptService {

    @Override
    public Result selectAllSysDept() {
        return (Result) this.baseMapper.selectAllSysDept();
    }

//    删除多个
    @Override
    public Result deleteByIdList(List<Integer> idList) {
        Result result = new Result();


        return null;
    }
}

