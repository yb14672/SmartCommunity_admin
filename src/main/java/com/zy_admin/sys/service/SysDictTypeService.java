package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.util.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型表(SysDictType)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
public interface SysDictTypeService extends IService<SysDictType> {
    /**
     * 根据id列表查询
     *
     * @param dictIds
     * @return
     */
    List<SysDictType> queryDictById(ArrayList<Integer> dictIds);

    /**
     * 获取所有字典
     *
     * @return
     */
    List<SysDictType> getDictLists();

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
     * 删除
     * @param idList
     * @return
     */
    Result deleteByIdList(List<Integer> idList);

    /**
     * 查询所有字典类型
     * @return
     */
    Result selectDictAll();
}
