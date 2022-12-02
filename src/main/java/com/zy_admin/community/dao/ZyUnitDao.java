package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.UnitListDto;
import com.zy_admin.community.entity.ZyUnit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 单元 (ZyUnit)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
public interface ZyUnitDao extends BaseMapper<ZyUnit> {
    /**
     * 获取单元总数
     * @param zyUnit 获取单元对象
     * @return 查询的数据量总数
     */
    Long count(@Param("zyUnit") ZyUnit zyUnit);
    /**
     * 分页查询单元楼信息
     * @param zyUnit   单元对象
     * @param pageable 分页对象
     * @return 返回单元的结果集
     */
    List<UnitListDto>queryAllByLimit(@Param("zyUnit") ZyUnit zyUnit,@Param("pageable") Pageable pageable);
    /**
     * 新增单元楼
     * @param zyUnit 新增的单元信息
     * @return 新增结果集
     */
    int insertUnit(ZyUnit zyUnit);
    /**
     * 单元名查重
     * @param unit 存在单元对象
     * @return 返回单元集合
     */
    List<ZyUnit> selectUnitName(@Param("unit") ZyUnit unit);
    /**
     * 修改单元楼
     * @param zyUnit 要修改的单元信息
     * @return 成功或错误信息
     */
    int updateUnit(ZyUnit zyUnit);
    /**
     * 删除单元
     * @param unitIds 需要被删除的单元id
     * @return 返回删除结果集
     */
    int deleteUnit(@Param("unitIds") List<String> unitIds);

    /**
     * 获取所有单元信息
     * @param communityId 存放小区信息
     * @return 单元集合
     */
    List<ZyUnit> getAll(String communityId);
    /**
     * 根据单元id获取单元信息
     * @param unitIds 存放单元id集合
     * @return 返回小区的集合
     */
    List<ZyUnit> getUnitById(@Param("unitIds") List<String> unitIds);
    /**
     * 根据ID获取单元信息
     * @param unitId 获取单元id
     * @return 单元对象
     */
    ZyUnit queryById(String unitId);
    /**
     * 获取社区ID
     * @param buildingId 存放楼层id
     * @return 单元对象
     */
    @Select("SELECT community_id FROM zy_building where building_id = #{buildingId}")
    ZyUnit selectBuildingId(String buildingId);

    /**
     * 修改单元后更新房屋所在的楼栋
     *
     * @param buildingId 楼栋id
     * @param unitId    单元id
     */
    @Update("update zy_room set building_id=#{buildingId} where unit_id =#{unitId}")
    void updateRoom(@Param("buildingId") String buildingId,@Param("unitId") String unitId);
}

