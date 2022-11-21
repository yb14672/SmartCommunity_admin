package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.community.entity.ZyComment;
import com.zy_admin.community.service.ZyCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 评论表(ZyComment)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@RestController
@RequestMapping("zyComment")
public class ZyCommentController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommentService zyCommentService;

    /**
     * 分页查询所有数据
     *
     * @param page      分页对象
     * @param zyComment 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyComment> page, ZyComment zyComment) {
        return success(this.zyCommentService.page(page, new QueryWrapper<>(zyComment)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyCommentService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyComment 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyComment zyComment) {
        return success(this.zyCommentService.save(zyComment));
    }

    /**
     * 修改数据
     *
     * @param zyComment 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyComment zyComment) {
        return success(this.zyCommentService.updateById(zyComment));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyCommentService.removeByIds(idList));
    }
}

