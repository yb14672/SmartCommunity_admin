package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.util.Result;

/**
 * 小区 (ZyCommunity)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyCommunityService extends IService<ZyCommunity> {
    /**
     * 查询所有小区信息
     * @return
     */
    Result selectCommunityLimit(ZyCommunity zyCommunity, Pageable pageable);
}

