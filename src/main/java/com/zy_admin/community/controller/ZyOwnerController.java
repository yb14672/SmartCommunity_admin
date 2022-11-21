package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyOwnerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 业主 (ZyOwner)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@RestController
@RequestMapping("zyOwner")
public class ZyOwnerController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerService zyOwnerService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param zyOwner 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyOwner> page, ZyOwner zyOwner) {
        return success(this.zyOwnerService.page(page, new QueryWrapper<>(zyOwner)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyOwnerService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyOwner 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyOwner zyOwner) {
        return success(this.zyOwnerService.save(zyOwner));
    }

    /**
     * 修改数据
     *
     * @param zyOwner 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyOwner zyOwner) {
        return success(this.zyOwnerService.updateById(zyOwner));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyOwnerService.removeByIds(idList));
    }
}

