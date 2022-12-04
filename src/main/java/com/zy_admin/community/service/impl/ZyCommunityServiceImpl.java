package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyBuildingDao;
import com.zy_admin.community.dao.ZyCommunityDao;
import com.zy_admin.community.dto.CommunityDto;
import com.zy_admin.community.dto.CommunityExcel;
import com.zy_admin.community.dto.ZyCommunityDto;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.service.ZyCommunityService;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 小区 (ZyCommunityDto)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Service("zyCommunityService")
public class ZyCommunityServiceImpl extends ServiceImpl<ZyCommunityDao, ZyCommunity> implements ZyCommunityService {
    /**
     * 服务对象
     */
    @Resource
    private SnowflakeManager snowflakeManager;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private ZyBuildingDao zyBuildingDao;

    /**
     * 删除数据
     *
     * @param ids 存放小区id
     * @return 删除结果
     */
    @Override
    public Result deleteByIds(List<String> ids) {
        Result result = new Result("删除失败", ResultTool.fail(ResultCode.COMMUNITY_UPDATE_FAIL));
        List<ZyBuilding> buildingListsByIds = zyBuildingDao.getBuildingListsByIds(ids);
        if (buildingListsByIds.size() == 0) {
            int i = this.baseMapper.deleteBatchIds(ids);
            if (i >= 1) {
                result.setData("删除成功");
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.COMMUNITY_HAVE_CHILD));
        }
        return result;
    }

    /**
     * 根据Id查询导出数据
     *
     * @param ids 存放小区id数组
     * @return 小区导出集合
     */
    @Override
    public List<CommunityExcel> selectByIds(ArrayList<Long> ids) {
        return this.baseMapper.selectByIds(ids);
    }

    /**
     * 修改数据
     *
     * @param community 更新的小区对象
     * @return 修改的小区结果集
     */
    @Override
    public Result updateCommunityById(ZyCommunity community) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMUNITY_UPDATE_FAIL));
        try {
            ZyCommunity zyCommunity1 = this.baseMapper.selectById(community.getCommunityId());
            String[] fields = new String[]{"communityName", "communityDetailedAddress", "communityProvenceCode", "communityCityCode", "communityTownCode", "remark"};
            if (ObjUtil.checkEquals(community, zyCommunity1, fields)) {
                result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
                return result;
            }
            //判断地区Code是否为空，为空就是修改物业
            if (!"".equals(community.getCommunityProvenceCode())) {
                /* 验证同一地区小区名是否重复 */
                ZyCommunity zyCommunity = this.baseMapper.checkZyCommunity(community);
                if (zyCommunity != null) {
                    if (community.getCommunityName().equals(zyCommunity.getCommunityName())) {
                        result.setMeta(ResultTool.fail(ResultCode.REPEAT_COMMUNITY_NAME));
                        return result;
                    }
                }
            }
            community.setUpdateTime(LocalDateTime.now().toString());
            int i = this.baseMapper.updateCommunityById(community);
            result.setData(i);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 新增数据
     *
     * @param community 新增的小区对象
     * @return 新增的小区结果集
     */
    @Override
    public Result insertCommunity(ZyCommunity community) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMUNITY_ADD_FAIL));
        try {
            /* 验证同一地区小区名是否重复 */
            ZyCommunity zyCommunity = this.baseMapper.checkZyCommunity(community);
            if (zyCommunity != null) {
                if (community.getCommunityName().equals(zyCommunity.getCommunityName())) {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_COMMUNITY_NAME));
                    return result;
                }
            }
            long now = System.currentTimeMillis();
            community.setCommunityId(snowflakeManager.nextId() + "");
            community.setCommunityCode("COMMUNITY_" + Long.toString(now).substring(0, 13));
            community.setCreateTime(LocalDateTime.now().toString());
            int i = this.baseMapper.insertCommunity(community);
            result.setData("新增成功，影响的行数：" + i);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param community 查询的小区对象
     * @param pageable  查询的分页对象
     * @return 返回查询分页的结果集
     */
    @Override
    public Result selectAllByLimit(ZyCommunity community, Pageable pageable) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMUNITY_GET_FAIL));
        try {
            long total = this.baseMapper.count(community);
            pageable.setTotal(total);
            long pages;
            if (total > 0) {
                if (pageable.getPageSize() != 0) {
                    //总页码数
                    pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
                    pageable.setPages(pages);
                    //页码修正
                    pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
                    pageable.setPageNum(Math.min(pageable.getPageNum(), pages));
                    //设置起始下标
                    pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
                } else {
                    pageable.setPageNum(1);
                    pageable.setPageSize(0);
                    pageable.setIndex(0);
                }
            } else {
                pageable.setPageNum(0);
            }
            List<CommunityDto> communityList = this.baseMapper.selectAllByLimit(community, pageable);
            result.setData(new ZyCommunityDto(communityList, pageable));
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 根据登录的管理员获取所在公司负责的小区
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getCommunityIdByUserId(String userId) {
        Result result = new Result("当前公司没有负责的物业", ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyCommunity> communityIdByUserId = this.baseMapper.getCommunityIdByUserId(userId);
        if(!communityIdByUserId.isEmpty()){
            result.setData(communityIdByUserId);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
}

