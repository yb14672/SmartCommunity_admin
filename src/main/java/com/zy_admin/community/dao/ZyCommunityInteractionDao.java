package com.zy_admin.community.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.ZyCommunityInteractionDto;
import com.zy_admin.community.entity.ZyCommunityInteraction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 社区互动表(ZyCommunityInteraction)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyCommunityInteractionDao extends MPJBaseMapper<ZyCommunityInteraction> {

    /**
     * 通过id列表删除社区互动
     *
     * @param idList id列表
     * @return 影响的行数
     */
    int deleteInteractionByIdList(@Param("idList") List<String> idList);

    /**
     * 计算总数量
     *
     * @param interactionDto 查询条件
     * @return {@link Long}
     */
    Long countNum(ZyCommunityInteractionDto interactionDto);


    /**
     * 查询并分页符合条件的数据
     *
     * @param interactionDto 查询条件
     * @param pageable       分页对象
     * @return {@link List}<{@link ZyCommunityInteractionDto}>
     */
    List<ZyCommunityInteractionDto> selectAllLimit(@Param("dto") ZyCommunityInteractionDto interactionDto,@Param("pageable") Pageable pageable);
}

