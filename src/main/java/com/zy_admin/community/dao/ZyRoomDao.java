//package com.zy_admin.community.dao;
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.zy_admin.common.Pageable;
//import com.zy_admin.community.dto.ZyRoomDto;
//import com.zy_admin.community.entity.ZyRoom;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
///**
// * 房间 (ZyRoom)表数据库访问层
// *
// * @author makejava
// * @since 2022-11-01 19:49:03
// */
//public interface ZyRoomDao extends BaseMapper<ZyRoom> {
//    /**
//     * 房间和分页
//     * @param zyRoom
//     * @param pageable
//     * @return
//     */
//    List<ZyRoomDto> selectAllRoomLimit(@Param("zyRoom") ZyRoom zyRoom, @Param("pageable") Pageable pageable);
//
//    Long count(@Param("zyRoom") ZyRoom zyRoom);
//}
//
