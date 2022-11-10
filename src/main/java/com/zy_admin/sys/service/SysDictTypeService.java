package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.util.Result;

import java.util.List;

/**
 * 字典类型表(SysDictType)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
public interface SysDictTypeService extends IService<SysDictType> {
    //    分页查询
    Result selectDictByLimit(SysDictType sysDictType, Pageable pageable, String startTime, String endTime);

//    新增
    Result insertOrUpdateBatch(SysDictType sysDictType);

//    修改
    Result updateDict(SysDictType sysDictType);

//    删除
    Result deleteByIdList(List<Integer> idList);
}

