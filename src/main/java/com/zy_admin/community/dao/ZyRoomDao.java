package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.community.entity.ZyRoom;

import java.util.List;

/**
 * 房间 (ZyRoom)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
public interface ZyRoomDao extends MPJBaseMapper<ZyRoom> {
    /**
     * 分页查询
     * @param page
     * @param zyRoom
     * @return
     */
    List<ZyRoom> getAllCommunity(Page page, ZyRoom zyRoom);

    /**
     * 总条数
     * @param zyRoom
     * @return
     */
    int count(ZyRoom zyRoom);
}

