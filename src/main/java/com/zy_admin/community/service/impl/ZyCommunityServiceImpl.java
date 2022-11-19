package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyCommunityDao;
import com.zy_admin.community.dto.CommunityDto;
import com.zy_admin.community.dto.CommunityExcel;
import com.zy_admin.community.dto.ZyCommunityDto;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.service.ZyCommunityService;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import com.zy_admin.util.*;
import com.zy_admin.common.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private SnowflakeManager snowflakeManager;
    @Resource
    private SysUserDao sysUserDao;

    @Override
    public List<CommunityExcel> selectByIds(ArrayList<Long> ids) {
        return this.baseMapper.selectByIds(ids);
    }

    @Override
    public Result updateCommunityById(ZyCommunity community, HttpServletRequest request) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMUNITY_UPDATE_FAIL));
        try {
            //判断地区Code是否为空，为空就是修改物业
            if (community.getCommunityProvenceCode() != "") {
                /* 验证同一地区小区名是否重复 */
                ZyCommunity zyCommunity = this.baseMapper.checkZyCommunity(community);
                if (zyCommunity != null) {
                    if (community.getCommunityName().equals(zyCommunity.getCommunityName())) {
                        result.setMeta(ResultTool.fail(ResultCode.REPEAT_COMMUNITY_NAME));
                        return result;
                    }
                }
            }
            String id = JwtUtil.getMemberIdByJwtToken(request);
            community.setUpdateBy(sysUserDao.getUserById(id).getUserName());
            community.setUpdateTime(LocalDateTime.now().toString());
            int i = this.baseMapper.updateCommunityById(community);
            result.setData(i);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public Result insertCommunity(ZyCommunity community, HttpServletRequest request) throws Exception {
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
            Long now = System.currentTimeMillis();
            String id = JwtUtil.getMemberIdByJwtToken(request);
            community.setCommunityId(snowflakeManager.nextId() + "");
            community.setCommunityCode("COMMUNITY_" + now.toString().substring(0, 13));
            community.setCreateBy(sysUserDao.getUserById(id).getUserName());
            community.setCreateTime(LocalDateTime.now().toString());
            int i = this.baseMapper.insertCommunity(community);
            result.setData(i);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public Result selectAllByLimit(ZyCommunity community, Pageable pageable) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMUNITY_GET_FAIL));
        try {
            long total = this.baseMapper.count(community);
            pageable.setTotal(total);
            long pages = 0;
            if (total > 0) {
                if (pageable.getPageSize() != 0) {
                    //总页码数
                    pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
                    pageable.setPages(pages);
                    //页码修正
                    pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
                    pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
                    //设置起始下标
                    pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
                }else {
                    pageable.setPageNum(1);
                    pageable.setPageSize(0);
                    pageable.setIndex(0);
                }
            } else {
                pageable.setPageNum(0);
            }
            List<CommunityDto> communityList = this.baseMapper.selectAllByLimit(community, pageable);
            result.setData(new ZyCommunityDto(communityList, pageable));
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }
}

