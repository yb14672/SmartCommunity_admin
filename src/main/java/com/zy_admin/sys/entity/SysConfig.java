package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 参数配置表(SysConfig)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@ApiModel(description = "参数配置表(SysConfig)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends Model<SysConfig> {
    /**
     * 参数主键
     */
    @ApiModelProperty("参数主键")
    private Long configId;
    /**
     * 参数名称
     */
    @ApiModelProperty("参数名称")
    private String configName;
    /**
     * 参数键名
     */
    @ApiModelProperty("参数键名")
    private String configKey;
    /**
     * 参数键值
     */
    @ApiModelProperty("参数键值")
    private String configValue;
    /**
     * 系统内置（Y是 N否）
     */
    @ApiModelProperty("系统内置（Y是 N否）")
    private String configType;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
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
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}