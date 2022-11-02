package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyRoomDao;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.community.service.ZyRoomService;
import org.springframework.stereotype.Service;

/**
 * 房间 (ZyRoom)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Service("zyRoomService")
public class ZyRoomServiceImpl extends ServiceImpl<ZyRoomDao, ZyRoom> implements ZyRoomService {

}

