package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.community.dto.ZyOwnerRoomRecordDto;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;

import java.util.List;

/**
 * 房屋绑定记录表 (ZyOwnerRoomRecord)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerRoomRecordDao extends BaseMapper<ZyOwnerRoomRecord> {

    /**
     * 审核记录
     * @param zyOwnerRoomRecordId
     * @return
     */
    List<ZyOwnerRoomRecordDto> selectZyOwnerRoomRecord(String zyOwnerRoomRecordId);

    /**
     * 新增审核记录
     * @param zyOwnerRoomRecord
     * @return
     */
    @Override
    int insert(ZyOwnerRoomRecord zyOwnerRoomRecord);
}

