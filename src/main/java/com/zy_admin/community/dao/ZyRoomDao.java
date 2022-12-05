package com.zy_admin.community.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.GetRoomSDto;
import com.zy_admin.community.entity.ZyRoom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     * 根据单元ID列表查询所有房屋
     * @param unitIds 存放单元id
     * @return 存放房屋集合
     */
    List<ZyRoom> getRoomByUnitId(@Param("unitIds") List<String> unitIds);
    /**
     * 新增房屋
     * @param zyRoom 新增的房屋信息
     * @return 返回成功或错误信息
     */
    int insertZyRoom(ZyRoom zyRoom);
    /**
     * 修改房屋
     * @param zyRoom 修改的房屋信息
     * @return 返回成功或错误信息
     */
    int updateZyRoom(ZyRoom zyRoom);
    /**
     * 根据ID列表查询所有的状态
     * @param idList 存放房屋id
     * @return 字符串集合
     */
    List<String> getStatus(@Param("idList") ArrayList<String> idList);
    /**
     * 删除房屋
     * @param idList 存放房屋的id数组
     * @return 返回正确或错误信息
     */
    int deleteZyRoom(@Param("idList") ArrayList<String> idList);
    /**
     * 根据id查房屋
     * @param roomId 存放获取的房屋id
     * @return 房屋对象
     */
    ZyRoom getZyRoom(String roomId);
    /**
     * 检查房屋名是否唯一
     * @param zyRoom 存放房屋对象
     * @return 房屋集合
     */
    List<ZyRoom> checkRoomName(@Param("zyRoom") ZyRoom zyRoom);

    /**
     * 修改房屋状态
     * @param roomId
     * @return
     */
    int updateRoomStatus(String roomId);

    /**
     * 查询房间状态总数
     *
     * @return {@code Result}
     */
    @Select("SELECT DISTINCT (SELECT COUNT(1) FROM zy_room) as RoomTotal,\n" +
            "(SELECT COUNT(1) FROM zy_room WHERE room_status = 'none') as UnsoldTotal,\n" +
            "(SELECT COUNT(1) FROM zy_room WHERE room_status IN ('has_give','none_stay')) as SoldTotal,\n" +
            "(SELECT COUNT(1) FROM zy_room WHERE room_status = 'has_stay') as MoveInTotal FROM zy_room")
    GetRoomSDto getRoomStatuses();
}

