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
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyParkService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import com.zy_admin.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * zy公园管理局impl
 * (ZyPark)表服务实现类
 *
 * @author makejava
 * @date 2022/12/02
 * @since 2022-12-01 15:13:40
 */
@Service("zyParkService")
public class ZyParkServiceImpl extends ServiceImpl<ZyParkDao, ZyPark> implements ZyParkService {
    /**
     * 雪花算法
     */
    @Resource
    private SnowflakeManager snowflakeManager;

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
        if (ObjUtil.isNotEmpty(zyPark)) {
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
                .eq(StringUtil.isNotEmpty(zyPark.getCommunityId()), ZyPark::getCommunityId, zyPark.getCommunityId());
//                .orderByDesc(ZyPark::getCreateTime);
        IPage<ZyParkDto> IPage = this.baseMapper.selectJoinPage(page, ZyParkDto.class, wrapper);
        if (IPage.getTotal() != 0) {
            result.setData(IPage);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } else {
            result.setData("没有符合条件的数据");
            result.setMeta(ResultTool.fail(ResultCode.NO_MATCHING_DATA));
        }
        return result;
    }

    /**
     * 批量插入
     *
     * @param zyPark zy公园
     * @param number 数量
     * @return {@link Result}
     * @throws Exception 异常
     */
    @Override
    public Result batchInsert(ZyPark zyPark, int number) throws Exception {
        Result result = new Result("添加失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyPark> zyParks = new ArrayList<>();
        long cont = this.baseMapper.getCont(zyPark.getCommunityId());
        for (int i = 0; i < number; i++) {
            ZyPark zyPark1 = new ZyPark();
            if (StringUtil.isNotEmpty(zyPark.getRemark())) {
                zyPark1.setRemark(zyPark.getRemark());
            }
            zyPark1.setParkType(zyPark.getParkType());
            zyPark1.setParkStatus(zyPark.getParkStatus());
            zyPark1.setCommunityId(zyPark.getCommunityId());
            zyPark1.setParkIsPublic(zyPark.getParkIsPublic());
            zyPark1.setCreateTime(LocalDateTime.now().toString());
            zyPark1.setCreateBy(zyPark.getCreateBy());
            zyPark1.setParkId(snowflakeManager.nextId() + "");
            zyPark1.setParkCode("PK_" +cont);
            cont++;
            zyParks.add(zyPark1);
        }
        try {
            int i = this.baseMapper.insertBatch(zyParks);
            if (i > 0) {
                result.setData("批量添加成功，影响的行数：" + i);
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.INSERT_PARK_FAIL));
        }
        return result;
    }

    /**
     * 删除车位
     *
     * @param parkIds 车位id
     * @return {@link Result}
     */
    @Override
    public Result deletePark(List<String> parkIds) {
        Result result = new Result("删除失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyOwnerPark> zyOwnerParks = this.baseMapper.selectOwnerParkByParkId(parkIds);
        if (zyOwnerParks.size() > 0) {
            result.setMeta(ResultTool.fail(ResultCode.DELETED_PARK_FAIL));
        } else {
            try {
                int i = this.baseMapper.deletedPark(parkIds);
                if (i > 0) {
                    result.setData("删除成功，影响的行数：" + i);
                    result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            }
        }
        return result;
    }

    /**
     * 修改停车位
     *
     * @param zyPark 停车位
     * @return {@link Result}
     */
    @Override
    public Result updatePark(ZyPark zyPark) {
        Result result = new Result("修改失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            int i = this.baseMapper.updatePark(zyPark);
            if (i == 1) {
                result.setData("修改完成");
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.UPDATE_PARK_FAIL));
        }
        return result;
    }

    /**
     * 新增停车位
     *
     * @param zyPark 停车位
     * @return {@link Result}
     */
    @Override
    public Result insertPark(ZyPark zyPark) {
        Result result = new Result("添加失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            this.baseMapper.insertPark(zyPark);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.INSERT_PARK_FAIL));
        }
        return result;
    }
}
