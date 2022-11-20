package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zy_admin.community.dao.ZyRoomDao;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyRoomService;
import com.zy_admin.sys.dao.SysDictDataDao;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

/**
 * 房间 (ZyRoom)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Service("zyRoomService")
public class ZyRoomServiceImpl extends ServiceImpl<ZyRoomDao, ZyRoom> implements ZyRoomService {
    private SysDictDataDao sysDictDataDao;

    @Override
    public Result getAllCommunity(Page page, ZyRoom zyRoom) {
        System.out.println("获得的zyRoom为" + zyRoom);
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        MPJLambdaWrapper<ZyRoom> queryWrapper = new MPJLambdaWrapper<ZyRoom>()
                .selectAll(ZyRoom.class)    // 查询房屋表全字段
                .leftJoin(ZyCommunity.class,ZyCommunity::getCommunityId,ZyRoom::getCommunityId)
                .leftJoin(ZyUnit.class,ZyUnit::getUnitId,ZyRoom::getUnitId)
                .leftJoin(ZyBuilding.class,ZyBuilding::getBuildingId,ZyRoom::getBuildingId)
                .eq(zyRoom.getBuildingId() != null && !"".equals(zyRoom.getBuildingId()),
                ZyRoom::getBuildingId, zyRoom.getBuildingId())
                .eq(zyRoom.getCommunityId() != null && !"".equals(zyRoom.getCommunityId()),
                        ZyRoom::getCommunityId, zyRoom.getCommunityId())
                .eq(zyRoom.getUnitId() != null && !"".equals(zyRoom.getUnitId()),
                        ZyRoom::getUnitId, zyRoom.getUnitId())
                .eq(zyRoom.getRoomStatus() != null && !"".equals(zyRoom.getRoomStatus()),
                        ZyRoom::getRoomStatus, zyRoom.getRoomStatus())
                .orderBy(true, true, ZyRoom::getCreateTime);
        Page page1 = this.baseMapper.selectPage(page, queryWrapper);
        if (page1.getSize() > 0) {
            result.setData(page1);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        System.out.println(page1);
        return result;
    }
}

