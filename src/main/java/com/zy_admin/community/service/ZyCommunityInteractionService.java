package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.ZyCommunityInteractionDto;
import com.zy_admin.community.entity.ZyCommunityInteraction;

import java.util.List;

/**
 * 社区互动表(ZyCommunityInteraction)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyCommunityInteractionService extends IService<ZyCommunityInteraction> {
    /**
     * 通过id列表获取互动信息
     *
     * @param idList id列表
     * @return {@link Result}
     */
    Result getListByIdList(List<String> idList);

    /**
     * 分页查询所有数据
     * @param page                   分页对象
     * @param interactionDto 查询实体
     * @return 所有数据
     */
    Result selectAllLimit(Page page, ZyCommunityInteractionDto interactionDto);

    /**
     * 通过id列表删除社区互动
     *
     * @param idList id列表
     * @return {@link Result}
     */
    Result deleteInteractionByIdList(List<String> idList);
}

