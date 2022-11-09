package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.util.Result;

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
}

