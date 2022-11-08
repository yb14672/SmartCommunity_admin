package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.SysDictData;

import java.util.List;

/**
 * 字典数据表(SysDictData)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
public interface SysDictDataDao extends BaseMapper<SysDictData> {

    List<SysDictData> getDict(String deptType);
}

