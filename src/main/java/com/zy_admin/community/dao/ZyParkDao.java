package com.zy_admin.community.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.community.dto.ZyParkDto;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.entity.ZyPark;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * (ZyPark)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
public interface ZyParkDao extends MPJBaseMapper<ZyPark> {

    /**
     * 通过车位Id查询是否被业主绑定
     *
     * @param parkIds 公园id
     * @return {@link List}<{@link ZyOwnerPark}>
     */
    List<ZyOwnerPark> selectOwnerParkByParkId(@Param("parkIds") List<String> parkIds);


    /**
     * 删除停车位
     *
     * @param parkIds 公园id
     */
    int deletedPark(@Param("parkIds") List<String> parkIds);


    /**
     * 更新公园
     * 修改停车位信息
     *
     * @param zyPark 停车位
     * @return int
     */
    int updatePark(ZyPark zyPark);

    /**
     * 插入公园
     * 新增停车位
     *
     * @param zyPark 停车位
     * @return int
     */
    int insertPark(ZyPark zyPark);

    /**
     * 通过ID查询单条数据
     *
     * @param parkId 主键
     * @return 实例对象
     */
    ZyParkDto queryById(String parkId);

    /**
     * 统计总行数
     *
     * @param zyPark 查询条件
     * @return 总行数
     */
    long count(ZyPark zyPark);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ZyPark> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ZyPark> entities);

    /**
     * 通过主键删除数据
     *
     * @param parkId 主键
     * @return 影响行数
     */
    int deleteById(Long parkId);

    /**
     * 根据ID集合查询车位列表列表
     * @param ids id集合
     * @return 车位列表
     */
    List<ZyParkDto> getDtoList(@Param("ids") ArrayList<String> ids);

    /**
     * 根据小区id查询车位列表
     * @param communityId 小区id
     * @return 车位列表
     */
    List<ZyParkDto> getAllDtoList(String communityId);
}

