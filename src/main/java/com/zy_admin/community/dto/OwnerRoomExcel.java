package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 主人房间excel
 *
 * @author 14208
 * @date 2022/11/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRoomExcel {

    /**
     * 小区名
     */
    @ExcelProperty("小区名字")
    private String communityName;
    /**
     * 楼栋名
     */
    @ExcelProperty("楼栋名")
    private String buildingName;
    /**
     * 单元名
     */
    @ExcelProperty("单元名")
    private String unitName;
    /**
     * 房间名
     */
    @ExcelProperty("房间名")
    private String roomName;
    //昵称
    @ExcelProperty("昵称")
    private String ownerNickname;
    //真实姓名
    @ExcelProperty("真实姓名")
    private String ownerRealName;
    //性别unknow未知male男female女
    @ExcelProperty("性别")
    private String ownerGender;
    //年龄
    @ExcelProperty("年龄")
    private Integer ownerAge;
    //身份证号码
    @ExcelProperty("身份证")
    private String ownerIdCard;
    //手机号码
    @ExcelProperty("电话")
    private String ownerPhoneNumber;
    @ExcelProperty("出生日期")
    //出生日期
    private String ownerBirthday;
    @ExcelProperty("业主类型")
    private String ownerType;
}
