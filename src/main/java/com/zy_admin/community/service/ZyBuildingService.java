package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.util.Result;

import java.util.List;

/**
 * 楼栋 (ZyBuilding)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
public interface ZyBuildingService extends IService<ZyBuilding> {

    /**
     * 删除楼层
     * @param idList
     * @return
     */
    Result deleteByIdList(List<Integer> idList);

    /**
     * 查询楼层和分页
     * @param zyBuilding
     * @param pageable
     * @return
     */
   Result selectBuildLimit(ZyBuilding zyBuilding, Pageable pageable);

    /**
     * 新增楼房
     * @return
     */
    Result insertZyBuilding(ZyBuilding zyBuilding);

    /**
     * 修改楼层
     * @param zyBuilding
     * @return
     */
    Result updateZyBuilding(ZyBuilding zyBuilding);

    /**
     * 查询对象
     * @param id
     * @return
     */
    Result queryById(String id);
}

