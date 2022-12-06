package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.sys.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色和菜单关联表(SysRoleMenu)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:41
 */
@Api(value = "sysRoleMenu", tags = {"角色和菜单关联表(SysRoleMenu)表控制层"})
@RestController
@RequestMapping("sysRoleMenu")
public class SysRoleMenuController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 根据角色ID获取其菜单权限
     *
     * @param roleId 角色id
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "roleId", value = "", required = true)
    })
    @GetMapping("/getMenuIds")
    public Result getMenuIds(String roleId){
        return sysRoleMenuService.getMenuIdsByRoleId(roleId);
    }
}

