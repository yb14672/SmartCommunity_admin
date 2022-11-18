package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysDictType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型表(SysDictType)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@SuppressWarnings("ALL")
public interface SysDictTypeDao extends BaseMapper<SysDictType> {
    /**
     * 分页查询
     * @param sysDictType
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    List<SysDictType> selectDictByLimit(@Param("sysDict") SysDictType sysDictType, @Param("pageable") Pageable pageable, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询总数据量
     * @param sysDictType
     * @param startTime
     * @param endTime
     * @return
     */
    Long count(@Param("sysDict") SysDictType sysDictType, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 新增
      * @param sysDictType
     * @return
     */
    int insert(SysDictType sysDictType);

    /**
     * 修改
     * @param sysDictType
     * @return
     */
    int update(SysDictType sysDictType);

    /**
     * 根据对象获取id
      * @param id
     * @return
     */
    SysDictType queryById(String id);

    /**
     * 同时修改字典和字典数据的类型
     * @param dictType
     * @param newDictType
     */
    void updateDictDataByDictType(@Param("dictType") String dictType, @Param("newDictType") String newDictType);


    /**
     * 删除多个
      * @param idList
     * @return
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);

    /**
     * 查询name是否重复
      * @param SysDictName
     * @return
     */
    @Select("select * from sys_dict_type where dict_name = #{dictName}")
    SysDictType selectSysDictByName(String SysDictName);

    /**
     * 查询type是否重复
     * @param SysDictType
     * @return
     */
    @Select("select * from sys_dict_type where dict_type = #{dictType}")
    SysDictType selectSysDictByType(String SysDictType);

    /**
     * 判断下面有没有子集
     * @param DictIds
     * @return
     */
    Integer hasChildDict(@Param("idList") List<Integer> DictIds);

    /**
     * 勾选用户获取excel
     * @param dictIds
     * @return
     */
    List<SysDictType> queryDictById(@Param("list") ArrayList<Integer> dictIds);

    /**
     * 所有用户获取excel
     * @return
     */
    List<SysDictType> getDictLists();

    /**
     * 查询所有字典
     * @return
     */
    @Select("select * from sys_dict_type")
    List<SysDictType> selectDictAll();
}

