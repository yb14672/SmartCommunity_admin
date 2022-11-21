package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.community.entity.ZyRepair;
import com.zy_admin.community.service.ZyRepairService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 报修信息(ZyRepair)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@RestController
@RequestMapping("zyRepair")
public class ZyRepairController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyRepairService zyRepairService;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param zyRepair 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyRepair> page, ZyRepair zyRepair) {
        return success(this.zyRepairService.page(page, new QueryWrapper<>(zyRepair)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyRepairService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyRepair 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyRepair zyRepair) {
        return success(this.zyRepairService.save(zyRepair));
    }

    /**
     * 修改数据
     *
     * @param zyRepair 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyRepair zyRepair) {
        return success(this.zyRepairService.updateById(zyRepair));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyRepairService.removeByIds(idList));
    }
}

