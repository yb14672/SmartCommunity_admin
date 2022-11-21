package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysNotice;
import com.zy_admin.sys.service.SysNoticeService;
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
    @GetMapping
    public R selectAll(Page<SysNotice> page, SysNotice sysNotice) {
        return success(this.sysNoticeService.page(page, new QueryWrapper<>(sysNotice)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysNoticeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysNotice 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysNotice sysNotice) {
        return success(this.sysNoticeService.save(sysNotice));
    }

    /**
     * 修改数据
     *
     * @param sysNotice 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysNotice sysNotice) {
        return success(this.sysNoticeService.updateById(sysNotice));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysNoticeService.removeByIds(idList));
    }
}

