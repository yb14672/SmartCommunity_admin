package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysJob;
import com.zy_admin.sys.service.SysJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 定时任务调度表(SysJob)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */

@Api(value = "sysJob", tags = {"定时任务调度表(SysJob)表控制层"})
@RestController
@RequestMapping("sysJob")
public class SysJobController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysJobService sysJobService;

    /**
     * 分页查询所有数据
     *
     * @param page   分页对象
     * @param sysJob 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page<SysJob>", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "SysJob", name = "sysJob", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    public R selectAll(Page<SysJob> page, SysJob sysJob) {
        return success(this.sysJobService.page(page, new QueryWrapper<>(sysJob)));
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
        return success(this.sysJobService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysJob 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysJob", name = "sysJob", value = "实体对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping
    public R insert(@RequestBody SysJob sysJob) {
        return success(this.sysJobService.save(sysJob));
    }

    /**
     * 修改数据
     *
     * @param sysJob 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysJob", name = "sysJob", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping
    public R update(@RequestBody SysJob sysJob) {
        return success(this.sysJobService.updateById(sysJob));
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
        return success(this.sysJobService.removeByIds(idList));
    }
}

