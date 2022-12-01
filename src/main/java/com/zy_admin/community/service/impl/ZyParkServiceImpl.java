package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyParkDao;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyParkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (ZyPark)表服务实现类
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
@Service("zyParkService")
public class ZyParkServiceImpl extends ServiceImpl<ZyParkDao, ZyPark> implements ZyParkService {
    @Resource
    private ZyParkDao zyParkDao;

    /**
     * 通过ID查询单条数据
     *
     * @param parkId 主键
     * @return 实例对象
     */
    @Override
    public ZyPark queryById(Long parkId) {
        return this.zyParkDao.queryById(parkId);
    }

    /**
     * 分页查询
     *
     * @param zyPark      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<ZyPark> queryByPage(ZyPark zyPark, PageRequest pageRequest) {
        long total = this.zyParkDao.count(zyPark);
        return new PageImpl<>(this.zyParkDao.queryAllByLimit(zyPark, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param zyPark 实例对象
     * @return 实例对象
     */
    @Override
    public ZyPark insert(ZyPark zyPark) {
        this.zyParkDao.insert(zyPark);
        return zyPark;
    }

    /**
     * 修改数据
     *
     * @param zyPark 实例对象
     * @return 实例对象
     */
    @Override
    public ZyPark update(ZyPark zyPark) {
        this.zyParkDao.update(zyPark);
        return this.queryById(zyPark.getParkId());
    }

    /**
     * 通过主键删除数据
     *
     * @param parkId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long parkId) {
        return this.zyParkDao.deleteById(parkId) > 0;
    }
}
