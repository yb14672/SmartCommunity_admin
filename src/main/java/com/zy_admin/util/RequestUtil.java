package com.zy_admin.util;

import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyOwnerService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysUserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取Request中数据的工具类
 *
 * @author yb14672
 * Time:2022/11/10 - 10:41
 * @date 2022/11/23
 */
@ApiModel(description = "获取Request中数据的工具类")
@Component
public class RequestUtil {
    /**
     * 系统用户服务
     */
    @ApiModelProperty("系统用户服务")
    @Resource
    private SysUserService sysUserService;

    /**
     * 业主服务
     */
    @ApiModelProperty("业主服务")
    @Resource
    private ZyOwnerService zyOwnerService;

    /**
     * 获取用户
     *
     * @param request 请求
     * @return {@link SysUser}
     */
    public SysUser getUser(HttpServletRequest request) {
        String id = JwtUtil.getMemberIdByJwtToken(request);
        Result result = sysUserService.queryById(id);
        SysUser user = (SysUser) result.getData();
        return user;
    }

    /**
     * 得到业主
     *
     * @param request 请求
     * @return {@link ZyOwner}
     */
    public ZyOwner getOwner(HttpServletRequest request) {
        String id = JwtUtil.getMemberIdByJwtToken(request);
        Result result = zyOwnerService.getOwnerById(id);
        ZyOwner owner = (ZyOwner) result.getData();
        return owner;
    }
}
