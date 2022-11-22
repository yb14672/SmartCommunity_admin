package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.dto.DataDictExcelDto;
import com.zy_admin.sys.entity.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典数据表(SysDictData)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
public interface SysDictDataDao extends BaseMapper<SysDictData> {

    /**
     * 修改状态
     * @param dictType
     * @param status
     */
    void changeStatusByDictType(@Param("dictType") String dictType, @Param("status") String status);


    /**
     * 根据字典类型获取所有对应的字典数据
     * @param dictType
     * @return
     */
    List<SysDictData> getDict(String dictType);

    /**
     *检查数据标签是否唯一
     * @param dictData
     * @return
     */
    SysDictData checkDictLabelUnique(SysDictData dictData);

    /**
     * 检查数据键值是否唯一
     * @param dictData
     * @return
     */
    SysDictData checkDictValueUnique(SysDictData dictData);

    /**
     * 根据ID获取对应的字典数据
     * @param id
     * @return
     */
    SysDictData getDictDataById(String id);

    /**
     * 根据ID更新字典类型
     * @param sysDictData
     * @return
     */
    int updateDictDataById(SysDictData sysDictData);

    /**
     * 批量删除
     * @param idList
     * @return
     */
    int deleteDictDataByIds(@Param("idList") List<String> idList);

    /**
     * 查询所有字典数据
     * @return
     */
    List<DataDictExcelDto> getDictList(String dictType);


    /**
     * 根据ID查询字典列表
     * @param idList
     * @return
     */
    List<DataDictExcelDto> getDictListById(@Param("idList") ArrayList<Integer> idList);
}

