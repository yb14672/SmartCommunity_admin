package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件管理(ZyFiles)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@ApiModel(description = "文件管理(ZyFiles)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyFiles extends Model<ZyFiles> {
    /**
     * 文件ID
     */
    @ApiModelProperty("文件ID")
    private Long filesId;
    /**
     * 文件地址
     */
    @ApiModelProperty("文件地址")
    private String filesUrl;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTime;
    /**
     * 创建人者
     */
    @ApiModelProperty("创建人者")
    private String createBy;
    /**
     * 删除状态0默认1删除
     */
    @ApiModelProperty("删除状态0默认1删除")
    private Integer delFlag;
    /**
     * 来源0APP端，1PC端
     */
    @ApiModelProperty("来源0APP端，1PC端")
    private Integer source;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 父级ID
     */
    @ApiModelProperty("父级ID")
    private Long parentId;
    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    private Long userId;
}

