package com.zy_admin.sys.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 系统访问记录(SysLogininfor)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@SuppressWarnings("serial")
public class SysLogininfor extends Model<SysLogininfor> {
    //访问ID
    private Long infoId;
    //用户账号
    private String userName;
    //登录IP地址
    private String ipaddr;
    //登录地点
    private String loginLocation;
    //浏览器类型
    private String browser;
    //操作系统
    private String os;
    //登录状态（0成功 1失败）
    private String status;
    //提示消息
    private String msg;
    //访问时间
    private Date loginTime;


    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

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

