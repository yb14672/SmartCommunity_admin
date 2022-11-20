package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyBuildingDao;
import com.zy_admin.community.dto.ZyBuildingDto;
import com.zy_admin.community.dto.ZyBuildingDtoAll;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.service.ZyBuildingService;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 楼栋 (ZyBuilding)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@Service("zyBuildingService")
public class ZyBuildingServiceImpl extends ServiceImpl<ZyBuildingDao, ZyBuilding> implements ZyBuildingService {

    @Autowired
    private SnowflakeManager snowflakeManager;

    @Resource
    private SysUserDao sysUserDao;

    /**
     * 导出选中的楼层
     *
     * @param buildingIds
     * @return
     */
    @Override
    public List<ZyBuilding> queryZyBuildingById(ArrayList<String> buildingIds) {
        //如果有选中列表，就执行导出多个
        if (buildingIds != null) {
            buildingIds = buildingIds.size() == 0 ? null : buildingIds;
        }
        return baseMapper.queryZyBuildingById(buildingIds);
    }

    /**
     * 导出所有的楼层
     *
     * @return
     */
    @Override
    public List<ZyBuilding> getBuildingLists(String communityId) {
        return baseMapper.getBuildingLists(communityId);
    }

    /**
     * 删除多条楼层
     *
     * @param idList
     * @return
     */
    @Override
    public Result deleteByIdList(List<String> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        Long aLong = this.baseMapper.selectChild(idList);
        //判断下面有没有子集
        if (aLong > 0) {
            result.setMeta(ResultTool.fail(ResultCode.BUILD_HAVA_CHILD));
        } else {
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
        List<ZyBuildingDto> zyBuildingList = this.baseMapper.selectBuildLimit(zyBuilding, pageable);
        //封装一个dto，把对象和分页放进去

        ZyBuildingDtoAll zyBuildingDtoAll = new ZyBuildingDtoAll(zyBuildingList, pageable);
        //存到data数据里面
        result.setData(zyBuildingDtoAll);
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
    public Result insertZyBuilding(ZyBuilding zyBuilding, HttpServletRequest request) throws Exception {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        Long now = System.currentTimeMillis();
        zyBuilding.setBuildingCode("BUILDING_" + now.toString().substring(0, 13));
        zyBuilding.setBuildingId(snowflakeManager.nextId() + "");
        zyBuilding.setCreateTime(LocalDateTime.now().toString());
        String id = JwtUtil.getMemberIdByJwtToken(request);
        zyBuilding.setCreateBy(sysUserDao.getUserById(id).getUserName());
        //判断同一小区的楼层是否唯一,type为0是新增
        if (!selectZyBuildingByName(0, zyBuilding)) {
            result.setMeta(ResultTool.fail(ResultCode.BUILDING_NAME_REPEAT));
            return result;
        }
        try {
            //新增楼层
            int sysDictType1 = this.baseMapper.insertZyBuilding(zyBuilding);
            if (sysDictType1 == 1) {
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                result.setData("新增成功");
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    /**
     * 修改楼层
     *
     * @param zyBuilding
     * @return
     */
    @Override
    public Result updateZyBuilding(ZyBuilding zyBuilding, HttpServletRequest request) {
        //判断楼层的值有没有改变 zyBuilding1是原来的对象
        ZyBuilding zyBuilding1 = this.baseMapper.getZyBuilding(zyBuilding.getBuildingId());
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断是不是完全相同
            String[] fields = new String[]{"buildingName", "buildingAcreage", "remark"};
            if (!ObjUtil.checkEquals(zyBuilding1, zyBuilding, fields)) {
                //type为1是修改
                if (selectZyBuildingByName(1, zyBuilding)) {
                    String id = JwtUtil.getMemberIdByJwtToken(request);
                    zyBuilding.setUpdateTime(LocalDateTime.now().toString());
                    zyBuilding.setUpdateBy(sysUserDao.getUserById(id).getUserName());
                    int i = this.baseMapper.updateZyBuilding(zyBuilding);
                    if (i == 1) {
                        result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.BUILDING_NAME_REPEAT));
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
     * 判断楼层号是否重复
     *
     * @param type
     * @param zyBuilding
     * @return
     */
    public boolean selectZyBuildingByName(int type, ZyBuilding zyBuilding) {
        ZyBuilding zyBuilding1 = this.baseMapper.selectZyBuildingByName(zyBuilding.getBuildingName(), zyBuilding.getCommunityId());
        //类型为0是新增
        if (type == 0) {
            //判断是否为空
            if (zyBuilding1 == null || zyBuilding1.getBuildingName() == null) {
                return true;
            }
        } else {
            //修改
            if (zyBuilding1 == null || zyBuilding1.getBuildingName() == null) {
                return true;
                //判断房屋楼层是否唯一
            } else if (!zyBuilding1.getBuildingId().equals(zyBuilding.getBuildingId())) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}

