package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
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
     * 分页查询单元楼信息
     * @param zyUnit   单元对象
     * @param pageable 分页对象
     * @return 返回单元的结果集
     */
    Result getUnitList(ZyUnit zyUnit,Pageable pageable);
    /**
     * 新增单元楼
     * @param zyUnit 新增的单元信息
     * @return 新增结果集
     */
    Result insertUnit(ZyUnit zyUnit);
    /**
     * 修改单元楼
     * @param zyUnit 要修改的单元信息
     * @return 成功或错误信息
     */
    Result updateUnit(ZyUnit zyUnit);
    /**
     * 删除单元
     * @param unitIds 需要被删除的单元id
     * @return 返回删除结果集
     */
    Result deleteUnit(List<String> unitIds);

    /**
     * 根据小区id获取指定小区的单元信息
     * @param communityId 存放小区id
     * @return 返回小区的集合
     */
    List<ZyUnit> getAll(String communityId);
    /**
     * 根据单元id获取单元信息
     * @param ids 存放单元id集合
     * @return 返回小区的集合
     */
    List<ZyUnit> getUnitById(List<String> ids);
    /**
     * 通过小区id获取楼栋
     * @param id 存放小区id
     * @return 返回楼栋的结果集
     */
    Result getBuildingList(String id);
}

