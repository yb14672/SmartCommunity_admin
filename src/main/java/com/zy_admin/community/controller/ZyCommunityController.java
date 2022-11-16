package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.service.ZyCommunityService;
import com.zy_admin.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 小区 (ZyCommunity)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@RestController
@RequestMapping("zyCommunity")
public class ZyCommunityController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommunityService zyCommunityService;

    /**
     * 分页查询
     * @param zyCommunity
     * @param pageable
     * @return
     */
    @GetMapping("/selectCommunityLimit")
    public Result selectCommunityLimit(ZyCommunity zyCommunity, Pageable pageable){
        Result result = zyCommunityService.selectCommunityLimit(zyCommunity, pageable);
        return result;
    }

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param zyCommunity 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyCommunity> page, ZyCommunity zyCommunity) {
        return success(this.zyCommunityService.page(page, new QueryWrapper<>(zyCommunity)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyCommunityService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyCommunity 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyCommunity zyCommunity) {
        return success(this.zyCommunityService.save(zyCommunity));
    }

    /**
     * 修改数据
     *
     * @param zyCommunity 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyCommunity zyCommunity) {
        return success(this.zyCommunityService.updateById(zyCommunity));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyCommunityService.removeByIds(idList));
    }
}

