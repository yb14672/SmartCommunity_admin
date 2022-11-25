package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyCommentDao;
import com.zy_admin.community.entity.ZyComment;
import com.zy_admin.community.service.ZyCommentService;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 评论表(ZyComment)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@Service("zyCommentService")
public class ZyCommentServiceImpl extends ServiceImpl<ZyCommentDao, ZyComment> implements ZyCommentService {

    @Resource
    private SnowflakeManager snowflakeManager;

    /**
     * 根据id删除评论
     *
     * @param commentId 评论id
     * @return {@link Result}
     */
    @Override
    public Result delCommentById(String commentId) {
        Result result = new Result("删除失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        int i = this.baseMapper.deleteCommentById(commentId);
        if(i == 1){
            result.setData("删除成功");
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 添加评论
     *
     * @param zyComment 评论信息
     * @return 执行结果
     */
    @Override
    public Result insert(ZyComment zyComment) throws Exception {
        Result result = new Result("添加失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        zyComment.setCreateTime(LocalDateTime.now().toString());
        zyComment.setCommentId(snowflakeManager.nextId() + "");
        int insert = this.baseMapper.insert(zyComment);
        if(insert==1){
            result.setData("添加成功");
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
}

