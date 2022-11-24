package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyComment;

/**
 * 评论表(ZyComment)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
public interface ZyCommentService extends IService<ZyComment> {

    /**
     * 根据id删除评论
     *
     * @param commentId 评论id
     * @return {@link Result}
     */
    Result delCommentById(String commentId);
}

