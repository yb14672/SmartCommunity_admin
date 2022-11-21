package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.BuildUnitDto;
import com.zy_admin.community.dto.ZyBuildingDto;
import com.zy_admin.community.entity.ZyBuilding;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 楼栋 (ZyBuilding)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
public interface ZyBuildingDao extends BaseMapper<ZyBuilding> {
    /**
     * 根据小区id获取楼栋及其对应的单元列表
     *
     * @param communityId 小区ID
     * @return 对应的楼栋列表
     */
    List<BuildUnitDto> getBuildingListByCommunityId(String communityId);

    /**
     * 根据小区id获取对应的楼栋
     * @param communityId
     * @return
     */
    List<ZyBuilding> getBuildingListsByIds(@Param("list") List<String> communityId);

    /**
     * 判断下面有没有子集
     * @param buildingIds
     * @return
     */
    Long selectChild(@Param("list") List<String> buildingIds);

    /**
     * 勾选用户获取excel
     * @param buildingIds
     * @return
     */
    List<ZyBuilding> queryZyBuildingById(@Param("list") ArrayList<String> buildingIds);

    /**
     * 所有用户获取excel
     * @return
     */
    List<ZyBuilding> getBuildingLists(String communityId);

    /**
     * 删除ids
     * @param idList
     * @return
     */
    int deleteByIdList(@Param("idList") List<String> idList);

    /**
     * 楼栋信息和分页
     * @return
     */
    List<ZyBuildingDto> selectBuildLimit(@Param("zyBuilding") ZyBuilding zyBuilding, @Param("pageable")Pageable pageable);

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
    ZyBuilding selectZyBuildingByName(@Param("buildingName")String buildingName,@Param("communityId")String communityId);

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


    /**
     * 根据楼层id查楼层
     * @param buildId
     * @return
     */
    ZyBuilding getZyBuilding(@Param("buildId") String buildId);
}

