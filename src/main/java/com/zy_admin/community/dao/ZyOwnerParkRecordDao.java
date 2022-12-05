package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.community.dto.ZyOwnerParkRecordDto;
import com.zy_admin.community.entity.ZyOwnerParkRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 房屋绑定记录表 (ZyOwnerParkRecord)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-01 15:13:39
 */
public interface ZyOwnerParkRecordDao extends BaseMapper<ZyOwnerParkRecord> {

    /**
     * 通过ID查询单条数据
     *
     * @param ownerParkId 主键
     * @return 实例对象
     */
    List<ZyOwnerParkRecordDto> selectOwnerParkById(String ownerParkId);

    /**
     * 根据id查当前车位审核记录
     * @param recordId
     * @return
     */
    ZyOwnerParkRecord queryById(String recordId);

    /**
     * 查询指定行数据
     *
     * @param zyOwnerParkRecord 查询条件
     * @param pageable          分页对象
     * @return 对象列表
     */
    List<ZyOwnerParkRecord> queryAllByLimit(ZyOwnerParkRecord zyOwnerParkRecord, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param zyOwnerParkRecord 查询条件
     * @return 总行数
     */
    long count(ZyOwnerParkRecord zyOwnerParkRecord);

    /**
     * 新增数据
     *
     * @param zyOwnerParkRecord 实例对象
     * @return 影响行数
     */
    int insert(ZyOwnerParkRecord zyOwnerParkRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ZyOwnerParkRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ZyOwnerParkRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ZyOwnerParkRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ZyOwnerParkRecord> entities);

    /**
     * 修改数据
     *
     * @param zyOwnerParkRecord 实例对象
     * @return 影响行数
     */
    int update(ZyOwnerParkRecord zyOwnerParkRecord);

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 影响行数
     */
    int deleteById(Long recordId);

}

