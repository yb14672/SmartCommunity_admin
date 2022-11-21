package com.zy_admin.community.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.community.entity.ZyRoom;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

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
     * 根据ID列表查询所有的状态
     * @param idList
     * @return
     */
    List<String> getStatus(@Param("idList") ArrayList<String> idList);

    /**
     * 删除
     * @param idList
     * @return
     */
    int deleteZyRoom(@Param("idList") ArrayList<String> idList);

    /**
     * 根据id查房屋
     * @param roomId
     * @return
     */
    ZyRoom getZyRoom(String roomId);

    /**
     * 检查房屋名是否唯一
     * @param zyRoom
     * @return
     */
    List<ZyRoom> checkRoomName(@Param("zyRoom") ZyRoom zyRoom);
}

