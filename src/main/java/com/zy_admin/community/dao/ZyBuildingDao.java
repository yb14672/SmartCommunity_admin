package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
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
     * 根据小区id获取对应的楼栋
     * @param communityId 查询的小区集合
     * @return 查询的楼栋集合
     */
    List<ZyBuilding> getBuildingListsByIds(@Param("list") List<String> communityId);
    /**
     * 判断下面有没有子集
     * @param buildingIds 存放查询的楼层id集合
     * @return 返回查询条数
     */
    Long selectChild(@Param("list") List<String> buildingIds);
    /**
     * 根据楼层id导出选中的楼层
     * @param buildingIds 存放楼层id的数组
     * @return 楼层集合
     */
    List<ZyBuilding> queryZyBuildingById(@Param("list") ArrayList<String> buildingIds);
    /**
     * 获取所有楼层
     * @param communityId 存放小区id
     * @return 查询的楼栋集合
     */
    List<ZyBuilding> getBuildingLists(String communityId);
    /**
     * 删除楼层
     * @param idList 存放楼层id的数组
     * @return 楼层结果集
     */
    int deleteByIdList(@Param("idList") List<String> idList);
    /**
     * 分页查询
     * @param zyBuilding  查询的楼层对象
     * @param pageable 分页对象
     * @return 返回成功或错误信息
     */
    List<ZyBuildingDto> selectBuildLimit(@Param("zyBuilding") ZyBuilding zyBuilding, @Param("pageable")Pageable pageable);
    /**
     * 计算总数据量
     * @param zyBuilding 查询的楼层对象
     * @return 查询求和后的总数
     */
    Long count(@Param("zyBuilding") ZyBuilding zyBuilding);
    /**
     * 新增楼层
     * @param zyBuilding 要新增的楼层信息
     * @return  查询的楼层结果集
     */
    int insertZyBuilding(ZyBuilding zyBuilding);
    /**
     * 判断楼层名是否重复
     * @param buildingName 获取的楼层名
     * @return 返回楼层对象
     */
    ZyBuilding selectZyBuildingByName(@Param("buildingName")String buildingName,@Param("communityId")String communityId);
    /**
     * 更新楼层信息
     * @param zyBuilding 要更新的楼层信息
     * @return 更新楼层结果集
     */
    int updateZyBuilding(@Param("zyBuilding") ZyBuilding zyBuilding);
    /**
     * 通过主键查询单条数据
     * @param id 查询的楼层主键id
     * @return 查询数据条数
     */
    ZyBuilding queryById(String id);
    /**
     * 根据楼层id查楼层
     * @param buildId 获取的楼层id
     * @return 楼层对象
     */
    ZyBuilding getZyBuilding(@Param("buildId") String buildId);
}

