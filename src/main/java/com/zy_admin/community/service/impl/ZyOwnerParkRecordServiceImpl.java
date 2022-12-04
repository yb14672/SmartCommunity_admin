package com.zy_admin.community.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerParkRecordDao;
import com.zy_admin.community.dto.ZyOwnerParkRecordDto;
import com.zy_admin.community.entity.ZyOwnerParkRecord;
import com.zy_admin.community.service.ZyOwnerParkRecordService;
import com.zy_admin.util.ResultTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 房屋绑定记录表 (ZyOwnerParkRecord)表服务实现类
 *
 * @author makejava
 * @since 2022-12-01 16:43:00
 */
@Service("zyOwnerParkRecordService")
public class ZyOwnerParkRecordServiceImpl extends ServiceImpl<ZyOwnerParkRecordDao, ZyOwnerParkRecord> implements ZyOwnerParkRecordService {
    @Resource
    private ZyOwnerParkRecordDao zyOwnerParkRecordDao;

    /**
     * 查询车位审核记录
     * @param ownerParkId 车位审核记录的id
     * @return 车位审核记录
     */
    @Override
    public Result selectOwnerParkById(String ownerParkId) {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //把找到的存到集合里面
        List<ZyOwnerParkRecordDto> zyOwnerParkRecordDtoList = this.baseMapper.selectOwnerParkById(ownerParkId);
        //存信息
        result.setData(zyOwnerParkRecordDtoList);
        //存信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    @Override
    public ZyOwnerParkRecord queryById(String recordId) {
        return this.zyOwnerParkRecordDao.queryById(recordId);
    }

    /**
     * 分页查询
     *
     * @param zyOwnerParkRecord 筛选条件
     * @param pageRequest       分页对象
     * @return 查询结果
     */
    @Override
    public Page<ZyOwnerParkRecord> queryByPage(ZyOwnerParkRecord zyOwnerParkRecord, PageRequest pageRequest) {
        long total = this.zyOwnerParkRecordDao.count(zyOwnerParkRecord);
        return new PageImpl<>(this.zyOwnerParkRecordDao.queryAllByLimit(zyOwnerParkRecord, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param zyOwnerParkRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ZyOwnerParkRecord insert(ZyOwnerParkRecord zyOwnerParkRecord) {
        this.zyOwnerParkRecordDao.insert(zyOwnerParkRecord);
        return zyOwnerParkRecord;
    }

    /**
     * 修改数据
     *
     * @param zyOwnerParkRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ZyOwnerParkRecord update(ZyOwnerParkRecord zyOwnerParkRecord) {
        this.zyOwnerParkRecordDao.update(zyOwnerParkRecord);
        return this.queryById(zyOwnerParkRecord.getRecordId());
    }

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long recordId) {
        return this.zyOwnerParkRecordDao.deleteById(recordId) > 0;
    }
}
