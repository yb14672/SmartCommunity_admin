package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.CommunityExcel;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.util.Result;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 小区 (ZyCommunityDto)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyCommunityService extends IService<ZyCommunity> {

    /**
     * 根据Id查询导出数据
     * @param ids
     * @return
     */
    List<CommunityExcel> selectByIds(ArrayList<Long> ids);

    /**
     * 修改小区信息
     * @param community
     * @param request
     * @return
     */
    Result updateCommunityById(ZyCommunity community,HttpServletRequest request);

    /**
     * 新增小区
     * @param community
     * @param request
     * @return
     * @throws Exception
     */
    Result insertCommunity(ZyCommunity community, HttpServletRequest request) throws Exception;

    /**
     * 分页加条件搜索
     * @param community
     * @param pageable
     * @return
     */
    Result selectAllByLimit(ZyCommunity community, Pageable pageable);


}

