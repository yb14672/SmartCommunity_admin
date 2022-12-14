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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * (ZyPark)表服务实现类
 *
 * @author makejava
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
     * 查询车位状态是启用0的
     *
     * @return 集合对象
     */
    @Override
    public Result selectParkStatusOpen() {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyPark> zyParkList = this.baseMapper.selectParkStatusOpen();
        if (zyParkList.size()!=0){
            result.setData(zyParkList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        }else {
            result.setData("没有被启用的");
            result.setMeta(ResultTool.fail(ResultCode.NO_MATCHING_DATA));
            return result;
        }

    }

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
                .eq(StringUtil.isNotEmpty(zyPark.getCommunityId()), ZyPark::getCommunityId, zyPark.getCommunityId())
                .eq(ZyPark::getDelFlag, 0)
                .orderByDesc(ZyPark::getCreateTime)
                .orderByDesc(ZyPark::getParkCode);
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
     * 根据ids查询列表，若为空根据小区id查询列表
     *
     * @param ids         车位ID列表
     * @param communityId 小区id
     * @return {@link Result}
     */
    @Override
    public Result getListByIdList(ArrayList<String> ids, String communityId) {
        Result result = new Result("没有符合条件的数据", ResultTool.fail(ResultCode.NO_MATCHING_DATA));
        List<ZyParkDto> dtoList = null;
        if (ids.size() != 0) {
            dtoList = this.baseMapper.getDtoList(ids);
        } else {
            dtoList = this.baseMapper.getAllDtoList(communityId);
        }
        if (dtoList.size() > 0) {
            result.setData(dtoList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
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
        //设置几位数字
        NumberFormat f = new DecimalFormat("0000");
        long cont = this.baseMapper.getCont(zyPark.getCommunityId())+1;
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
            zyPark1.setParkCode("PK_" + f.format(cont));
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
