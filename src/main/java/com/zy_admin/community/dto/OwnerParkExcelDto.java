package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerParkExcelDto {
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @ExcelProperty("昵称")
    private String ownerNickname;
    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    @ExcelProperty("真实姓名")
    private String ownerRealName;
    /**
     * 性别unknow未知male男female女
     */
    @ApiModelProperty("性别unknow未知male男female女")
    @ExcelProperty("性别")
    private String ownerGender;
    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    @ExcelProperty("年龄")
    private Integer ownerAge;
    /**
     * 身份证号码
     */
    @ApiModelProperty("身份证号码")
    @ExcelProperty("身份证号码")
    private String ownerIdCard;
    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @ExcelProperty("手机号码")
    private String ownerPhoneNumber;
    /**
     * 社区名
     */
    @ApiModelProperty("社区名")
    @ExcelProperty("社区名")
    private String communityName;


    /**
     * 车位编号
     */
    @ExcelProperty("车位编号")
    @ApiModelProperty("车位编号")
    private String parkCode;
    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 车位类型
     */
    @ExcelProperty("车位类型")
    @ApiModelProperty("车位类型")
    private String parkType;

}
