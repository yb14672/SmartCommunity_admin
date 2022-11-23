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
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@SuppressWarnings("ALL")
public interface SysDictTypeDao extends BaseMapper<SysDictType> {
    /**
     * 分页查询
     * @param sysDictType 字典类型对象
     * @param pageable    分页对象
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 字典类型集合
     */
    List<SysDictType> selectDictByLimit(@Param("sysDict") SysDictType sysDictType, @Param("pageable") Pageable pageable, @Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 查询总数据量
     * @param sysDictType 字典类型对象
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return {@code Long}
     */
    Long count(@Param("sysDict") SysDictType sysDictType, @Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 新增字典类型
     * @param sysDictType 字典类型对象
     * @return 字典类型条数
     */
    int insert(SysDictType sysDictType);
    /**
     * 修改字典类型
     * @param sysDictType 字典类型对象
     * @return 字典类型条数
     */
    int update(SysDictType sysDictType);
    /**
     * 根据对象获取id
     * @param id 字典类型id
     * @return 字典类型条数
     */
    SysDictType queryById(String id);
    /**
     * 同时修改字典和字典数据的类型
     * @param dictType    字典类型对象
     * @param newDictType 新字典类型对象
     */
    void updateDictDataByDictType(@Param("dictType") String dictType, @Param("newDictType") String newDictType);
    /**
     * 通过id列表删除多个
     * @param idList 字典类型id列表集合
     * @return 删除字典条数
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);
    /**
     * 查询name是否重复
     * @param SysDictName 字典类型名字
     * @return 字典类型对象
     */
    @Select("select * from sys_dict_type where dict_name = #{dictName}")
    SysDictType selectSysDictByName(String SysDictName);
    /**
     * 查询type是否重复
     * @param SysDictType 字典类型对象
     * @return 字典类型对象
     */
    @Select("select * from sys_dict_type where dict_type = #{dictType}")
    SysDictType selectSysDictByType(String SysDictType);
    /**
     * 判断下面有没有子集
     * @param DictIds 字典id
     * @return 子级数量
     */
    Integer hasChildDict(@Param("idList") List<Integer> DictIds);

    /**
     * 勾选用户获取excel
     * @param dictIds 字典id
     * @return 字典类型集合
     */
    List<SysDictType> queryDictById(@Param("list") ArrayList<Integer> dictIds);
    /**
     * 所有用户获取excel
     * @return 字典类型集合
     */
    List<SysDictType> getDictLists();
    /**
     * 查询所有字典
     * @return 字典类型集合
     */
    @Select("select * from sys_dict_type")
    List<SysDictType> selectDictAll();
}

