package com.zy_admin.community.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.CommunityDto;
import com.zy_admin.community.dto.CommunityExcel;
import com.zy_admin.community.dto.AreaInfo;
import com.zy_admin.community.entity.ZyCommunity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;
/**
 * 小区 (ZyCommunityDto)表数据库访问层
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyCommunityDao extends BaseMapper<ZyCommunity> {
    /**
     * 根据部门ID查询小区
     * @param idList 用于查询的部门id 集合
     * @return 查询的小区集合
     */
    List<ZyCommunity> selectCommunityByDeptId(@Param("idList") List<Integer> idList);
    /**
     * 根据Id查询导出数据
     * @param ids  存放小区id数组
     * @return 小区导出集合
     */
    List<CommunityExcel> selectByIds(@Param("ids") ArrayList<Long> ids);
    /**
     * 修改数据
     *  @param community 更新的小区对象
     * @return 修改的小区结果集
     */
    int updateCommunityById(ZyCommunity community);
    /**
     * 检验同一地区小区是否同名
     * @param community 检查的小区对象
     * @return 返回小区对象
     */
    ZyCommunity checkZyCommunity(ZyCommunity community);
    /**
     * 新增数据
     * @param community 新增的小区对象
     * @return 新增的小区结果集
     */
    int insertCommunity(ZyCommunity community);
    /**
     *  分页查询所有数据
     * @param community 查询的小区对象
     * @param pageable 查询的分页对象
     * @return 返回查询分页的结果集
     */
    List<CommunityDto> selectAllByLimit(@Param("community")ZyCommunity community, @Param("pageable")Pageable pageable);
    /**
     * 统计数据量
     * @param community 查询的小区对象
     * @return 返回查询条数
     */
    Long count(@Param("community") ZyCommunity community);


    /**
     * 根据登录的管理员获取所在公司负责的小区
     *
     * @param userId 用户id
     * @return {@link List}<{@link ZyCommunity}>
     */
    List<ZyCommunity> getCommunityIdByUserId(String userId);

    /**
     * 获得省
     *
     * @return 查询省的小区总数量
     */
    List<AreaInfo> getProvinces();

    /**
     * 得到城市
     *
     * @param provence 数量
     * @return 查询省的小区总数量
     */
    @Select("select community_city_code as name , count(*) as value FROM zy_community WHERE community_provence_code = #{provence} GROUP BY community_city_code")
    List<AreaInfo> getCities(String provence);
}

