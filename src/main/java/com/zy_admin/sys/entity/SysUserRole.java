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
 * 用户和角色关联表(SysUserRole)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@ApiModel(description = "用户和角色关联表(SysUserRole)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends Model<SysUserRole> {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Long roleId;
    /**
     * 获取主键值
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}

