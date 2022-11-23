package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报修信息(ZyRepair)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@ApiModel(description = "报修信息(ZyRepair)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyRepair extends Model<ZyRepair> {
    /**
     * 报修ID
     */
    @ApiModelProperty("报修ID")
    private Long repairId;
    /**
     * 报修编号
     */
    @ApiModelProperty("报修编号")
    private String repairNum;
    /**
     * 报修状态
     */
    @ApiModelProperty("报修状态")
    private String repairState;
    /**
     * 派单时间
     */
    @ApiModelProperty("派单时间")
    private String assignmentTime;
    /**
     * 接单时间
     */
    @ApiModelProperty("接单时间")
    private String receivingOrdersTime;
    /**
     * 处理完成时间
     */
    @ApiModelProperty("处理完成时间")
    private String completeTime;
    /**
     * 取消时间
     */
    @ApiModelProperty("取消时间")
    private String cancelTime;
    /**
     * 期待上门时间
     */
    @ApiModelProperty("期待上门时间")
    private String doorTime;
    /**
     * 分派人id
     */
    @ApiModelProperty("分派人id")
    private Long assignmentId;
    /**
     * 处理人id
     */
    @ApiModelProperty("处理人id")
    private Long completeId;
    /**
     * 处理人电话
     */
    @ApiModelProperty("处理人电话")
    private String completePhone;
    /**
     * 处理人姓名
     */
    @ApiModelProperty("处理人姓名")
    private String completeName;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
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
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private Long userId;
    /**
     * 删除状态0默认1删除
     */
    @ApiModelProperty("删除状态0默认1删除")
    private Integer delFlag;
    /**
     * 报修内容
     */
    @ApiModelProperty("报修内容")
    private String repairContent;
    /**
     * 小区ID
     */
    @ApiModelProperty("小区ID")
    private Long communityId;
    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String address;
}