package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.common.core.Result.Result;

/**
 * 房屋绑定记录表 (ZyOwnerRoomRecord)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerRoomRecordService extends IService<ZyOwnerRoomRecord> {
    /**
     * 审核记录
     * @param zyOwnerRoomRecordId
     * @return
     */
    Result selectZyOwnerRoomRecord(String zyOwnerRoomRecordId);

    /**
     * 新增审核记录
     * @param zyOwnerRoomRecord
     * @return
     */
    Result insertZyOwnerRoomRecord(ZyOwnerRoomRecord zyOwnerRoomRecord);
}

