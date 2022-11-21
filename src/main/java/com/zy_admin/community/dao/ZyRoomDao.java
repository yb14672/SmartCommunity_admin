package com.zy_admin.community.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.community.entity.ZyRoom;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * 房间 (ZyRoom)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
public interface ZyRoomDao extends MPJBaseMapper<ZyRoom> {
    /**
     * 新增
     * @param zyRoom
     * @return
     */
    int insertZyRoom(ZyRoom zyRoom);

    /**
     * 修改
     * @param zyRoom
     * @return
     */
    int updateZyRoom(ZyRoom zyRoom);

    /**
     * 删除
     * @param idList
     * @return
     */
    int deleteZyRoom(ArrayList<String> idList);


    /**
     *  判断楼层名是否重复
     * @param roomName
     * @param unitId
     * @param buildingId
     * @param communityId
     * @return
     */

    ZyRoom selectZyRoomByName(@Param("roomName")String roomName, @Param("unitId")String unitId,@Param("buildingId") String buildingId,@Param("communityId") String communityId);
    /**
     * 根据id查房屋
     * @param roomId
     * @return
     */
    ZyRoom getZyRoom(String roomId);
}

