package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.community.dto.ZyCommentDto;
import com.zy_admin.community.entity.ZyComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论表(ZyComment)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
public interface ZyCommentDao extends BaseMapper<ZyComment> {
    /**
     * 根据文章ID列表批量逻辑删除评论
     * @param interactionIds 文章ID集合
     * @return 影响的行数
     */
    int deleteByInteractionIdList(@Param("interactionIds") List<String> interactionIds);

    /**
     * 添加评论
     * @param zyComment 评论信息
     * @return 影响行数
     */
    int insertComment(ZyComment zyComment);

    /**
     * 根据查询条件获取评论集合
     * @param zyCommentDto 查询条件
     * @return 评论集合
     */
    List<ZyCommentDto> queryByInteractionId(ZyCommentDto zyCommentDto);

    /**
     * 通过id删除评论
     *
     * @param commentId 评论id
     * @return int
     */
    int deleteCommentById(String commentId);
}

