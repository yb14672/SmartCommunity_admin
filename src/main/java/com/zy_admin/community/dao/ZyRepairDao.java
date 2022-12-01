package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.RepairDto;
import com.zy_admin.community.entity.ZyRepair;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 报修信息(ZyRepair)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
public interface ZyRepairDao extends BaseMapper<ZyRepair> {
    /**
     * 通过业主Id查询报修信息
     *
     * @param repair 修复
     * @return {@link List}<{@link ZyRepair}>
     */
    List<ZyRepair> getRepairByOwnerId(ZyRepair repair);
    /**
     * 分页查询所有报修数据
     *
     * @param pageable     分页对象
     * @param repairDto 查询报修对象
     * @return 所有报修信息数据结果集
     */
    List<RepairDto> getAllRepairs(@Param("pageable") Pageable pageable, @Param("repairDto") RepairDto repairDto);
    /**
     * 计算总数据量
     * @param repairDto 查询的报修对象
     * @return 查询求和后的总数
     */
    Long count(@Param("repairDto") RepairDto repairDto);
    /**
     * 得到所有报修列表
     *
     * @return
     */
    List<RepairDto> getAllRepairList();
    /**
     * 通过id获取报修
     *
     * @param repairIds 报修id
     * @return
     */
    List<RepairDto> getRepairById(@Param("repairList") ArrayList<String> repairIds);

    /**
     * 插入修复
     * @param zyRepair zy修复
     * @return 条数
     */
    int insertRepair(ZyRepair zyRepair);
    /**
     * 更新修复
     *
     * @param zyRepair zy修复
     * @return int
     */
    int updateRepair(ZyRepair zyRepair);


    /**
     * 删除修复
     *
     * @param idList id列表
     * @return int
     */
    int deleteRepair(@Param("idList") List<String> idList);
}

