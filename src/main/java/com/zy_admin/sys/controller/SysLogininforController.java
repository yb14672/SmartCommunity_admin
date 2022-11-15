package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysLogininfor;
import com.zy_admin.sys.service.SysLogininforService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 系统访问记录(SysLogininfor)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@RestController
@RequestMapping("sysLogininfor")
public class SysLogininforController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysLogininforService sysLogininforService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param sysLogininfor 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysLogininfor> page, SysLogininfor sysLogininfor) {
        return success(this.sysLogininforService.page(page, new QueryWrapper<>(sysLogininfor)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysLogininforService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysLogininfor 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysLogininfor sysLogininfor) {
        return success(this.sysLogininforService.save(sysLogininfor));
    }

    /**
     * 修改数据
     *
     * @param sysLogininfor 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysLogininfor sysLogininfor) {
        return success(this.sysLogininforService.updateById(sysLogininfor));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysLogininforService.removeByIds(idList));
    }
}

