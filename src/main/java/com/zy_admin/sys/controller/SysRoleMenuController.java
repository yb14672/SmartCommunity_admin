package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysRoleMenu;
import com.zy_admin.sys.service.SysRoleMenuService;
import com.zy_admin.common.core.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

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
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param sysRoleMenu 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page<SysRoleMenu>", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "SysRoleMenu", name = "sysRoleMenu", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    public R selectAll(Page<SysRoleMenu> page, SysRoleMenu sysRoleMenu) {
        return success(this.sysRoleMenuService.page(page, new QueryWrapper<>(sysRoleMenu)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Serializable", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysRoleMenuService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRoleMenu 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysRoleMenu", name = "sysRoleMenu", value = "实体对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping
    public R insert(@RequestBody SysRoleMenu sysRoleMenu) {
        return success(this.sysRoleMenuService.save(sysRoleMenu));
    }

    /**
     * 修改数据
     *
     * @param sysRoleMenu 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysRoleMenu", name = "sysRoleMenu", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping
    public R update(@RequestBody SysRoleMenu sysRoleMenu) {
        return success(this.sysRoleMenuService.updateById(sysRoleMenu));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "主键结合", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysRoleMenuService.removeByIds(idList));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "roleId", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/getMenuIds")
    public Result getMenuIds(String roleId){
        return sysRoleMenuService.getMenuIdsByRoleId(roleId);
    }
}

