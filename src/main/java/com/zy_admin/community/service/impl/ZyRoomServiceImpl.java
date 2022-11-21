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
import com.zy_admin.sys.dao.SysDictDataDao;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
    private SysDictDataDao sysDictDataDao;
    private SysUserDao sysUserDao;
    private ZyRoomDao zyRoomDao;
    @Autowired
    private SnowflakeManager snowflakeManager;

    @Override
    public Result getAllCommunity(Page page, ZyRoom zyRoom) {
        System.out.println("获得的zyRoom为" + zyRoom);
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        MPJLambdaWrapper<ZyRoom> queryWrapper = new MPJLambdaWrapper<ZyRoom>()
                .selectAll(ZyRoom.class)    // 查询房屋表全字段
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
            List<ZyRoom> zyRoomList = this.baseMapper.selectList(queryWrapper);
            result.setData(zyRoomList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 根据id查房屋
     *
     * @param roomIds
     * @return
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
        List<ZyRoomDto> zyRoomDtoList = this.baseMapper.selectJoinList(ZyRoomDto.class, wrapper);
        return zyRoomDtoList;
    }

    /**
     * 新增
     *
     * @param zyRoom
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result insertZyRoom(ZyRoom zyRoom, HttpServletRequest request) throws Exception {
        Long now = System.currentTimeMillis();
        zyRoom.setRoomCode("ROOM_" + now.toString().substring(0, 13));
        zyRoom.setRoomId(snowflakeManager.nextId() + "");
        zyRoom.setCreateTime(LocalDateTime.now().toString());
        String id = JwtUtil.getMemberIdByJwtToken(request);
        zyRoom.setCreateBy(sysUserDao.getUserById(id).getUserName());

        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断同一小区的楼层是否唯一,type为0是新增
        if (!selectZyRoomByName(0, zyRoom)) {
            result.setMeta(ResultTool.fail(ResultCode.ROOM_HAVE_BEEN));
            return result;
        }
        try {
            //新增楼层
            int sysDictType1 = this.baseMapper.insertZyRoom(zyRoom);
            if (sysDictType1 == 1) {
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                result.setData("新增成功");
            }
            return result;
        } catch (Exception e) {
            return new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        }

    }

    /**
     * 修改
     *
     * @param zyRoom
     * @param request
     * @return
     */
    @Override
    public Result updateZyRoom(ZyRoom zyRoom, HttpServletRequest request) {
        ZyRoom zyRoom1 = this.baseMapper.getZyRoom(zyRoom.getRoomId());
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断是不是完全相同
//            if (checkEquals(zyBuilding1, zyBuilding)) {
            //type为1是修改
            if (selectZyRoomByName(1, zyRoom)) {
                String id = JwtUtil.getMemberIdByJwtToken(request);
                zyRoom.setUpdateTime(LocalDateTime.now().toString());
                zyRoom.setUpdateBy(sysUserDao.getUserById(id).getUserName());
                int i = this.baseMapper.updateZyRoom(zyRoom);
                if (i == 1) {
                    result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.ROOM_HAVE_BEEN));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }
        return result;
    }

    /**
     * 删除
     *
     * @param idList
     * @return
     */
    @Override
    public Result deleteZyRoom(ArrayList<String> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是单个
        if (idList.size() == 1) {
            int i = this.baseMapper.deleteZyRoom(idList);
            result.setData("删除成功，影响的行数：" + i);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            //多个就是批量删除
        } else {
            int i = this.baseMapper.deleteZyRoom(idList);
            if (i >= 1) {
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
            }
        }
        return result;
    }

    /**
     * 判断楼层号是否重复
     *
     * @param type
     * @param zyRoom
     * @return
     */
    public boolean selectZyRoomByName(int type, ZyRoom zyRoom) {
        ZyRoom zyRoom1 = this.baseMapper.selectZyRoomByName
                (zyRoom.getRoomName(), zyRoom.getUnitId(), zyRoom.getBuildingId(), zyRoom.getCommunityId());
        //类型为0是新增
        if (type == 0) {
            //判断是否为空
            if (zyRoom1 == null || zyRoom1.getRoomName() == null) {
                return true;
            }
        } else {
            if (zyRoom1 == null || zyRoom1.getUnitId() == null) {
                return true;
                //判断房屋楼层是否唯一
            } else if (!zyRoom1.getUnitId().equals(zyRoom.getUnitId())){
                return false;
            }else if (!zyRoom1.getBuildingId().equals(zyRoom.getBuildingId())) {
                return false;
            } else if (!zyRoom1.getCommunityId().equals(zyRoom.getCommunityId())) {
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }
}

