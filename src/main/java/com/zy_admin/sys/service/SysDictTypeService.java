package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.util.Result;

/**
 * 字典类型表(SysDictType)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 根据ID字典类型
     * @param id
     * @return
     */
    Result getDictTypeById(String id);
}

