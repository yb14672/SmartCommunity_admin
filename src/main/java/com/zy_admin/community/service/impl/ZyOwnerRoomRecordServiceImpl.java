package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerRoomRecordDao;
import com.zy_admin.community.dto.ZyOwnerRoomRecordDto;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.community.service.ZyOwnerRoomRecordService;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房屋绑定记录表 (ZyOwnerRoomRecord)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerRoomRecordService")
public class ZyOwnerRoomRecordServiceImpl extends ServiceImpl<ZyOwnerRoomRecordDao, ZyOwnerRoomRecord> implements ZyOwnerRoomRecordService {
    /**
     * 审核记录
     *
     * @param zyOwnerRoomRecordId
     * @return
     */
    @Override
    public Result selectZyOwnerRoomRecord(String zyOwnerRoomRecordId) {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //把找到的存到集合里面
        List<ZyOwnerRoomRecordDto> zyOwnerRoomRecordDtoList = this.baseMapper.selectZyOwnerRoomRecord(zyOwnerRoomRecordId);
        //存信息
        result.setData(zyOwnerRoomRecordDtoList);
        //存信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 新增审核记录
     *
     * @param zyOwnerRoomRecord
     * @return
     */
    @Override
    public Result insertZyOwnerRoomRecord(ZyOwnerRoomRecord zyOwnerRoomRecord) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        int i = this.baseMapper.insert(zyOwnerRoomRecord);
        if (i==1){
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            result.setData("新增成功");
        }
        return result;
    }
}

