package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.VisitorGetExcelDto;
import com.zy_admin.community.dto.VisitorListDto;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.entity.ZyVisitor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 访客邀请 (ZyVisitor)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:04
 */


public interface ZyVisitorDao extends BaseMapper<ZyVisitor> {


    /**通过社区Id获取所有访客信息
     * @param communityId 社区Id
     * @return {@link List}<{@link VisitorGetExcelDto}>
     */
    List<VisitorGetExcelDto> getLists(String communityId);

    /**
     * 根据id查询访客
     *
     * @param visitorIds 游客id
     * @return {@link List}<{@link VisitorGetExcelDto}>
     */
    List<VisitorGetExcelDto> queryVisitorById(@Param("visitorIds") List<String> visitorIds);


    /**
     * 插入访客申请
     *
     * @param zyVisitor 访客信息
     */
    void insertVisitor(ZyVisitor zyVisitor);
    /**
     * 访客总数
     *
     * @param zyVisitor zy访客
     * @return long
     */
    long count(ZyVisitor zyVisitor);

    /**
     * 查询分页
     *
     * @param zyVisitor 访客
     * @param pageable  可分页
     * @return {@link List}<{@link VisitorListDto}>
     */
    List<VisitorListDto> queryAllByLimit(@Param("zyVisitor") ZyVisitor zyVisitor, @Param("pageable") Pageable pageable);


    /**
     * 更新状态
     *
     * @param zyVisitor 状态
     */
    @Update("update zy_visitor set status =#{status} where visitor_id =#{visitorId}")
    void updateStatus( ZyVisitor zyVisitor);

    /**
     * 得到主人房间用来判定是否可以邀请访客
     *
     * @param ownerId 所有者id
     * @return {@link ZyOwnerRoom}
     */
    @Select("select * from zy_owner_room where owner_id=#{ownerId}")
    ZyOwnerRoom getOwnerRoom(String ownerId);

}

