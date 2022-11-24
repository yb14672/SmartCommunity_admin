package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.dto.DataDictExcelDto;
import com.zy_admin.sys.entity.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys dict数据刀
 * 字典数据表(SysDictData)表数据库访问层
 *
 * @author makejava
 * @date 2022/11/23
 * @since 2022-11-01 19:49:34
 */
public interface SysDictDataDao extends BaseMapper<SysDictData> {

    /**
     * 按照dict类型变化状态
     * 修改状态
     *
     * @param dictType dict类型
     * @param status   状态
     */
    void changeStatusByDictType(@Param("dictType") String dictType, @Param("status") String status);


    /**
     * 得到东西
     * 根据字典类型获取所有对应的字典数据
     *
     * @param dictType dict类型
     * @return {@link List}<{@link SysDictData}>
     */
    List<SysDictData> getDict(String dictType);

    /**
     * 检查dict标签独特
     * 检查数据标签是否唯一
     *
     * @param dictData dict类型数据
     * @return {@link SysDictData}
     */
    SysDictData checkDictLabelUnique(SysDictData dictData);

    /**
     * 检查dict独特价值
     * 检查数据键值是否唯一
     *
     * @param dictData dict类型数据
     * @return {@link SysDictData}
     */
    SysDictData checkDictValueUnique(SysDictData dictData);

    /**
     * 通过id获取dict数据
     * 根据ID获取对应的字典数据
     *
     * @param id id
     * @return {@link SysDictData}
     */
    SysDictData getDictDataById(String id);

    /**
     * 更新数据dict通过id
     * 根据ID更新字典类型
     *
     * @param sysDictData sys dict类型数据
     * @return int
     */
    int updateDictDataById(SysDictData sysDictData);

    /**
     * 删除dict数据id
     * 批量删除
     *
     * @param idList id列表
     * @return int
     */
    int deleteDictDataByIds(@Param("idList") List<String> idList);

    /**
     * 得到dict类型列表
     * 查询所有字典数据
     *
     * @param dictType dict类型
     * @return {@link List}<{@link DataDictExcelDto}>
     */
    List<DataDictExcelDto> getDictList(String dictType);


    /**
     * 得到dict通过id列表
     * 根据ID查询字典列表
     *
     * @param idList id列表
     * @return {@link List}<{@link DataDictExcelDto}>
     */
    List<DataDictExcelDto> getDictListById(@Param("idList") List<Integer> idList);
}

