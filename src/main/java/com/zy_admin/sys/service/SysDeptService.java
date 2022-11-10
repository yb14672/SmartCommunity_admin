package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.util.Result;

import java.util.List;

/**
 * 部门表(SysDept)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
public interface SysDeptService extends IService<SysDept> {
    Result selectAllSysDept();

//    删除
    Result deleteByIdList(List<Integer> idList);
}

