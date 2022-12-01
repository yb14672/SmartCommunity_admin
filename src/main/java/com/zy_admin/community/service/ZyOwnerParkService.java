package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerPark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 房屋绑定表 (ZyOwnerPark)表服务接口
 *
 * @author makejava
 * @since 2022-12-01 15:18:39
 */
public interface ZyOwnerParkService extends IService<ZyOwnerPark> {

    Result getOwnerParkList(ZyOwner zyOwner, Pageable pageable);
    /**
     * 通过ID查询单条数据
     *
     * @param ownerParkId 主键
     * @return 实例对象
     */
    ZyOwnerPark queryById(Long ownerParkId);

    /**
     * 分页查询
     *
     * @param zyOwnerPark 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<ZyOwnerPark> queryByPage(ZyOwnerPark zyOwnerPark, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param zyOwnerPark 实例对象
     * @return 实例对象
     */
    ZyOwnerPark insert(ZyOwnerPark zyOwnerPark);

    /**
     * 修改数据
     *
     * @param zyOwnerPark 实例对象
     * @return 实例对象
     */
    ZyOwnerPark update(ZyOwnerPark zyOwnerPark);

    /**
     * 通过主键删除数据
     *
     * @param ownerParkId 主键
     * @return 是否成功
     */
    boolean deleteById(Long ownerParkId);

}
