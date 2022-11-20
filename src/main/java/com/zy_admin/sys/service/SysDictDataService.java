package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.dto.DataDictExcelDto;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典数据表(SysDictData)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 根据字典类型查询所有数据
     * @param deptType
     * @return
     */
    public Result getDict(String deptType);

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param sysDictData 查询实体
     * @return 所有数据
     */
    public Result selectDictDataLimit(SysDictData sysDictData, Page page);

    /**
     * 根据ID查询对应字典数据
     * @param id
     * @return
     */
    Result getDictDataById(String id);

    /**
     * 新增字典数据
     * @param sysDictData
     * @return
     */
    Result insert(SysDictData sysDictData, SysUser user);

    /**
     * 修改字典数据
     * @param sysDictData
     * @return
     */
    Result updateDictData(SysDictData sysDictData, SysUser user);
    /**
     * 检查数据键值是否唯一
     * @param type
     * @param dictData
     * @return
     */
    Boolean checkUnique(int type, SysDictData dictData, SysDictData sysDictData);

    /**
     * 批量删除字典数据
     * @param idList
     * @return
     */
    Result removeDictDataByIds(List<String> idList);

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
    List<DataDictExcelDto> getDictListById(ArrayList<Integer> idList);

}

