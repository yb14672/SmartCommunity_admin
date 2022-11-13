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
     * @param sysPost
     * @return
     */
    long count(SysPost sysPost);

    /**
     * 根据条件搜索分页
     * @param sysPost
     * @param pageable
     * @return
     */
    List<SysPost>queryAllByLimit(@Param("sysPost") SysPost sysPost, @Param("pageable") Pageable pageable);


    /**
     * 通过主键修改岗位
     * @param sysPost
     * @return
     */
    @Override
    int updateById(SysPost sysPost);

    /**
     * 查询名称
     * @param postName
     * @return
     */
    @Select("select * from sys_post where post_name=#{postName} ")
    SysPost selectPostName(String postName);


    /**
     * 查询编码
     * @param postCode
     * @return
     */
    @Select("select * from sys_post where post_code=#{postCode} ")
    SysPost selectPostCode(String postCode);


    /**
     * 勾选用户获取excel
     * @param roleIds
     * @return
     */
    List<SysPost> queryRoleById(@Param("list") ArrayList<Integer> roleIds);


    /**
     * 所有用户获取excel
     * @return
     */
    List<SysPost> getRoleLists();

    /**
     * 查询用户岗位表中的岗位ID
     * @return
     */

    List<Integer> getPostIdFromUserPost(@Param("ids") List<Integer> ids);


    /**
     * 用户删除
     * @param sysPosts
     * @return
     */
    int deletePost(@Param("idList") List<Integer> sysPosts);
}

