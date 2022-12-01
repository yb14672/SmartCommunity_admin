package com.zy_admin.community.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.dao.ZyOwnerParkDao;
import com.zy_admin.community.dto.ZyOwnerRoomDto;
import com.zy_admin.community.dto.ZyOwnerRoomDtoAll;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.service.ZyOwnerParkService;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerPark)表服务实现类
 *
 * @author makejava
 * @since 2022-12-01 15:18:41
 */
@Service("zyOwnerParkService")
public class ZyOwnerParkServiceImpl extends ServiceImpl<ZyOwnerParkDao, ZyOwnerPark> implements ZyOwnerParkService {
    @Resource
    private ZyOwnerParkDao zyOwnerParkDao;

    @Override
    public Result getOwnerParkList(ZyOwner zyOwner, Pageable pageable) {
//        //默认给失败的情况的状态
//        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
//        //查询出来的总数量
//        Long total = this.baseMapper.count(zyOwnerPark);
//        //默认的页面总数设为0
//        long pages;
//        if (total>0){
//            pages = total % pageable.getPageSize() == 0 ? total/pageable.getPageSize() : total/pageable.getPageSize() + 1;
//            pageable.setPages(pages);
//            //页码修正
//            pageable.setPageNum(pageable.getPageNum()<1 ? 1 : pageable.getPageNum());
//            pageable.setPageNum(pageable.getPageNum()>pages ? pages : pageable.getPageNum());
//            //设置起始下标
//            pageable.setIndex((pageable.getPageNum()-1)*pageable.getPageSize());
//        }else {
//            pageable.setPageNum(0);
//        }
//        pageable.setTotal(total);
//        List<ZyOwnerRoomDto> zyOwnerRoomList = this.baseMapper.selectAllOwnerRoomLimit(zyOwnerRoom, pageable);
//        ZyOwnerRoomDtoAll zyOwnerRoomDtoAll = new ZyOwnerRoomDtoAll(zyOwnerRoomList, pageable);
//        //集合存进去
//        result.setData(zyOwnerRoomDtoAll);
//        //给一个信号
//        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
//        return result;
        return null;
    }
//

    /**
     * 通过ID查询单条数据
     *
     * @param ownerParkId 主键
     * @return 实例对象
     */
    @Override
    public ZyOwnerPark queryById(Long ownerParkId) {
        return this.zyOwnerParkDao.queryById(ownerParkId);
    }

    /**
     * 分页查询
     *
     * @param zyOwnerPark 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<ZyOwnerPark> queryByPage(ZyOwnerPark zyOwnerPark, PageRequest pageRequest) {
//        long total = this.zyOwnerParkDao.count(zyOwnerPark);
//        return new PageImpl<>(this.zyOwnerParkDao.queryAllByLimit(zyOwnerPark, pageRequest), pageRequest, total);
        return null;
    }

    /**
     * 新增数据
     *
     * @param zyOwnerPark 实例对象
     * @return 实例对象
     */
    @Override
    public ZyOwnerPark insert(ZyOwnerPark zyOwnerPark) {
        this.zyOwnerParkDao.insert(zyOwnerPark);
        return zyOwnerPark;
    }

    /**
     * 修改数据
     *
     * @param zyOwnerPark 实例对象
     * @return 实例对象
     */
    @Override
    public ZyOwnerPark update(ZyOwnerPark zyOwnerPark) {
        this.zyOwnerParkDao.update(zyOwnerPark);
        return this.queryById(zyOwnerPark.getOwnerParkId());
    }

    /**
     * 通过主键删除数据
     *
     * @param ownerParkId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long ownerParkId) {
        return this.zyOwnerParkDao.deleteById(ownerParkId) > 0;
    }
}
