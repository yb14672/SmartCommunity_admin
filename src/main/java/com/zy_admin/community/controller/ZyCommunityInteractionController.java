package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.community.entity.ZyCommunityInteraction;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 社区互动表(ZyCommunityInteraction)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@RestController
@RequestMapping("zyCommunityInteraction")
public class ZyCommunityInteractionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommunityInteractionService zyCommunityInteractionService;

    /**
     * 分页查询所有数据
     *
     * @param page                   分页对象
     * @param zyCommunityInteraction 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyCommunityInteraction> page, ZyCommunityInteraction zyCommunityInteraction) {
        return success(this.zyCommunityInteractionService.page(page, new QueryWrapper<>(zyCommunityInteraction)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyCommunityInteractionService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyCommunityInteraction 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyCommunityInteraction zyCommunityInteraction) {
        return success(this.zyCommunityInteractionService.save(zyCommunityInteraction));
    }

    /**
     * 修改数据
     *
     * @param zyCommunityInteraction 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyCommunityInteraction zyCommunityInteraction) {
        return success(this.zyCommunityInteractionService.updateById(zyCommunityInteraction));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyCommunityInteractionService.removeByIds(idList));
    }
}

