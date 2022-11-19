package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 楼栋 (ZyBuilding)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
public interface ZyBuildingService extends IService<ZyBuilding> {

    /**
     * 导出选中的楼层
     *
     * @param buildingIds
     * @return
     */
    List<ZyBuilding> queryZyBuildingById(ArrayList<String> buildingIds);

    /**
     * 导出所有的楼层
     *
     * @return
     */
    List<ZyBuilding> getBuildingLists();

    /**
     * 删除楼层
     * @param idList
     * @return
     */
    Result deleteByIdList(List<String> idList);

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
    Result insertZyBuilding(ZyBuilding zyBuilding, HttpServletRequest request) throws Exception;

    /**
     * 修改楼层
     * @param zyBuilding
     * @return
     */
    Result updateZyBuilding(ZyBuilding zyBuilding, HttpServletRequest request);

    /**
     * 查询对象
     * @param id
     * @return
     */
    Result queryById(String id);
}

