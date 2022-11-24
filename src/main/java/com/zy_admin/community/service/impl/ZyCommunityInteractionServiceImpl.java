package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyCommunityInteractionDao;
import com.zy_admin.community.dto.ZyCommunityInteractionDto;
import com.zy_admin.community.entity.ZyCommunityInteraction;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社区互动表(ZyCommunityInteraction)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Service("zyCommunityInteractionService")
public class ZyCommunityInteractionServiceImpl extends ServiceImpl<ZyCommunityInteractionDao, ZyCommunityInteraction> implements ZyCommunityInteractionService {

    /**
     * 通过id列表获取互动信息
     *
     * @param idList id列表
     * @return {@link Result}
     */
    @Override
    public Result getListByIdList(List<String> idList) {
        Result result = new Result("没有符合条件的数据", ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyCommunityInteraction> zyCommunityInteractions = null;
        if (idList.size() != 0) {
//            zyCommunityInteractions = this.baseMapper.selectBatchIds(idList);
        } else {
            //zyCommunityInteractions=this.baseMapper.
        }
        if (zyCommunityInteractions.size() > 0) {
            result.setData(zyCommunityInteractions);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 分页查询所有数据
     *
     * @param page           分页对象
     * @param interactionDto 查询实体
     * @return 所有数据
     */
    @Override
    public Result selectAllLimit(Page page, ZyCommunityInteractionDto interactionDto) {
        Result result = new Result("没有符合条件的数据", ResultTool.fail(ResultCode.COMMON_FAIL));
        Pageable pageable = new Pageable();
        pageable.setPageSize(page.getSize());
        pageable.setPageNum(page.getCurrent());
        //满足条件的总数
        Long total = this.baseMapper.countNum(interactionDto);
        //默认设置页面为0
        long pages;
        if (total > 0) {
            //计算出总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(Math.min(pageable.getPageNum(), pages));
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<ZyCommunityInteractionDto> interactionDtoList = this.baseMapper.selectAllLimit(interactionDto, pageable);
        if(interactionDtoList.size()!=0){
            com.zy_admin.common.Page<ZyCommunityInteractionDto> dtoPage = new com.zy_admin.common.Page<>(interactionDtoList, pageable);
            result.setData(dtoPage);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 分页查询所有数据
     *
     * @param page           分页对象
     * @param interactionDto 查询实体
     * @return 所有数据
     */
    public Result selectAll(Page page, ZyCommunityInteractionDto interactionDto) {
        Result result = new Result("没有符合条件的数据", ResultTool.fail(ResultCode.COMMON_FAIL));
        MPJLambdaWrapper<ZyCommunityInteraction> wrapper = new MPJLambdaWrapper<ZyCommunityInteraction>()
                .selectAll(ZyCommunityInteraction.class)
                .select(ZyOwner::getOwnerNickname)
                .select(ZyOwner::getOwnerRealName)
                .select(ZyOwner::getOwnerPhoneNumber)
                .select(ZyOwner::getOwnerPortrait)
                .leftJoin(ZyOwner.class, ZyOwner::getOwnerId, ZyCommunityInteraction::getUserId)
                .like(StringUtil.isNotEmpty(interactionDto.getOwnerNickname()), ZyOwner::getOwnerNickname, interactionDto.getOwnerNickname())
                .like(StringUtil.isNotEmpty(interactionDto.getOwnerRealName()), ZyOwner::getOwnerRealName, interactionDto.getOwnerRealName())
                .like(StringUtil.isNotEmpty(interactionDto.getOwnerPhoneNumber()), ZyOwner::getOwnerPhoneNumber, interactionDto.getOwnerPhoneNumber())
                .eq(StringUtil.isNotEmpty(interactionDto.getCommunityId()), ZyCommunityInteraction::getCommunityId, interactionDto.getCommunityId())
                .eq(ZyCommunityInteraction::getDelFlag, 0);
        IPage<ZyCommunityInteractionDto> interactionDtoList = this.baseMapper.selectJoinPage(page, ZyCommunityInteractionDto.class, wrapper);
        if (interactionDtoList.getSize() > 0) {
            result.setData(interactionDtoList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 通过id列表删除社区互动
     *
     * @param idList id列表
     * @return {@link Result}
     */
    @Override
    public Result deleteInteractionByIdList(List<String> idList) {
        Result result = new Result("删除失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        int i = this.baseMapper.deleteInteractionByIdList(idList);
        if (i > 0) {
            result.setData("删除成功");
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
}

