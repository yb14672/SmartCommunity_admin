package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.ZyOwnerRoomDto;
import com.zy_admin.community.entity.ZyOwnerRoom;
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
     * 修改业主审核的状态为绑定
     * @param zyOwnerRoom
     * @return
     */
    int updateOwnerRoomStatusBinding(@Param("zyOwnerRoom") ZyOwnerRoom zyOwnerRoom);

    /**
     * 修改业主审核的状态为审核失败
     * @param zyOwnerRoom
     * @return
     */
    int updateOwnerRoomStatusReject(@Param("zyOwnerRoom") ZyOwnerRoom zyOwnerRoom);

    /**
     * 根据id查业主审核对象
     * @param ownerRoomId
     * @return
     */
    @Select("select * from zy_owner_room where owner_room_id = #{ownerRoomId}")
    ZyOwnerRoom selectOwnerRoomById(String ownerRoomId);

}

