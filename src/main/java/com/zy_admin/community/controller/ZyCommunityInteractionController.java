package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyCommunityInteraction;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import com.zy_admin.util.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 社区互动表(ZyCommunityInteraction)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Api(value = "zyCommunityInteraction", tags = {"移动端社区互动"})
@RestController
@RequestMapping("/mini/interaction")
public class ZyCommunityInteractionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommunityInteractionService zyCommunityInteractionService;
    @Resource
    private RequestUtil requestUtil;
    /**
     * 分页查询所有数据
     * @param page                   分页对象
     * @param zyCommunityInteraction 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page<ZyCommunityInteraction>", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "ZyCommunityInteraction", name = "zyCommunityInteraction", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Serializable", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyCommunityInteraction", name = "zyCommunityInteraction", value = "实体对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping
    public Result insert(@RequestBody ZyCommunityInteraction zyCommunityInteraction, HttpServletRequest request) throws Exception {
        zyCommunityInteraction.setCreateBy(requestUtil.getOwnerId(request));
        zyCommunityInteraction.setUserId(requestUtil.getOwnerId(request));
        return this.zyCommunityInteractionService.insert(zyCommunityInteraction);
    }

    /**
     * 修改数据
     *
     * @param zyCommunityInteraction 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyCommunityInteraction", name = "zyCommunityInteraction", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "主键结合", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyCommunityInteractionService.removeByIds(idList));
    }
}

