package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.UnitListDto;
import com.zy_admin.community.entity.ZyUnit;
import org.apache.ibatis.annotations.Param;

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
}

