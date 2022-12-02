package com.zy_admin.community.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.community.dto.OwnerParkListDto;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.entity.ZyOwnerParkRecord;
import com.zy_admin.community.entity.ZyPark;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerPark)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-01 15:18:33
 */
public interface ZyOwnerParkDao extends MPJBaseMapper<ZyOwnerPark> {



    /**
     * 解绑停车位
     *
     * @param ownerParkId 业主停车位Id;
     */
    @Update("Update  zy_owner_park set park_owner_status = 'UnBuilding' where owner_park_id = #{ownerParkId}")
    void deleteOwnerPark(String ownerParkId);


    /**
     * 获取车位业主信息
     *
     * @param ownerParkId 公园所有者id
     * @return {@link OwnerParkListDto}
     */
    @Select("select*from zy_owner_park where owner_park_id =#{ownerParkId}")
    OwnerParkListDto getOwnerPark(String ownerParkId);

    @Select("select community_id from zy_park where park_id = #{parkId}")
    String getCommunityId(String parkId);


    void insertRecord (OwnerParkListDto ownerParkListDto);
    /**
     * 通过ID查询单条数据
     *
     * @param ownerParkId 主键
     * @return 实例对象
     */
    ZyOwnerPark queryById(Long ownerParkId);


    /**
     * 统计总行数
     *
     * @param zyOwner 查询条件
     * @return 总行数
     */
    long count(ZyOwner zyOwner);

    /**
     * 新增数据
     *
     * @param zyOwnerPark 实例对象
     * @return 影响行数
     */
    @Override
    int insert(ZyOwnerPark zyOwnerPark);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ZyOwnerPark> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ZyOwnerPark> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ZyOwnerPark> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ZyOwnerPark> entities);

    /**
     * 修改数据
     *
     * @param zyOwnerPark 实例对象
     * @return 影响行数
     */
    int update(ZyOwnerPark zyOwnerPark);

    /**
     * 通过主键删除数据
     *
     * @param ownerParkId 主键
     * @return 影响行数
     */
    int deleteById(Long ownerParkId);

}

