package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.UnitListDto;
import com.zy_admin.community.entity.ZyUnit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     * @param zyUnit
     * @return
     */
    Long count(@Param("zyUnit") ZyUnit zyUnit);

    /**
     * 获取单元信息并分页
     * @param zyUnit
     * @param pageable
     * @return
     */
    List<UnitListDto>queryAllByLimit(@Param("zyUnit") ZyUnit zyUnit,@Param("pageable") Pageable pageable);

    /**
     * 根据楼栋ID获取单元信息
     * @param buildingId
     * @return
     */
    @Select("select * from zy_unit where building_id = #{buildingId}")
    List<ZyUnit> getUnit(String buildingId);

    /**
     * 新增单元楼
     * @param zyUnit
     */
    int insertUnit(ZyUnit zyUnit);

    /**
     * 单元名查重
     * @param unit
     * @return
     */
    List<ZyUnit> selectUnitName(@Param("unit") ZyUnit unit);


    /**
     * 修改单元楼
     * @param zyUnit
     */
    int updateUnit(ZyUnit zyUnit);

    /**
     * 删除单元
     * @param unitIds
     */
    int deleteUnit(@Param("unitIds") List<String> unitIds);


    /**
     * 获取所有单元信息
     * @return
     */
    List<ZyUnit> getAll(String communityId);

    /**
     * 根据ID获取单元信息
     * @param unitIds
     * @return
     */
    List<ZyUnit> getUnitById(@Param("unitIds") List<String> unitIds);

    /**
     * 根据ID获取单元信息
     * @param unitId
     * @return
     */
    ZyUnit queryById(String unitId);

    /**
     * 获取社区ID
     * @param buildingId
     * @return
     */
    @Select("SELECT community_id FROM zy_building where building_id = #{buildingId}")
    ZyUnit selectBuildingId(String buildingId);
}

