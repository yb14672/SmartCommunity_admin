package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.UnitListDto;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.util.Result;

/**
 * 单元 (ZyUnit)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
public interface ZyUnitService extends IService<ZyUnit> {

    /**
     *
     * @param unitListDto
     * @param pageable
     * @return
     */
    Result getUnitList(ZyUnit unitListDto,Pageable pageable);
}

