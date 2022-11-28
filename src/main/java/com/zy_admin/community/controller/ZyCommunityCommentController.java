package com.zy_admin.community.controller;

import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyComment;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyCommentService;
import com.zy_admin.util.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 移动端互动评论
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Api(value = "zyComment", tags = {"手机端互动评论"})
@RestController
@RequestMapping("/mini/comment")
public class ZyCommunityCommentController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommentService zyCommentService;
    @Resource
    private RequestUtil requestUtil;
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
    public Result insert(@RequestBody ZyComment zyComment, HttpServletRequest request) throws Exception {
        ZyOwner owner = requestUtil.getOwner(request);
        zyComment.setCreateBy(owner.getOwnerRealName());
        zyComment.setUserId(owner.getOwnerId());
        return this.zyCommentService.insert(zyComment);
    }

    /**
     * 删除数据
     *
     * @param commentId 主键
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "commentId", value = "主键", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping("/{commentId}")
    public Result delete(@PathVariable String commentId) {
        return this.zyCommentService.delCommentById(commentId);
    }
}
