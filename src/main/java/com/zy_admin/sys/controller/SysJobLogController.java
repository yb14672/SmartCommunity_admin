package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysJobLog;
import com.zy_admin.sys.service.SysJobLogService;
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
    @GetMapping
    public R selectAll(Page<SysJobLog> page, SysJobLog sysJobLog) {
        return success(this.sysJobLogService.page(page, new QueryWrapper<>(sysJobLog)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysJobLogService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysJobLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysJobLog sysJobLog) {
        return success(this.sysJobLogService.save(sysJobLog));
    }

    /**
     * 修改数据
     *
     * @param sysJobLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysJobLog sysJobLog) {
        return success(this.sysJobLogService.updateById(sysJobLog));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysJobLogService.removeByIds(idList));
    }
}

