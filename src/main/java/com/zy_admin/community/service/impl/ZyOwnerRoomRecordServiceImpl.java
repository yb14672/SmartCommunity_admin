package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyOwnerRoomRecordDao;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.community.service.ZyOwnerRoomRecordService;
import org.springframework.stereotype.Service;

/**
 * 房屋绑定记录表 (ZyOwnerRoomRecord)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerRoomRecordService")
public class ZyOwnerRoomRecordServiceImpl extends ServiceImpl<ZyOwnerRoomRecordDao, ZyOwnerRoomRecord> implements ZyOwnerRoomRecordService {

}

