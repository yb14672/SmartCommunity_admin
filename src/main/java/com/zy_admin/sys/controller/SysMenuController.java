package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysMenuService;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表(SysMenu)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@Api(value = "sysMenu", tags = {"菜单权限表(SysMenu)表控制层"})
@RestController
@RequestMapping("sysMenu")
public class SysMenuController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private RequestUtil requestUtil;
    /**
     * 获取菜单树
     * @return 菜单树的结果集
     */
    @ApiOperation(value = "获取菜单树", notes = "获取菜单树", httpMethod = "GET")
    @GetMapping("/getMenuTree")
    public Result getMenuTree() {
        return this.sysMenuService.getMenuTrees();
    }
    /**
     * 菜单的条件搜索
     * @param menu 查询的菜单对象
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysMenu", name = "menu", value = "查询的菜单对象", required = true)
    })
    @ApiOperation(value = "菜单的条件搜索", notes = "菜单的条件搜索", httpMethod = "GET")
    @GetMapping("/queryMenus")
    public Result queryAllMenu(SysMenu menu) {
        return this.sysMenuService.queryAllMenu(menu);
    }

    /**
     * 获取所有菜单
     * @param request 前端请求
     * @return 所有菜单的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "获取所有菜单", notes = "获取所有菜单", httpMethod = "GET")
    @GetMapping("/getMenus")
//    @PreAuthorize("hasAnyAuthority('system:menu:query')")
    public Result getMenuList(HttpServletRequest request) {
        String userId = requestUtil.getUserId(request);
        return this.sysMenuService.getAllMenu(userId);
    }
    /**
     * 新增数据
     * @param sysMenu 新增菜单对象
     * @param request 前端请求
     * @return 新增菜单结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysMenu", name = "sysMenu", value = "新增菜单对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping("/addMenu")
    @MyLog(title = "菜单管理", optParam = "#{sysMenu}", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAnyAuthority('system:menu:add')")
    public Result insert(@RequestBody SysMenu sysMenu, HttpServletRequest request) {
        String userId = requestUtil.getUserId(request);
        Result result = this.sysUserService.queryById(userId);
        try {
            SysUser user = (SysUser) result.getData();
            sysMenu.setCreateTime(LocalDateTime.now().toString());
            sysMenu.setCreateBy(user.getUserName());
            result = this.sysMenuService.insertMenu(sysMenu);
        } catch (Exception e) {
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
        return result;
    }

    /**
     * 修改数据
     * @param sysMenu 修改的菜单信息
     * @return 根据id修改菜单结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysMenu", name = "sysMenu", value = "修改的菜单信息", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping("/updateMenu")
    @MyLog(title = "菜单管理", optParam = "#{sysMenu}", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyAuthority('system:menu:edit')")
    public Result updateMenu(@RequestBody SysMenu sysMenu) {
        return this.sysMenuService.updateMenu(sysMenu);
    }
    /**
     * 根据id删除菜单
     * @param id 菜单id
     * @return 根据id查出的菜单结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "id", value = "菜单id", required = true)
    })
    @ApiOperation(value = "根据id删除菜单", notes = "根据id删除菜单", httpMethod = "DELETE")
    @DeleteMapping("/deleteById")
    @MyLog(title = "菜单管理", optParam = "#{id}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAnyAuthority('system:menu:remove')")
    public Result deleteById(@RequestParam String id) {
        return this.sysMenuService.deteleById(Long.valueOf(id));
    }
    /**
     * 批量删除菜单
     * @param idList 菜单id数组
     * @return 逻辑删除菜单结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String[]", name = "idList", value = "菜单id数组", required = true)
    })
    @ApiOperation(value = "批量删除菜单", notes = "批量删除菜单", httpMethod = "DELETE")
    @DeleteMapping
    @MyLog(title = "菜单管理", optParam = "#{idList}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAnyAuthority('system:menu:remove')")
    public Result deleteByIdList(@RequestParam String[] idList) {
        List<Long> idList1 = new ArrayList<Long>();
        for (String str : idList) {
            idList1.add(Long.valueOf(str));
        }
        return this.sysMenuService.deleteByIdList(idList1);
    }
}

