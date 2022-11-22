package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.ZyOwnerRoomDto;
import com.zy_admin.community.entity.ZyOwnerRoom;
import org.apache.ibatis.annotations.Param;

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

}

