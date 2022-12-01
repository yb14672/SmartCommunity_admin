package com.zy_admin.community.service;

import com.zy_admin.community.entity.ZyOwnerParkRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 房屋绑定记录表 (ZyOwnerParkRecord)表服务接口
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
public interface ZyOwnerParkRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    ZyOwnerParkRecord queryById(Long recordId);

    /**
     * 分页查询
     *
     * @param zyOwnerParkRecord 筛选条件
     * @param pageRequest       分页对象
     * @return 查询结果
     */
    Page<ZyOwnerParkRecord> queryByPage(ZyOwnerParkRecord zyOwnerParkRecord, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param zyOwnerParkRecord 实例对象
     * @return 实例对象
     */
    ZyOwnerParkRecord insert(ZyOwnerParkRecord zyOwnerParkRecord);

    /**
     * 修改数据
     *
     * @param zyOwnerParkRecord 实例对象
     * @return 实例对象
     */
    ZyOwnerParkRecord update(ZyOwnerParkRecord zyOwnerParkRecord);

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 是否成功
     */
    boolean deleteById(Long recordId);

}
