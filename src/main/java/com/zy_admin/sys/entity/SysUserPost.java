package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户与岗位关联表(SysUserPost)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@SuppressWarnings("serial")
public class SysUserPost extends Model<SysUserPost> {
    //用户ID
    private Long userId;
    //岗位ID
    private Long postId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}

