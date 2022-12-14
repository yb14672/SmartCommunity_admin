package com.zy_admin.sys.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 字典类型表(SysDictType)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@ApiModel(description = "字典类型表(SysDictType)表实体类")
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysDictType extends Model<SysDictType> {
    /**
     * 字典主键
     */
    @ApiModelProperty("字典主键")
    @ExcelProperty("角色ID")
    private Long dictId;
    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    @ExcelProperty("字典名称")
    private String dictName;
    /**
     * 字典类型
     */
    @ApiModelProperty("字典类型")
    @ExcelProperty("字典类型")
    private String dictType;
    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty("状态（0正常 1停用）")
    @ExcelProperty("状态（0正常 1停用）")
    private String status;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    @ExcelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @ExcelProperty("创建时间")
    private String createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    @ExcelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @ExcelProperty("更新时间")
    private String updateTime;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @ExcelProperty("备注")
    private String remark;
}

