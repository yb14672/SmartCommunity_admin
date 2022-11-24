package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.community.dto.ZyOwnerRoomRecordDto;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;

import java.util.List;

/**
 * 业主房间记录道
 * 房屋绑定记录表 (ZyOwnerRoomRecord)表数据库访问层
 *
 * @author makejava
 * @date 2022/11/22
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerRoomRecordDao extends BaseMapper<ZyOwnerRoomRecord> {

    /**
     * 选择业主房间记录
     *
     * @param zyOwnerRoomRecordId 业主房间记录id
     * @return {@link List<ZyOwnerRoomRecordDto>}
     */
    List<ZyOwnerRoomRecordDto> selectZyOwnerRoomRecord(String zyOwnerRoomRecordId);


    /**
     * 插入
     *
     * @param zyOwnerRoomRecord 业主房间记录
     * @return int
     */
    @Override
    int insert(ZyOwnerRoomRecord zyOwnerRoomRecord);
}

