package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyParkDao;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyParkService;
import com.zy_admin.util.ResultTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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


    @Override
    public Result deletePark(List<String> parkIds) {
        Result result = new Result();
        List<ZyOwnerPark> zyOwnerParks = this.baseMapper.selectOwnerParkByParkId(parkIds);
        if (zyOwnerParks.size()>0)
        {
            result.setMeta(ResultTool.fail(ResultCode.DELETED_PARK_FAIL));
        }else
        {
            try {
                this.baseMapper.deletedPark(parkIds);
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            }
        }
        return result;
    }

    /**
     *修改停车位
     *
     * @param zyPark 停车位
     * @return {@link Result}
     */
    @Override
    public Result updatePark(ZyPark zyPark) {
        Result result = new Result();
        try {
            this.baseMapper.updatePark(zyPark);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.UPDATE_PARK_FAIL));
        }
        return result;
    }

    /**
     * 新增停车位
     *
     * @param zyPark 停车位
     * @return {@link Result}
     */
    @Override
    public Result insertPark(ZyPark zyPark) {
        Result result = new Result();
        try {
            this.baseMapper.insertPark(zyPark);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.INSERT_PARK_FAIL));
        }
        return result;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param parkId 主键
     * @return 实例对象
     */
    @Override
    public ZyPark queryById(String parkId) {
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
        return this.queryById(zyPark.getParkId()+"");
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
