package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysNotice;
import com.zy_admin.sys.service.SysNoticeService;
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
 * 通知公告表(SysNotice)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@Api(value = "sysNotice", tags = {"通知公告表(SysNotice)表控制层"})
@RestController
@RequestMapping("sysNotice")
public class SysNoticeController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysNoticeService sysNoticeService;

    /**
     * 分页查询所有数据
     *
     * @param page      分页对象
     * @param sysNotice 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page<SysNotice>", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "SysNotice", name = "sysNotice", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_common','ROLE_admin')")
    public R selectAll(Page<SysNotice> page, SysNotice sysNotice) {
        return success(this.sysNoticeService.page(page, new QueryWrapper<>(sysNotice)));
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
        return success(this.sysNoticeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysNotice 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysNotice", name = "sysNotice", value = "实体对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('system:notice:add')")
    public R insert(@RequestBody SysNotice sysNotice) {
        return success(this.sysNoticeService.save(sysNotice));
    }

    /**
     * 修改数据
     *
     * @param sysNotice 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysNotice", name = "sysNotice", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('system:notice:edit')")
    public R update(@RequestBody SysNotice sysNotice) {
        return success(this.sysNoticeService.updateById(sysNotice));
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
    @PreAuthorize("hasAnyAuthority('system:notice:remove')")
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysNoticeService.removeByIds(idList));
    }
}

