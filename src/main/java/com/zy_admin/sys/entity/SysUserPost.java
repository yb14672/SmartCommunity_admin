package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户与岗位关联表(SysUserPost)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@ApiModel(description = "用户与岗位关联表(SysUserPost)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUserPost extends Model<SysUserPost> {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 岗位ID
     */
    @ApiModelProperty("岗位ID")
    private Long postId;
    /**
     * 获取主键值
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}

