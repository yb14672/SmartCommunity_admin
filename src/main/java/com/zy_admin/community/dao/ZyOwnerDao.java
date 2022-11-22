package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.OwenrDto;
import com.zy_admin.community.dto.OwnerListDto;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.community.entity.ZyUnit;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 业主 (ZyOwner)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerDao extends BaseMapper<ZyOwner> {
    /**
     *
     * @param zyOwner 户主信息
     * @param pageable 页码
     * @return
     */
    List<OwnerListDto> getOwnerList(@Param("zyOwner") ZyOwner zyOwner, @Param("pageable") Pageable pageable);

    /**
     * 获取总数据量
     * @param zyOwner
     * @return
     */
    long count( ZyOwner zyOwner);

    /**
     * 解绑业主
     * @param ownerRoomId
     */
    @Delete("delete from zy_owner_room where owner_room_id = #{ownerRoomId}")
    void deletOwnerRoomId(String ownerRoomId);

    /**
     * 获取被解绑房屋的所有信息
     * @param ownerRoomId
     * @return
     */
    @Select("select * from zy_owner_room where owner_room_id = #{ownerRoomId}")
    ZyOwnerRoomRecord getZyOwnerRoom(String ownerRoomId);

    void updateIntoRoomRecord(ZyOwnerRoomRecord zyOwnerRoomRecord);
}

