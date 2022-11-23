package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyCommunityInteractionDao;
import com.zy_admin.community.dto.ZyCommunityInteractionDto;
import com.zy_admin.community.entity.ZyComment;
import com.zy_admin.community.entity.ZyCommunityInteraction;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.StringUtil;
import org.springframework.stereotype.Service;

/**
 * 社区互动表(ZyCommunityInteraction)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Service("zyCommunityInteractionService")
public class ZyCommunityInteractionServiceImpl extends ServiceImpl<ZyCommunityInteractionDao, ZyCommunityInteraction> implements ZyCommunityInteractionService {

    /**
     * 分页查询所有数据
     *
     * @param page           分页对象
     * @param interactionDto 查询实体
     * @return 所有数据
     */
    @Override
    public Result selectAll(Page page, ZyCommunityInteractionDto interactionDto) {
        Result result = new Result("没有符合条件的数据", ResultTool.fail(ResultCode.COMMON_FAIL));
        MPJLambdaWrapper<ZyCommunityInteraction> wrapper = new MPJLambdaWrapper<ZyCommunityInteraction>()
                .selectAll(ZyCommunityInteraction.class)
                .select(ZyOwner::getOwnerNickname)
                .select(ZyOwner::getOwnerRealName)
                .select(ZyOwner::getOwnerPhoneNumber)
                .select(ZyOwner::getOwnerPortrait)
                .selectCollection(ZyComment.class, ZyCommunityInteractionDto::getZyCommentList)
                .leftJoin(ZyComment.class, ZyComment::getInteractionId, ZyCommunityInteraction::getInteractionId)
                .leftJoin(ZyOwner.class, ZyOwner::getOwnerId, ZyCommunityInteraction::getUserId)
                .like(StringUtil.isNotEmpty(interactionDto.getOwnerNickname()), ZyOwner::getOwnerNickname, interactionDto.getOwnerNickname())
                .like(StringUtil.isNotEmpty(interactionDto.getOwnerRealName()), ZyOwner::getOwnerRealName, interactionDto.getOwnerRealName())
                .like(StringUtil.isNotEmpty(interactionDto.getOwnerPhoneNumber()), ZyOwner::getOwnerPhoneNumber, interactionDto.getOwnerPhoneNumber())
                .eq(StringUtil.isNotEmpty(interactionDto.getCommunityId()), ZyCommunityInteraction::getCommunityId, interactionDto.getCommunityId());
        IPage<ZyCommunityInteractionDto> interactionDtoList = this.baseMapper.selectJoinPage(page, ZyCommunityInteractionDto.class, wrapper);
        if (interactionDtoList.getSize() > 0) {
            result.setData(interactionDtoList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
}

