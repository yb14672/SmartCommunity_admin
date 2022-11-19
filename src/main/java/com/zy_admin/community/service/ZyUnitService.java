package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.UnitListDto;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.util.Result;

import java.util.List;

/**
 * 单元 (ZyUnit)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
public interface ZyUnitService extends IService<ZyUnit> {

    /**
     *获取单元列表并分页
     * @param unitListDto
     * @param pageable
     * @return
     */
    Result getUnitList(ZyUnit unitListDto,Pageable pageable);

    /**
     * 新增单元楼
     * @param zyUnit
     * @return
     */
    Result insertUnit(ZyUnit zyUnit);

    /***
     * 修改单元
     * @param zyUnit
     * @return
     */
    Result updateUnit(ZyUnit zyUnit);

    /**
     * 删除单元
     * @param unitIds
     * @return
     */
    Result deleteUnit(List<String> unitIds);


    /**
     * 获取所有单元
     * @return
     */
    List<ZyUnit> getAll();

    List<ZyUnit> getUnitById(List<Integer> ids);
}

