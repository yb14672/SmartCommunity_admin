package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.ZyParkDto;

/**
 * (ZyPark)表服务接口
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
public interface ZyParkService {

    /**
     * 通过ID查询单条数据
     *
     * @param parkId 主键
     * @return 实例对象
     */
    Result queryById(String parkId);

    /**
     * 分页查询
     *
     * @param zyPark    筛选条件
     * @param page      分页对象
     * @return 查询结果
     */
    Result queryByPage(ZyParkDto zyPark, Page page);
}
