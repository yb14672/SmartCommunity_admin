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
     * 字典分页查询
      * @param sysDictType
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    Result selectDictByLimit(SysDictType sysDictType, Pageable pageable, String startTime, String endTime);

    /**
     * 查詢所有字典
     * @return
     */
    Result selectDictAll();

    /**
     * 新增字典数据状态
     * @param sysDictType
     * @return
     */
    Result insertOrUpdateBatch(SysDictType sysDictType);


    /**
     * 修改字典数据状态
     * @param sysDictType
     * @return
     */
    Result updateDict(SysDictType sysDictType);

    /**
     * 字典删除
     * @param idList
     * @return
     */
    Result deleteByIdList(List<Integer> idList);

    /**
     * 根据id查询字典类型
     * @param id
     * @return
     */
    Result getDictTypeById(String id);

    /**
     * 检查前端输入的值是否和数据库相等
     * @param dictType
     * @param sysDictType
     * @return
     */
    boolean checkEquals(SysDictType dictType,SysDictType sysDictType);
}

