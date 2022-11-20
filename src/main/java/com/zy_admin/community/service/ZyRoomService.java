package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.util.Result;

/**
 * 房间 (ZyRoom)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
public interface ZyRoomService extends IService<ZyRoom> {

    Result getAllCommunity(Page page, ZyRoom zyRoom);
}

