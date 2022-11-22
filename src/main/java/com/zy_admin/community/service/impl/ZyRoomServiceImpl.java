package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyRoomDao;
import com.zy_admin.community.dto.ZyRoomDto;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyRoomService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 房间 (ZyRoom)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Service("zyRoomService")
public class ZyRoomServiceImpl extends ServiceImpl<ZyRoomDao, ZyRoom> implements ZyRoomService {
    @Resource
    private SnowflakeManager snowflakeManager;
    /**
     * 分页查询小区信息
     * @param page  分页对象
     * @param zyRoom 查询的房屋信息
     * @return 查询分页的结果集
     */
    @Override
    public Result getAllCommunity(Page page, ZyRoom zyRoom) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        MPJLambdaWrapper<ZyRoom> queryWrapper = new MPJLambdaWrapper<ZyRoom>()
                .selectAll(ZyRoom.class)
                .select(ZyCommunity::getCommunityName)
                .select(ZyUnit::getUnitName)
                .select(ZyBuilding::getBuildingName)
                .selectAssociation(ZyRoom.class, ZyRoomDto::getZyRoom)
                .selectAssociation(ZyCommunity.class, ZyRoomDto::getZyCommunity)
                .selectAssociation(ZyUnit.class, ZyRoomDto::getZyUnit)
                .selectAssociation(ZyBuilding.class, ZyRoomDto::getZyBuilding)
                .leftJoin(ZyCommunity.class, ZyCommunity::getCommunityId, ZyRoom::getCommunityId)
                .leftJoin(ZyUnit.class, ZyUnit::getUnitId, ZyRoom::getUnitId)
                .leftJoin(ZyBuilding.class, ZyBuilding::getBuildingId, ZyRoom::getBuildingId)
                .eq(zyRoom.getBuildingId() != null && !"".equals(zyRoom.getBuildingId()),
                        ZyRoom::getBuildingId, zyRoom.getBuildingId())
                .eq(zyRoom.getCommunityId() != null && !"".equals(zyRoom.getCommunityId()),
                        ZyRoom::getCommunityId, zyRoom.getCommunityId())
                .eq(zyRoom.getUnitId() != null && !"".equals(zyRoom.getUnitId()),
                        ZyRoom::getUnitId, zyRoom.getUnitId())
                .eq(zyRoom.getRoomStatus() != null && !"".equals(zyRoom.getRoomStatus()),
                        ZyRoom::getRoomStatus, zyRoom.getRoomStatus())
                .orderBy(true, true, ZyRoom::getCreateTime);
        if (page.getSize() != 0) {
            IPage<ZyRoomDto> page1 = this.baseMapper.selectJoinPage(page, ZyRoomDto.class, queryWrapper);
            if (page1.getSize() > 0) {
                result.setData(page1);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
        } else {
            List<ZyRoomDto> zyRoomList = this.baseMapper.selectJoinList(ZyRoomDto.class, queryWrapper);
            result.setData(zyRoomList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 根据id查房屋
     * @param roomIds 存放房屋id的数组
     * @return 查询房屋的集合
     */
    @Override
    public List<ZyRoomDto> getRoomByIds(ArrayList<String> roomIds) {
        MPJLambdaWrapper<ZyRoom> wrapper = new MPJLambdaWrapper<ZyRoom>()
                .selectAll(ZyRoom.class)
                .select(ZyCommunity::getCommunityName)
                .select(ZyUnit::getUnitName)
                .select(ZyBuilding::getBuildingName)
                .selectAssociation(ZyRoom.class, ZyRoomDto::getZyRoom)
                .selectAssociation(ZyCommunity.class, ZyRoomDto::getZyCommunity)
                .selectAssociation(ZyUnit.class, ZyRoomDto::getZyUnit)
                .selectAssociation(ZyBuilding.class, ZyRoomDto::getZyBuilding)
                .leftJoin(ZyCommunity.class, ZyCommunity::getCommunityId, ZyRoom::getCommunityId)
                .leftJoin(ZyUnit.class, ZyUnit::getUnitId, ZyRoom::getUnitId)
                .leftJoin(ZyBuilding.class, ZyBuilding::getBuildingId, ZyRoom::getBuildingId)
                .in(ZyRoom::getRoomId, roomIds);
        //连表查询 返回自定义ResultType
        return this.baseMapper.selectJoinList(ZyRoomDto.class, wrapper);
    }
    /**
     * 新增房屋
     * @param zyRoom 新增的房屋信息
     * @param request 前端请求
     * @return 返回成功或错误信息
     */
    @Override
    public Result insertZyRoom(ZyRoom zyRoom, HttpServletRequest request) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断同一小区的楼层是否唯一,type为0是新增
        if (!selectZyRoomByName(0, zyRoom)) {
            result.setMeta(ResultTool.fail(ResultCode.ROOM_HAVE_BEEN));
            return result;
        }
        try {
            long now = System.currentTimeMillis();
            zyRoom.setRoomCode("ROOM_" + Long.toString(now).substring(0, 13));
            zyRoom.setRoomId(snowflakeManager.nextId() + "");
            //新增楼层
            int sysDictType1 = this.baseMapper.insertZyRoom(zyRoom);
            if (sysDictType1 == 1) {
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                result.setData("新增成功");
            }
            return result;
        } catch (Exception e) {
            return new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        }
    }
    /**
     * 修改房屋
     * @param zyRoom 修改的房屋信息
     * @param request 前端请求
     * @return 返回成功或错误信息
     */
    @Override
    public Result updateZyRoom(ZyRoom zyRoom, HttpServletRequest request) {
        ZyRoom zyRoom1 = this.baseMapper.getZyRoom(zyRoom.getRoomId());
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断是不是完全相同
            String[] fields = new String[]{"communityId", "buildingId", "unitId", "roomLevel", "roomName", "roomAcreage", "roomCost", "roomStatus", "roomIsShop", "roomSCommercialHouse", "roomHouseType", "remark"};
            if (!ObjUtil.checkEquals(zyRoom, zyRoom1, fields)) {
                //type为1是修改
                if (selectZyRoomByName(1, zyRoom)) {
                    int i = this.baseMapper.updateZyRoom(zyRoom);
                    if (i == 1) {
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.ROOM_HAVE_BEEN));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }
        return result;
    }
    /**
     * 删除房屋
     * @param idList 存放房屋的id数组
     * @return 返回正确或错误信息
     */
    @Override
    public Result deleteZyRoom(ArrayList<String> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //获取所有状态
        List<String> status = this.baseMapper.getStatus(idList);
        //循环所有状态，如果有一个不是未出售，则返回
        for (String s : status) {
            if(!"none".equals(s)){
                result.setMeta(ResultTool.success(ResultCode.ROOM_HAVE_OWNER));
                return result;
            }
        }
        int i = this.baseMapper.deleteZyRoom(idList);
        if (i >= 1) {
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } else {
            result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
        }
        return result;
    }
    /**
     * 判断楼层号是否重复
     * @param type 判断是新增0还是修改1
     * @param zyRoom 存放房屋信息
     * @return 返回布尔值
     */
    public boolean selectZyRoomByName(int type, ZyRoom zyRoom) {
        List<ZyRoom> zyRooms = this.baseMapper.checkRoomName(zyRoom);
        //类型为0是新增
        if (type == 0) {
            //判断是否为空
            return zyRooms.size() == 0;
        } else {
            if (zyRooms.size() == 0) {
                return true;
                //判断房屋楼层是否唯一
            } else {
                if (zyRooms.size() == 1) {
                    return zyRooms.get(0).getRoomId().equals(zyRoom.getRoomId());
                }
            }
        }
        return false;
    }
}

