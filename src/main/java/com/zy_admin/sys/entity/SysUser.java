package com.zy_admin.sys.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 用户信息表(SysUser)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class SysUser extends Model<SysUser> {
    //用户ID
    @ExcelProperty("用户ID")
    private Long userId;

    //部门ID
    @ExcelProperty("部门ID")
    private Long deptId;

    //用户账号
    @ExcelProperty("用户账号")
    private String userName;

    //用户昵称
    @ExcelProperty("用户昵称")
    private String nickName;

    //用户类型（00系统用户）
    @ExcelProperty("用户类型（00表示系统用户）")
    private String userType;

    //用户邮箱
    @ExcelProperty("用户邮箱")
    private String email;

    //手机号码
    @ExcelProperty("手机号码")
    private String phonenumber;

    //用户性别（0男 1女 2未知）
    @ExcelProperty("用户性别")
    private String sex;

    //头像地址
    @ExcelProperty("头像地址")
    private String avatar;

    //密码
    @ExcelProperty("密码")
    private String password;

    //帐号状态（0正常 1停用）
    @ExcelProperty("帐号状态（0正常 1停用）")
    private String status;

    //删除标志（0代表存在 2代表删除）
    @ExcelProperty("删除标志（0代表存在 2代表删除）")
    private String delFlag;

    //最后登录IP
    @ExcelProperty("最后登录IP")
    private String loginIp;

    //最后登录时间
    @ExcelProperty("最后登录时间")
    private Date loginDate;

    //创建者
    @ExcelProperty("创建者")
    private String createBy;

    //创建时间
    @ExcelProperty("创建时间")
    private Date createTime;

    //更新者
    @ExcelProperty("更新者")
    private String updateBy;

    //更新时间
    @ExcelProperty("更新时间")
    private Date updateTime;

    //备注
    @ExcelProperty("备注")
    private String remark;

}