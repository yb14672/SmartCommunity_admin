package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dao.ZyBuildingDao;
import com.zy_admin.community.dto.ZyBuildingDto;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.service.ZyBuildingService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 楼栋 (ZyBuilding)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@Service("zyBuildingService")
public class ZyBuildingServiceImpl extends ServiceImpl<ZyBuildingDao, ZyBuilding> implements ZyBuildingService {

    /**
     * 删除多条楼层
     * @param idList
     * @return
     */
    @Override
    public Result deleteByIdList(List<Integer> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是单个
        if (idList.size() == 1) {
            int i = this.baseMapper.deleteByIdList(idList);
            result.setData("删除成功，影响的行数：" + i);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        //多个就是批量删除
        } else {
                int i = this.baseMapper.deleteByIdList(idList);
                if (i >= 1) {
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
                }
            }
        return result;
    }

    @Override
    public Result selectBuildLimit(ZyBuilding zyBuilding, Pageable pageable) {
        //默认给失败的情况
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //满足条件的总数
        Long total = this.baseMapper.count(zyBuilding);
        //默认设置页面为0
        long pages = 0;
        if (total > 0) {
            //计算出总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<ZyBuilding> zyBuildingList = this.baseMapper.selectBuildLimit(zyBuilding, pageable);
        //封装一个dto，把对象和分页放进去
        ZyBuildingDto zyBuildingDto = new ZyBuildingDto(zyBuildingList, pageable);
        //存到data数据里面
        result.setData(zyBuildingDto);
        //返回信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 新增楼层
     *
     * @param zyBuilding
     * @return
     */
    @Override
    public Result insertZyBuilding(ZyBuilding zyBuilding) {
        zyBuilding.setCreateTime(new Date());
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断同一小区的楼层是否唯一,type为0是新增
        if (!selectZyBuildingByName(0, zyBuilding)) {
            result.setMeta(ResultTool.fail(ResultCode.BUILDING_REPEAT));
            return result;
        }
        try {
            //新增楼层
            int sysDictType1 = this.baseMapper.insertZyBuilding(zyBuilding);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            return new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        }

    }

    /**
     * 修改楼层
     *
     * @param zyBuilding
     * @return
     */
    @Override
    public Result updateZyBuilding(ZyBuilding zyBuilding) {
        //判断楼层的值有没有改变 zyBuilding1是原来的对象
        System.out.println(zyBuilding);
        ZyBuilding zyBuilding1 = this.baseMapper.getZyBuilding(zyBuilding.getBuildingId());
        System.out.println(zyBuilding1);
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断是不是完全相同
            if (checkEquals(zyBuilding1, zyBuilding)) {
                //type为1是修改
                if (selectZyBuildingByName(1, zyBuilding)) {
                    int i = this.baseMapper.updateZyBuilding(zyBuilding);
                    if (i == 1) {
                        result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.BUILDINGNAME_REPEAT));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.BUILD_IDENTICAL));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }
        return result;
    }

    /**
     * 查询对象
     *
     * @param id
     * @return
     */
    @Override
    public Result queryById(String id) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        ZyBuilding zyBuilding = this.baseMapper.queryById(id);
        if (zyBuilding != null || zyBuilding.getBuildingId() != null) {
            result.setData(zyBuilding);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 检查前端输入的值是否和数据库相等
     *
     * @param zyBuildingOld
     * @param zyBuildingNew
     * @return
     */
    public boolean checkEquals(ZyBuilding zyBuildingOld, ZyBuilding zyBuildingNew) {
        //判断名字，面积和备注是否相同
        System.out.println(zyBuildingOld);
        System.out.println(zyBuildingNew);
        if (zyBuildingOld.getBuildingName().equals(zyBuildingNew.getBuildingName()) && zyBuildingOld.getBuildingAcreage().equals(zyBuildingNew.getBuildingAcreage()) && zyBuildingOld.getRemark().equals(zyBuildingNew.getRemark())) {
            return false;
        }
        return true;
    }

    //    判断楼层号是否重复
    public boolean selectZyBuildingByName(int type, ZyBuilding zyBuilding) {
        ZyBuilding zyBuilding1 = this.baseMapper.selectZyBuildingByName(zyBuilding.getBuildingName());
//        类型为0是新增
        if (type == 0) {
//            判断是否为空
            if (zyBuilding1 == null || zyBuilding1.getBuildingName() == null) {
                return true;
            }
        } else {
//            修改
            if (zyBuilding1 == null || zyBuilding1.getBuildingName() == null) {
                return true;
//                判断房屋楼层是否唯一
            } else if (!zyBuilding1.getBuildingName().equals(zyBuilding.getBuildingName())) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}

