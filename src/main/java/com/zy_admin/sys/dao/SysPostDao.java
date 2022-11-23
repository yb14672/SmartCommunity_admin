package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysPost;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位信息表(SysPost)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysPostDao extends BaseMapper<SysPost> {
    /**
     * 获取总行数
     * @param sysPost 系统岗位
     * @return 查询岗位数据条数
     */
    long count(SysPost sysPost);

    /**
     * 根据条件搜索分页
     * @param sysPost  系统发布
     * @param pageable 分页对象
     * @return 岗位集合
     */
    List<SysPost>queryAllByLimit(@Param("sysPost") SysPost sysPost, @Param("pageable") Pageable pageable);
    /**
     * 通过主键修改岗位
     * @param sysPost 系统发布
     * @return 更新条数
     */
    @Override
    int updateById(SysPost sysPost);
    /**
     * 查询名称
     * @param postName 文章名字
     * @return 岗位对象
     */
    @Select("select * from sys_post where post_name=#{postName} ")
    SysPost selectPostName(String postName);

    /**
     * 查询名称
     * @param postId 岗位id
     * @return 岗位对象
     */
    @Select("select * from sys_post where post_id=#{postId} ")
    SysPost selectPostById(String postId);
    /**
     * 查询邮政编码
     * @param postCode 邮政编码
     * @return  岗位对象
     */
    @Select("select * from sys_post where post_code=#{postCode} ")
    SysPost selectPostCode(String postCode);

    /**
     * 勾选用户获取excel
     * @param roleIds 角色id
     * @return  岗位集合
     */
    List<SysPost> queryRoleById(@Param("list") ArrayList<Integer> roleIds);
    /**
     * 所有用户获取excel
     * @return 岗位集合
     */
    List<SysPost> getPostLists();
    /**
     * 查询用户岗位表中的岗位ID
     * @param ids 岗位id
     * @return id集合
     */
    List<Integer> getPostIdFromUserPost(@Param("ids") List<Integer> ids);
    /**
     * 岗位删除
     * @param sysPosts 岗位对象
     * @return int
     */
    int deletePost(@Param("idList") List<Integer> sysPosts);
    /**
     * 查询后通过id
     * @param postId 岗位id
     * @return 岗位对象
     */
    @Select("select * from sys_post where post_id = #{postId}")
    SysPost queryPostById(long postId);
}

