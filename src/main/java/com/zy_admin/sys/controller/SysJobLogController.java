package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysJobLog;
import com.zy_admin.sys.service.SysJobLogService;
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
 * 定时任务调度日志表(SysJobLog)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@Api(value = "sysJobLog", tags = {"定时任务调度日志表(SysJobLog)表控制层"})
@RestController
@RequestMapping("sysJobLog")
public class SysJobLogController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysJobLogService sysJobLogService;

    /**
     * 分页查询所有数据
     *
     * @param page      分页对象
     * @param sysJobLog 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page<SysJobLog>", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "SysJobLog", name = "sysJobLog", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_common','ROLE_admin')")
    public R selectAll(Page<SysJobLog> page, SysJobLog sysJobLog) {
        return success(this.sysJobLogService.page(page, new QueryWrapper<>(sysJobLog)));
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
        return success(this.sysJobLogService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysJobLog 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysJobLog", name = "sysJobLog", value = "实体对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('monitor:job:add')")
    public R insert(@RequestBody SysJobLog sysJobLog) {
        return success(this.sysJobLogService.save(sysJobLog));
    }

    /**
     * 修改数据
     *
     * @param sysJobLog 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysJobLog", name = "sysJobLog", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('monitor:job:edit')")
    public R update(@RequestBody SysJobLog sysJobLog) {
        return success(this.sysJobLogService.updateById(sysJobLog));
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
    @PreAuthorize("hasAnyAuthority('monitor:job:remove')")
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysJobLogService.removeByIds(idList));
    }
}

