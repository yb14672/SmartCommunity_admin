package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.OwnerRoomDto;
import com.zy_admin.community.dto.ZyOwnerRoomDto;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.util.RoomTree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerRoom)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerRoomDao extends BaseMapper<ZyOwnerRoom> {


    /**
     * 根据业主ID查询关联列表
     *
     * @param ownerId 所有者id
     * @return {@link List}<{@link OwnerRoomDto}>
     */
    List<OwnerRoomDto> selectOwnerRoomByOwnerId(String ownerId);

    /**
     * 根据业主ID获取其房屋绑定列表
     * @param ownerId 业主ID
     * @return 关联关系列表
     */
    List<ZyOwnerRoom> getOwnerRoomByOwnerId(String ownerId);

    /**
     * 验重提交记录
     * @param ownerRoom 房屋信息
     * @return 审核记录
     */
    ZyOwnerRoom checkOwnerRoom(ZyOwnerRoom ownerRoom);

    /**
     * 提交审核
     * @param ownerRoom 提交信息
     * @return 插入影响行数
     */
    Integer insertOwnerRoom(ZyOwnerRoom ownerRoom);

    /**
     获取小区，楼栋，单元，房屋四级联动的树形结构
     * @return 树形结构
     */
    @Select("SELECT community_id id, community_name NAME, 0 parent_id FROM zy_community UNION SELECT building_id id, building_name NAME,community_id parent_id FROM zy_building UNION SELECT unit_id,unit_name,building_id parent_id FROM zy_unit UNION SELECT room_id, room_name,unit_id parent_id FROM zy_room WHERE room_id NOT IN (SELECT room_id from zy_owner_room where room_status != 'Reject'")
    List<RoomTree> getTreeRoom();


    /**
     * 查询所有业主审核的和分页
     * @param zyOwnerRoom
     * @param pageable
     * @return
     */
    List<ZyOwnerRoomDto> selectAllOwnerRoomLimit(@Param("zyOwnerRoom")ZyOwnerRoom zyOwnerRoom, @Param("pageable") Pageable pageable);

    /**
     * 业主审核的总数量
     * @param zyOwnerRoom
     * @return
     */
    Long count(@Param("zyOwnerRoom") ZyOwnerRoom zyOwnerRoom);

    /**
     * 修改业主审核的状态
     * @param zyOwnerRoom
     * @return
     */
    int updateOwnerRoomStatus(@Param("zyOwnerRoom") ZyOwnerRoom zyOwnerRoom);

    /**
     * 根据id查业主审核对象
     * @param ownerRoomId
     * @return
     */
    @Select("select * from zy_owner_room where owner_room_id = #{ownerRoomId}")
    ZyOwnerRoom selectOwnerRoomById(String ownerRoomId);

}

