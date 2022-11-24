package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.util.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 业主 (ZyOwner)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerService extends IService<ZyOwner> {

    /**
     * 获取登录业主信息
     * @param request 请求
     * @return 业主信息结果集
     */
    Result getOwner(HttpServletRequest request);

    /**
     * 业主个人信息设置
     * @param owner 需要修改的信息
     * @param request 请求
     * @return 修改结果
     */
    Result ownerUpdate(ZyOwner owner, HttpServletRequest request);

    /**
     * 业主登录
     * @param owner 账号密码
     * @return 封装结果集
     */
    Result ownerLogin(ZyOwner owner);

    /**
     * 业主注册
     * @param owner 注册信息
     * @return 是否成功结果集
     * @throws Exception
     */
    Result ownerRegister(ZyOwner owner) throws Exception;
}

