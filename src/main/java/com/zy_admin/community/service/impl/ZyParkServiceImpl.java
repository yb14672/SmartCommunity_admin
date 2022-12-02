package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyParkDao;
import com.zy_admin.community.dto.ZyParkDto;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyParkService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.StringUtil;
import org.springframework.stereotype.Service;

/**
 * (ZyPark)表服务实现类
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
@Service("zyParkService")
public class ZyParkServiceImpl extends ServiceImpl<ZyParkDao, ZyPark> implements ZyParkService {

    /**
     * 通过ID查询单条数据
     *
     * @param parkId 主键
     * @return 实例对象
     */
    @Override
    public Result queryById(String parkId) {
        Result result = new Result("查询失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        ZyParkDto zyPark = this.baseMapper.queryById(parkId);
        if(ObjUtil.isNotEmpty(zyPark)){
            result.setData(zyPark);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 分页查询
     *
     * @param zyPark 筛选条件
     * @param page   分页对象
     * @return 查询结果
     */
    @Override
    public Result queryByPage(ZyParkDto zyPark, Page page) {
        Result result = new Result("查询失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        MPJLambdaWrapper<ZyPark> wrapper = new MPJLambdaWrapper<ZyPark>()
                .selectAll(ZyPark.class)
                .select(ZyCommunity::getCommunityName)
                .leftJoin(ZyCommunity.class, ZyCommunity::getCommunityId, ZyPark::getCommunityId)
                .eq(StringUtil.isNotEmpty(zyPark.getParkType()), ZyPark::getParkType, zyPark.getParkType())
                .eq(StringUtil.isNotEmpty(zyPark.getParkStatus()), ZyPark::getParkStatus, zyPark.getParkStatus())
                .eq(StringUtil.isNotEmpty(zyPark.getParkIsPublic()), ZyPark::getParkIsPublic, zyPark.getParkIsPublic())
                .eq(StringUtil.isNotEmpty(zyPark.getCommunityId()), ZyPark::getCommunityId, zyPark.getCommunityId())
                .orderByDesc(ZyPark::getCreateTime);
        IPage<ZyParkDto> IPage = this.baseMapper.selectJoinPage(page, ZyParkDto.class, wrapper);
        if (IPage.getTotal() != 0) {
            result.setData(IPage);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }else{
            result.setData("没有符合条件的数据");
            result.setMeta(ResultTool.fail(ResultCode.NO_MATCHING_DATA));
        }
        return result;
    }
}
