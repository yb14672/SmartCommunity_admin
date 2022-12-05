package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysRoleDept;
import com.zy_admin.sys.service.SysRoleDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 角色和部门关联表(SysRoleDept)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Api(value = "sysRoleDept", tags = {"角色和部门关联表(SysRoleDept)表控制层"})
@RestController
@RequestMapping("sysRoleDept")
public class SysRoleDeptController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleDeptService sysRoleDeptService;

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param sysRoleDept 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page<SysRoleDept>", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "SysRoleDept", name = "sysRoleDept", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    public R selectAll(Page<SysRoleDept> page, SysRoleDept sysRoleDept) {
        return success(this.sysRoleDeptService.page(page, new QueryWrapper<>(sysRoleDept)));
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
    @PreAuthorize("hasAnyAuthority('ROLE_common','ROLE_admin')")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysRoleDeptService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRoleDept 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysRoleDept", name = "sysRoleDept", value = "实体对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('system:user:add')")
    public R insert(@RequestBody SysRoleDept sysRoleDept) {
        return success(this.sysRoleDeptService.save(sysRoleDept));
    }

    /**
     * 修改数据
     *
     * @param sysRoleDept 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysRoleDept", name = "sysRoleDept", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping
    public R update(@RequestBody SysRoleDept sysRoleDept) {
        return success(this.sysRoleDeptService.updateById(sysRoleDept));
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
        return success(this.sysRoleDeptService.removeByIds(idList));
    }
}

