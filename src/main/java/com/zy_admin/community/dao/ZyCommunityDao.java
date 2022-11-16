package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyCommunity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 小区 (ZyCommunity)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Repository
public interface ZyCommunityDao extends BaseMapper<ZyCommunity> {
    /**
     * 查询所有的小区信息加分页
     * @return
     */
    List<ZyCommunity> selectCommunityLimit(@Param("zyCommunity")ZyCommunity zyCommunity, @Param("pageable")Pageable pageable);

    /**
     * 获取数量
     * @param zyCommunity
     * @return
     */
    Long count(@Param("zyCommunity") ZyCommunity zyCommunity);

    /**
     * 获取下面的所有子集
     * @param menuId
     * @return
     */
    List<Long> getChildrenById(String menuId);
}

