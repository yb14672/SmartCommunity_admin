package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.community.service.ZyCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 评论表(ZyComment)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@Api(value = "/system/comment", tags = {"评论表(ZyComment)表控制层"})
@RestController
@RequestMapping("/system/comment")
public class ZyCommentController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommentService zyCommentService;

    /**
     * 删除数据
     *
     * @param commentId 主键
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "commentId", value = "主键", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @MyLog(title = "评论信息", optParam = "#{commentId}", businessType = BusinessType.DELETE)
    @DeleteMapping("/{commentId}")
    public Result delete(@PathVariable String commentId) {
        return this.zyCommentService.delCommentById(commentId);
    }
}

