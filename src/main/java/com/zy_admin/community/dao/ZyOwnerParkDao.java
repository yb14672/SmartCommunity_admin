package com.zy_admin.community.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.ZyOwnerParkDto;
import com.zy_admin.community.entity.ZyOwnerPark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerPark)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-01 15:50:35
 */
public interface ZyOwnerParkDao extends MPJBaseMapper<ZyOwnerPark> {

    /**
     * 修改车位审核的状态
     * @param zyOwnerPark 车位审核对象
     * @return 修改的数量
     */
    int updateOwnerParkStatus(@Param("zyOwnerPark") ZyOwnerPark zyOwnerPark);

    /**
     * 统计车位审核总数量
     * @param zyOwnerParkDto 车位审核对象
     * @return
     */
    Long count(@Param("zyOwnerParkDto") ZyOwnerParkDto zyOwnerParkDto);

    /**
     * 查询所有的车位审核并分页
     * @param zyOwnerParkDto 车位审核对象
     * @return
     */
    List<ZyOwnerParkDto> selectAllParkLimit(@Param("zyOwnerParkDto") ZyOwnerParkDto zyOwnerParkDto);

    /**
     * 通过ID查询单条数据
     *
     * @param ownerParkId 主键
     * @return 实例对象
     */
    ZyOwnerPark queryById(Long ownerParkId);

    /**
     * 查询指定行数据
     *
     * @param zyOwnerPark 查询条件
     * @param pageable    分页对象
     * @return 对象列表
     */
    List<ZyOwnerPark> queryAllByLimit(ZyOwnerPark zyOwnerPark, @Param("pageable") Pageable pageable);

    /**
     * 新增数据
     *
     * @param zyOwnerPark 实例对象
     * @return 影响行数
     */
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

