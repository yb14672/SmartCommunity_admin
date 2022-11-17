package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyBuilding;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 楼栋 (ZyBuilding)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
public interface ZyBuildingDao extends BaseMapper<ZyBuilding> {

    /**
     * 删除ids
     * @param idList
     * @return
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);

    /**
     * 楼栋信息和分页
     * @return
     */
    List<ZyBuilding> selectBuildLimit(@Param("zyBuilding") ZyBuilding zyBuilding, @Param("pageable")Pageable pageable);

    /**
     * 计算数量
     * @param zyBuilding
     * @return
     */
    Long count(@Param("zyBuilding") ZyBuilding zyBuilding);

    /**
     * 新增楼房
     * @return
     */
    int insertZyBuilding(ZyBuilding zyBuilding);

    /**
     * 判断楼层名是否重复
     * @param buildingName
     * @return
     */
    @Select("select * from zy_building where building_name = #{buildingName}")
    ZyBuilding selectZyBuildingByName(String buildingName);

    /**
     * 修改楼层
     * @param zyBuilding
     * @return
     */
    int updateZyBuilding(@Param("zyBuilding") ZyBuilding zyBuilding);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    ZyBuilding queryById(String id);


    ZyBuilding getZyBuilding(@Param("buildId") String buildId);
}

