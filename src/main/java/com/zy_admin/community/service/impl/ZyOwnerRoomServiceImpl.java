package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyOwnerRoomDao;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.service.ZyOwnerRoomService;
import org.springframework.stereotype.Service;

/**
 * 房屋绑定表 (ZyOwnerRoom)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerRoomService")
public class ZyOwnerRoomServiceImpl extends ServiceImpl<ZyOwnerRoomDao, ZyOwnerRoom> implements ZyOwnerRoomService {

}

