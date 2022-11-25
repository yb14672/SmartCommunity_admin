package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyComment;
import com.zy_admin.community.service.ZyCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 评论表(ZyComment)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@Api(value = "zyComment", tags = {"web端互动评论"})
@RestController
@RequestMapping("/system/comment")
public class ZyCommentController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommentService zyCommentService;

    /**
     * 分页查询所有数据
     * @param page      分页对象
     * @param zyComment 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page<ZyComment>", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "ZyComment", name = "zyComment", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Serializable", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyComment", name = "zyComment", value = "实体对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyComment", name = "zyComment", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping
    public R update(@RequestBody ZyComment zyComment) {
        return success(this.zyCommentService.updateById(zyComment));
    }

    /**
     * 删除数据
     *
     * @param commentId 主键
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "主键结合", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping("/{commentId}")
    public Result delete(@PathVariable String commentId) {
        return this.zyCommentService.delCommentById(commentId);
    }
}

