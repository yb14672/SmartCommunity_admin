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
     * 根据ID查询单个文章
     * @param interactionId 互动文章id
     * @return 文章信息
     */
    ZyCommunityInteractionDto selectInteractionById(String interactionId);

    /**
     * 根据id集合查询所有数据
     * @param ids id集合
     * @return 数据列表
     */
    List<ZyCommunityInteractionDto> getDtoList(@Param("ids") List<String> ids);

    /**
     * 根据小区查询所有数据
     * @param communityId 小区id
     * @return 数据列表
     */
    List<ZyCommunityInteractionDto> getAllDtoList(String communityId);

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
     * @param zyCommunityInteractionDto 查询条件
     * @return {@link Long}
     */
    Long countNum(@Param("zyCommunityInteractionDto") ZyCommunityInteractionDto zyCommunityInteractionDto);

    /**
     * 查询并分页符合条件的数据
     *
     * @param zyCommunityInteractionDto 查询条件
     * @param pageable                  分页对象
     * @return {@link List}<{@link ZyCommunityInteractionDto}>
     */
    List<ZyCommunityInteractionDto> selectAllLimit(@Param("zyCommunityInteractionDto") ZyCommunityInteractionDto zyCommunityInteractionDto,@Param("pageable") Pageable pageable);
}

