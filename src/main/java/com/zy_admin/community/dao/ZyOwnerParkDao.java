package com.zy_admin.community.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.common.Pageable;
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
     * 查询车牌号有没有重复
     * @param carNumber 车牌号
     * @return
     */
    ZyOwnerPark selectCarNumber(String carNumber);

    /**
     * 查询未被绑定的车位
     * @return
     */
    List<ZyOwnerPark> selectNoBindingPark(String communityId);

    /**
     * 批量删除
     * @param idList id集合
     * @return
     */
    Integer deleteOwnerParkByIds(@Param("idList") List<String> idList);

    /**
     * 修改车位审核的状态
     * @param zyOwnerPark 车位审核对象
     * @return 修改的数量
     */
    int updateOwnerParkStatus(@Param("zyOwnerPark") ZyOwnerPark zyOwnerPark);

    /**
     * 通过ID查询单条数据
     *
     * @param ownerParkId 主键
     * @return 实例对象
     */
    ZyOwnerPark queryById(String ownerParkId);

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
    int updateOwnerPark(@Param("zyOwnerPark") ZyOwnerPark zyOwnerPark);

    /**
     * 通过主键删除数据
     *
     * @param ownerParkId 主键
     * @return 影响行数
     */
    int deleteById(Long ownerParkId);

}

