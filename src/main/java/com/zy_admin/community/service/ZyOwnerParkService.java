package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.OwnerParkListDto;
import com.zy_admin.community.dto.ZyOwnerParkDto;
import com.zy_admin.community.entity.ZyOwnerPark;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerPark)表服务接口
 *
 * @author makejava
 * @since 2022-12-01 15:50:35
 */
public interface ZyOwnerParkService extends IService<ZyOwnerPark> {

    /**
     * 根据ids查询列表，若为空根据小区id查询列表
     *
     * @param ids 车位ID列表
     * @param communityId 小区id
     * @return {@link Result}
     */
    Result getListByIdList(ArrayList<String> ids, String communityId);

    /**
     * 查询未被绑定和启用的车位
     * @return 车位审核集合
     */
    Result selectNoBindingAndStatusPark(String communityId);

    /**
     * 批量删除
     *
     * @param idList id的集合
     * @return {@link Result}
     */
    Result deleteOwnerParkByIds(List<String> idList);

    /**
     * 修改数据
     *
     * @param zyOwnerPark 实例对象
     * @return 实例对象
     */
    Result updateOwnerPark(ZyOwnerPark zyOwnerPark);

    /**
     * 新增车位审核
     *
     * @param zyOwnerPark 车位审核对象
     * @return 车位审核的条数
     * @throws Exception 异常
     */
    Result insertOwnerPark(ZyOwnerPark zyOwnerPark) throws Exception;

    /**
     * 根据id删除关联信息
     *
     * @param ownerParkId 公园所有者id
     * @param request     请求
     * @return {@link Result}
     * @throws Exception 异常
     */
    Result deleteOwnerPark(String ownerParkId, HttpServletRequest request) throws Exception;


    /**
     * 得到关联列表
     *
     * @param ownerParkListDto 主人dto公园列表
     * @param page             页面
     * @return {@link Result}
     */
    Result getOwnerParkList(OwnerParkListDto ownerParkListDto, Page page);

    /**
     * 提交车位审核以后审核状态改变
     *
     * @param zyOwnerPark        车位审核对象
     * @param recordAuditOpinion 车位审核意见
     * @return 修改的个数
     * @throws Exception 异常
     */
    Result updateOwnerParkStatus(ZyOwnerPark zyOwnerPark,String recordAuditOpinion) throws Exception;

    /**
     * 查询所有的车位审核并分页
     *
     * @param zyOwnerParkDto 车位审核对象
     * @param page           页面
     * @return 车位审核列表
     */
    Result selectAllParkLimit(ZyOwnerParkDto zyOwnerParkDto, Page page);

    /**
     * 通过ID查询单条数据
     *
     * @param ownerParkId 主键
     * @return 实例对象
     */
    ZyOwnerPark queryById(String ownerParkId);


    /**
     * 新增数据
     *
     * @param zyOwnerPark 实例对象
     * @return 实例对象
     */
    ZyOwnerPark insert(ZyOwnerPark zyOwnerPark);

    /**
     * 通过主键删除数据
     *
     * @param ownerParkId 主键
     * @return 是否成功
     */
    boolean deleteById(Long ownerParkId);

}
