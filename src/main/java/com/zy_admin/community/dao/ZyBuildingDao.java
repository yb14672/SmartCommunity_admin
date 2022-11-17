package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyBuilding;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 楼栋 (ZyBuilding)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
public interface ZyBuildingDao extends BaseMapper<ZyBuilding> {
    /**
     * 楼栋信息和分页
     * @return
     */
    List<ZyBuilding> selectBuildLimit(@Param("zyBuilding") ZyBuilding zyBuilding, @Param("pageable")Pageable pageable);
}

