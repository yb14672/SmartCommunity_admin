package com.zy_admin.sys.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统访问记录(SysLogininfor)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysLogininfor extends Model<SysLogininfor> {
    /**
     * 访问ID
     */
    @ExcelProperty("访问ID")
    private Long infoId;
    /**
     * 用户账号
     */
    @ExcelProperty("用户账号")
    private String userName;
    /**
     * 登录IP地址
     */
    @ExcelProperty("登录IP地址")
    private String ipaddr;
    /**
     * 登录地点
     */
    @ExcelProperty("登录地点")
    private String loginLocation;
    /**
     * 浏览器类型
     */
    @ExcelProperty("浏览器类型")
    private String browser;
    /**
     * 操作系统
     */
    @ExcelProperty("操作系统")
    private String os;
    /**
     * 登录状态（0成功 1失败）
     */
    @ExcelProperty("登录状态")
    private String status;
    /**
     * 提示消息
     */
    @ExcelProperty("提示消息")
    private String msg;
    /**
     * 访问时间
     */
    @ExcelProperty("访问时间")
    private String loginTime;
    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.infoId;
    }
}

