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
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
public interface ZyBuildingService extends IService<ZyBuilding> {
    /**
     * 根据小区ID获取下面的楼栋、单元集合
     * @param communityId 查询的小区主键id
     * @return 返回查询数据条数
     */
    Result getBuildingAndUnitListByCommunityId(String communityId);
    /**
     * 根据楼层id导出选中的楼层
     * @param buildingIds 存放楼层id的数组
     * @return 楼层集合
     */
    List<ZyBuilding> queryZyBuildingById(ArrayList<String> buildingIds);
    /**
     * 导出所有的楼层
     * @param communityId 存放小区id的数组
     * @return 楼层集合
     */
    List<ZyBuilding> getBuildingLists(String communityId);
    /**
     * 删除楼层
     * @param idList 存放楼层id的数组
     * @return 楼层结果集
     */
    Result deleteByIdList(List<String> idList);
    /**
     * 分页查询
     * @param zyBuilding  查询的楼层对象
     * @param pageable 分页对象
     * @return 返回成功或错误信息
     */
   Result selectBuildLimit(ZyBuilding zyBuilding, Pageable pageable);
    /**
     * 新增楼层
     * @param zyBuilding 要新增的楼层信息
     * @param request 前端请求
     * @return  查询的楼层结果集
     */
    Result insertZyBuilding(ZyBuilding zyBuilding, HttpServletRequest request) throws Exception;
    /**
     * 更新楼层信息
     * @param zyBuilding 要更新的楼层信息
     * @param request 前端请求
     * @return 更新楼层结果集
     */
    Result updateZyBuilding(ZyBuilding zyBuilding, HttpServletRequest request);
    /**
     * 通过主键查询单条数据
     * @param id 查询的楼层主键id
     * @return 返回查询数据条数
     */
    Result queryById(String id);
}

