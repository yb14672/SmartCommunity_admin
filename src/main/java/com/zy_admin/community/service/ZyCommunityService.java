package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.CommunityExcel;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.common.core.Result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 小区 (ZyCommunityDto)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyCommunityService extends IService<ZyCommunity> {
    /**
     * 删除数据
     * @param ids 存放小区id
     * @return 删除结果
     */
    Result deleteByIds(List<String> ids);
    /**
     * 根据Id查询导出数据
     * @param ids  存放小区id数组
     * @return 小区导出集合
     */
    List<CommunityExcel> selectByIds(ArrayList<Long> ids);
    /**
     * 修改数据
     *  @param community 更新的小区对象
     * @param request 前端请求
     * @return 修改的小区结果集
     */
    Result updateCommunityById(ZyCommunity community,HttpServletRequest request);
    /**
     * 新增数据
     * @param community 新增的小区对象
     * @param request 前端请求
     * @return 新增的小区结果集
     */
    Result insertCommunity(ZyCommunity community, HttpServletRequest request);
    /**
     *  分页查询所有数据
     * @param community 查询的小区对象
     * @param pageable 查询的分页对象
     * @return 返回查询分页的结果集
     */
    Result selectAllByLimit(ZyCommunity community, Pageable pageable);

}

