package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.community.dto.AreaDto;
import com.zy_admin.sys.entity.SysArea;

import java.util.List;

/**
 * 区域表(SysArea)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:33
 */
public interface SysAreaDao extends BaseMapper<SysArea> {
    /**
     * 查询区域树
     * @return 区域集合
     */
    List<AreaDto> queryAreaTree();
}

