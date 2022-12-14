package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 主人房间excel
 * 业主房间excel
 *
 * @author 14208
 * @date 2022/11/22
 */

@ApiModel(description = "业主房间excel模板")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRoomExcel {

    /**
     * 社区名字
     */
    @ApiModelProperty("小区名")
    @ExcelProperty("小区名字")
    private String communityName;
    /**
     * 建筑名字
     */
    @ApiModelProperty("楼栋名")
    @ExcelProperty("楼栋名")
    private String buildingName;
    /**
     * 单位名字
     */
    @ApiModelProperty("单元名")
    @ExcelProperty("单元名")
    private String unitName;
    /**
     * 房间名字
     */
    @ApiModelProperty("房间名")
    @ExcelProperty("房间名")
    private String roomName;
    /**
     * 业主昵称
     */
    @ApiModelProperty("昵称")
    @ExcelProperty("昵称")
    private String ownerNickname;
    /**
     * 业主真实姓名
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
    @ExcelProperty("身份证")
    private String ownerIdCard;
    /**
     * 电话号码
     */
    @ApiModelProperty("手机号码")
    @ExcelProperty("电话")
    private String ownerPhoneNumber;
    /**
     * 出生日期
     */
    @ApiModelProperty(hidden = true)
    @ExcelProperty("出生日期")
    private String ownerBirthday;
    /**
     * 业主类型
     */
    @ApiModelProperty(hidden = true)
    @ExcelProperty("业主类型")
    private String ownerType;
}
