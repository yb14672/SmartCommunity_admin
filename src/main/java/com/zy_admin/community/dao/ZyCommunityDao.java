package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.CommunityDto;
import com.zy_admin.community.dto.CommunityExcel;
import com.zy_admin.community.entity.ZyCommunity;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 小区 (ZyCommunityDto)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyCommunityDao extends BaseMapper<ZyCommunity> {


    /**
     * 根据部门ID查询
     * @param idList
     * @return
     */
    List<ZyCommunity> selectCommunityByDeptId(@Param("idList") List<Integer> idList);

    /**
     * 根据小区id查询导出数据
     * @param ids
     * @return
     */
    List<CommunityExcel> selectByIds(@Param("ids") ArrayList<Long> ids);

    /**
     * 修改小区信息
     * @param community
     * @return
     */
    int updateCommunityById(ZyCommunity community);

    /**
     * 效验同一地区小区是否同名
     * @param community
     * @return
     */
    ZyCommunity checkZyCommunity(ZyCommunity community);

    /**
     * 新增小区
     * @param community
     * @return
     */
    int insertCommunity(ZyCommunity community);

    /**
     * 分页搜索条件
     * @param community
     * @param pageable
     * @return
     */
    List<CommunityDto> selectAllByLimit(@Param("community")ZyCommunity community, @Param("pageable")Pageable pageable);

    /**
     * 统计数据量
     * @param community
     * @return
     */
    Long count(@Param("community") ZyCommunity community);


}

