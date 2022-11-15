package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysUserPost;
import com.zy_admin.sys.service.SysUserPostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 用户与岗位关联表(SysUserPost)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@RestController
@RequestMapping("sysUserPost")
public class SysUserPostController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserPostService sysUserPostService;

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param sysUserPost 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysUserPost> page, SysUserPost sysUserPost) {
        return success(this.sysUserPostService.page(page, new QueryWrapper<>(sysUserPost)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysUserPostService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysUserPost 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysUserPost sysUserPost) {
        return success(this.sysUserPostService.save(sysUserPost));
    }

    /**
     * 修改数据
     *
     * @param sysUserPost 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysUserPost sysUserPost) {
        return success(this.sysUserPostService.updateById(sysUserPost));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysUserPostService.removeByIds(idList));
    }
}

