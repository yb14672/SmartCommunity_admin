package com.zy_admin.community.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.community.dto.ZyParkDto;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.entity.ZyPark;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

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
    void deletedPark(@Param("parkIds") List<String> parkIds);


    /**
     * 修改停车位信息
     *
     * @param zyPark 停车位
     */
    void updatePark(ZyPark zyPark);

    /**
     * 新增停车位
     *
     * @param zyPark 停车位
     */
    void insertPark(ZyPark zyPark);

    /**
     * 通过ID查询单条数据
     *
     * @param parkId 主键
     * @return 实例对象
     */
    ZyParkDto queryById(String parkId);

    /**
     * 查询指定行数据
     *
     * @param zyPark   查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<ZyPark> queryAllByLimit(ZyPark zyPark, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param zyPark 查询条件
     * @return 总行数
     */
    long count(ZyPark zyPark);

    /**
     * 新增数据
     *
     * @param zyPark 实例对象
     * @return 影响行数
     */
    int insert(ZyPark zyPark);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ZyPark> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ZyPark> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ZyPark> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ZyPark> entities);

    /**
     * 通过主键删除数据
     *
     * @param parkId 主键
     * @return 影响行数
     */
    int deleteById(Long parkId);

}

