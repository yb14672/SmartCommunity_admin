package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.dto.DataDictExcelDto;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.common.core.Result.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典数据表(SysDictData)表服务接口
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
public interface SysDictDataService extends IService<SysDictData> {
    /**
     * 根据字典类型获取所有字典数据
     * @param dictType 字典类型
     * @return 根据字典类型获取所有字典数据结果集
     */
    Result getDict(String dictType);
    /**
     * 分页查询所有字典数据
     * @param page        分页对象
     * @param sysDictData 查询字典数据对象
     * @return 分页查询的结果集
     */
    Result selectDictDataLimit(SysDictData sysDictData, Page page);
    /**
     * 通过字典数据id查询单条数据
     * @param id 字典数据主键
     * @return 单条数据结果集
     */
    Result getDictDataById(String id);
    /**
     * 新增字典数据
     * @param sysDictData 字典数据对象
     * @return 新增的字典数据结果集
     */
    Result insert(SysDictData sysDictData, SysUser user);
    /**
     * 修改字典数据
     * @param sysDictData 字典数据对象
     * @return 修改的字典数据结果集
     */
    Result updateDictData(SysDictData sysDictData, SysUser user);
    /**
     * 检查数据键值是否唯一
     * @param type 判断是新增0还是修改1
     * @param dictData 字典数据对象
     * @return 成功或失败的结果集
     */
    Boolean checkUnique(int type, SysDictData dictData, SysDictData sysDictData);
    /**
     * 删除字典数据
     * @param idList 字典数据主键集合
     * @return 删除的字典结果集
     */
    Result removeDictDataByIds(List<String> idList);
    /**
     * 查询所有字典数据
     * @param dictType 字典数据对象
     * @return 导出字典数据的集合
     */
    List<DataDictExcelDto> getDictList(String dictType);
    /**
     * 根据ID查询字典列表
     * @param idList 字典数据主键
     * @return 字典数据的集合
     */
    List<DataDictExcelDto> getDictListById(List<Integer> idList);

}

