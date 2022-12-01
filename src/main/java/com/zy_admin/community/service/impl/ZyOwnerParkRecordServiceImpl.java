package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyOwnerParkRecordDao;
import com.zy_admin.community.entity.ZyOwnerParkRecord;
import com.zy_admin.community.service.ZyOwnerParkRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 房屋绑定记录表 (ZyOwnerParkRecord)表服务实现类
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
@Service("zyOwnerParkRecordService")
public class ZyOwnerParkRecordServiceImpl extends ServiceImpl<ZyOwnerParkRecordDao, ZyOwnerParkRecord> implements ZyOwnerParkRecordService {
    @Resource
    private ZyOwnerParkRecordDao zyOwnerParkRecordDao;

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    @Override
    public ZyOwnerParkRecord queryById(Long recordId) {
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
