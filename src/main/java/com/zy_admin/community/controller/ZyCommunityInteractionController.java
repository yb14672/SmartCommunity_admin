package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.ZyCommunityInteractionDto;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import com.zy_admin.util.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 社区互动表(ZyCommunityInteraction)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Api(value = "/mini/interaction", tags = {"社区互动表(ZyCommunityInteraction)表控制层"})
@RestController
@RequestMapping("/mini/interaction")
public class ZyCommunityInteractionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommunityInteractionService zyCommunityInteractionService;
    /**
     * 请求工具类
     */
    @Resource
    private RequestUtil requestUtil;
    /**
     * 分页查询所有数据
     * @param zyCommunityInteraction 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyCommunityInteractionDto", name = "zyCommunityInteraction", value = "查询实体", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    public Result selectAll(ZyCommunityInteractionDto zyCommunityInteraction,HttpServletRequest request) {
        //由于是业主使用的小程序，所以只能看到已绑定的房屋对应的小区
        zyCommunityInteraction.setUserId(requestUtil.getOwnerId(request));
        Page page = new Page<>(1,5);
        return this.zyCommunityInteractionService.selectAllLimit(page,zyCommunityInteraction);
    }

    /**
     * 通过文章ID查询详情和评论列表
     *
     * @param id  文章ID
     * @return    单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "文章ID", required = true)
    })
    @ApiOperation(value = "通过文章ID查询详情和评论列表", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("{id}")
    public Result selectOne(@PathVariable String id) {
        return this.zyCommunityInteractionService.getInteractionInfoById(id);
    }

    /**
     * 新增数据
     *
     * @param zyCommunityInteraction 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyCommunityInteractionDto", name = "zyCommunityInteraction", value = "实体对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping
    public Result insert(@RequestBody ZyCommunityInteractionDto zyCommunityInteraction, HttpServletRequest request) throws Exception {
        zyCommunityInteraction.setCreateBy(requestUtil.getOwnerId(request));
        zyCommunityInteraction.setUserId(requestUtil.getOwnerId(request));
        return this.zyCommunityInteractionService.insert(zyCommunityInteraction);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping
    public Result delete(@RequestParam("id") String id) throws Exception {
        List<String> idList = new ArrayList<>();
        idList.add(id);
        return this.zyCommunityInteractionService.deleteInteractionByIdList(idList);
    }
}

