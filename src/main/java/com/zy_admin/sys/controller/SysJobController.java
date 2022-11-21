package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysJob;
import com.zy_admin.sys.service.SysJobService;
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
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysJobService.removeByIds(idList));
    }
}

