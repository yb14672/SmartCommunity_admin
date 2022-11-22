package com.zy_admin.sys.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
public class SysUserUpload extends Model<SysUserUpload> {
    /**
     * 用户ID
     */
    @ExcelIgnore
    private Long userId;

    /**
     * 部门ID
     */
    @ExcelIgnore
    private Long deptId;

    /**
     * 用户账号
     */
    @ExcelProperty("用户名称")
    private String userName;

    /**
     * 用户昵称
     */
    @ExcelProperty("用户昵称")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @ExcelIgnore
    private String userType;

    /**
     * 用户邮箱
     */
    @ExcelProperty("用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ExcelProperty("手机号码")
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelIgnore
    private String sex;

    /**
     * 头像地址
     */
    @ExcelIgnore
    private String avatar;

    /**
     * 密码
     */
    @ExcelProperty("密码(不填则为888888)")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ExcelIgnore
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ExcelIgnore
    private String delFlag;

    /**
     * 最后登录IP
     */
    @ExcelIgnore
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ExcelIgnore
    private String loginDate;

    /**
     * 创建者
     */
    @ExcelIgnore
    private String createBy;

    /**
     * 创建时间
     */
    @ExcelIgnore
    private String createTime;

    /**
     * 更新者
     */
    @ExcelIgnore
    private String updateBy;

    /**
     * 更新时间
     */
    @ExcelIgnore
    private String updateTime;

    /**
     * 备注
     */
    @ExcelIgnore
    private String remark;

}