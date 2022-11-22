package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.common.core.Result.Result;

import java.util.ArrayList;
import java.util.List;
/**
 * 字典类型表(SysDictType)表服务接口
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
public interface SysDictTypeService extends IService<SysDictType> {
    /**
     * 根据id列表查询字典类型
     * @param dictIds 字典类型的主键集合
     * @return 字典类型的集合
     */
    List<SysDictType> queryDictById(ArrayList<Integer> dictIds);
    /**
     * 获取所有字典类型信息
     * @return
     */
    List<SysDictType> getDictLists();
    /**
     * 分页查询字典类型
     * @param sysDictType 字典类型对象
     * @param pageable 分页对象
     * @param startTime 开始时间对象
     * @param endTime 结束时间对象
     * @return 字典类型信息结果集
     */
    Result selectDictByLimit(SysDictType sysDictType, Pageable pageable, String startTime, String endTime);
    /**
     * 查詢所有字典
     * @return
     */
    Result selectDictAll();
    /**
     * 新增字典
     * @param sysDictType 字典类型对象
     * @return 新增的字典类型结果集
     */
    Result insertOrUpdateBatch(SysDictType sysDictType);
    /**
     * 修改字典类型
     * @param sysDictType 字典类型对象
     * @return 修改的字典类型结果集
     */
    Result updateDict(SysDictType sysDictType);
    /**
     * 删除字典类型
     * @param idList 字典类型的主键数组
     * @return 删除的字典类型结果集
     */
    Result deleteByIdList(List<Integer> idList);
    /**
     * 通过主键查询单条数据
     * @param id 字典类型的主键
     * @return 单条数据结果集
     */
    Result getDictTypeById(String id);
}

