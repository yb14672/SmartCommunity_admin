package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.util.Result;

/**
 * 字典类型表(SysDictType)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 根据ID字典类型
     * @param id
     * @return
     */
    Result getDictTypeById(String id);

    /**
     * 分页查询所有数据
     *
     * @param pageable    分页对象
     * @param sysDictType 查询实体
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 所有数据
     */
    Result selectDictByLimit(SysDictType sysDictType, Pageable pageable, String startTime, String endTime);

    /**
     * 新增字典类型
     * @param sysDictType
     * @return
     */
    Result insertOrUpdateBatch(SysDictType sysDictType);

    /**
     * 修改字典类型
     * @param sysDictType
     * @return
     */
    Result updateDict(SysDictType sysDictType);

    /**
     * 查询所有字典类型
     * @return
     */
    Result selectDictAll();
}
