package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysDictType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典类型表(SysDictType)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
public interface SysDictTypeDao extends BaseMapper<SysDictType> {
    /**
     * 根据ID查询字典类型
     * @param id
     * @return
     */
    SysDictType queryById(String id);

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
    Long count(@Param("sysDict")SysDictType sysDictType,@Param("startTime")String startTime,@Param("endTime")String endTime);

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
     * 查询所有字典类型
     * @return
     */
    @Select("select * from sys_dict_type")
    List<SysDictType> selectDictAll();
}

