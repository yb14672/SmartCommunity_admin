package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.dao.ZyOwnerRoomDao;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.service.ZyOwnerRoomService;
import com.zy_admin.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerRoom)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerRoomService")
public class ZyOwnerRoomServiceImpl extends ServiceImpl<ZyOwnerRoomDao, ZyOwnerRoom> implements ZyOwnerRoomService {

    @Resource
    private ZyOwnerDao ownerDao;
    @Autowired
    private SnowflakeManager snowflakeManager;

    @Override
    public Result ownerInsert(ZyOwnerRoom ownerRoom, HttpServletRequest request) throws Exception {
        Result result = new Result("提交失败",ResultTool.fail(ResultCode.OWNER_ROOM_INSERT_FAIL));
        ZyOwnerRoom zyOwnerRoom = this.baseMapper.checkOwnerRoom(ownerRoom);
        if (zyOwnerRoom != null ){
            result.setData("提交失败");
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_OWNER_ROOM));
            return result;
        }
        String id = JwtUtil.getMemberIdByJwtToken(request);
        ZyOwner zyOwner = ownerDao.selectById(id);
        ownerRoom.setOwnerRoomId(snowflakeManager.nextId()+"");
        ownerRoom.setOwnerId(id);
        ownerRoom.setOwnerType(zyOwner.getOwnerType());
        ownerRoom.setCreateBy(zyOwner.getOwnerRealName());
        ownerRoom.setCreateTime(LocalDateTime.now().toString());
        Integer i = this.baseMapper.insertOwnerRoom(ownerRoom);
        if (i == 1){
            result.setData("提交成功");
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result getTreeData() {
        Result result = new Result("获取失败！", ResultTool.fail(ResultCode.ROOMTREE_GET_FAIL));
        try {
            List<RoomTree> treeRoom = this.baseMapper.getTreeRoom();
            TreeData treeData = new TreeData(treeRoom);
            List<RoomTree> roomTrees = treeData.buildTree();
            result.setData(roomTrees);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }
}

