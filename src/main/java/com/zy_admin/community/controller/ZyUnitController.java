package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyUnitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 单元 (ZyUnit)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@RestController
@RequestMapping("zyUnit")
public class ZyUnitController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyUnitService zyUnitService;

    /**
     * 分页查询所有数据
     *
     * @param page   分页对象
     * @param zyUnit 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyUnit> page, ZyUnit zyUnit) {
        return success(this.zyUnitService.page(page, new QueryWrapper<>(zyUnit)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyUnitService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyUnit 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyUnit zyUnit) {
        return success(this.zyUnitService.save(zyUnit));
    }

    /**
     * 修改数据
     *
     * @param zyUnit 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyUnit zyUnit) {
        return success(this.zyUnitService.updateById(zyUnit));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyUnitService.removeByIds(idList));
    }
}

