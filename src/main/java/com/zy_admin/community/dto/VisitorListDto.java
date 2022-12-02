package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyVisitor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访客列表dto
 *
 * @author 张友炜
 * @date 2022/11/23
 */
@ApiModel(description = "访客列表dto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorListDto extends ZyVisitor {


    @ApiModelProperty(hidden = true)
    private String communityId;
    /**
     * 小区名称
     */
    @ApiModelProperty("小区名称")
    private String communityName;
    /**
     * 访客姓名
     */
    @ApiModelProperty("访客姓名")
    private String visitorName;
    /**
     * 访客手机号
     */
    @ApiModelProperty("访客手机号")
    private String visitorPhoneNumber;
    /**
     * 到访时间
     */
    @ApiModelProperty("到访时间")
    private String visitorDate;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 访客操作
     */
    @ApiModelProperty("访客操作")
    private String status;
}
