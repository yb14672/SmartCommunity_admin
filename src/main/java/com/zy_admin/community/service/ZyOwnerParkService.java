package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.ZyOwnerParkDto;
import com.zy_admin.community.entity.ZyOwnerPark;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerPark)表服务接口
 *
 * @author makejava
 * @since 2022-12-01 15:50:35
 */
public interface ZyOwnerParkService {

    /**
     * 获取所有未绑定的车位审核信息
     * @return 车位审核集合
     */
    Result selectNoBindingPark(String communityId);

    /**
     * 批量删除
     * @param idList id的集合
     * @return
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
     * 插入所有者公园
     * 新增车位审核
     *
     * @param zyOwnerPark 车位审核对象
     * @return 车位审核的条数
     * @throws Exception 异常
     */
    Result insertOwnerPark(ZyOwnerPark zyOwnerPark) throws Exception;

    /**
     * 提交车位审核以后审核状态改变
     * @param zyOwnerPark 车位审核对象
     * @param recordAuditOpinion 车位审核意见
     * @return 修改的个数
     */
    Result updateOwnerParkStatus(ZyOwnerPark zyOwnerPark,String recordAuditOpinion) throws Exception;

    /**
     *查询所有的车位审核并分页
     * @param zyOwnerParkDto 车位审核对象
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
     * 分页查询
     *
     * @param zyOwnerPark 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<ZyOwnerPark> queryByPage(ZyOwnerPark zyOwnerPark, PageRequest pageRequest);

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
