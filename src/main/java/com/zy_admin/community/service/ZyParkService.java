package com.zy_admin.community.service;

import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyPark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (ZyPark)表服务接口
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
public interface ZyParkService {


    /**
     * 删除车位
     *
     * @param parkIds 公园id
     * @return {@link Result}
     */
    Result deletePark(List<String> parkIds);

    /**
     * 修改停车位信息
     *
     * @param zyPark 停车位
     * @return {@link Result}
     */
    Result updatePark(ZyPark zyPark);


    /**
     * 插入车位
     *
     * @param zyPark zy公园
     * @return {@link Result}
     */
    Result insertPark(ZyPark zyPark);

    /**
     * 通过ID查询单条数据
     *
     * @param parkId 主键
     * @return 实例对象
     */
    ZyPark queryById(String parkId);

    /**
     * 分页查询
     *
     * @param zyPark      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<ZyPark> queryByPage(ZyPark zyPark, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param zyPark 实例对象
     * @return 实例对象
     */
    ZyPark insert(ZyPark zyPark);

    /**
     * 修改数据
     *
     * @param zyPark 实例对象
     * @return 实例对象
     */
    ZyPark update(ZyPark zyPark);

    /**
     * 通过主键删除数据
     *
     * @param parkId 主键
     * @return 是否成功
     */
    boolean deleteById(Long parkId);

}
