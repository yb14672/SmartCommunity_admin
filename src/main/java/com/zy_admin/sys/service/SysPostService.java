package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysPost;
import com.zy_admin.util.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位信息表(SysPost)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysPostService extends IService<SysPost> {
    /**
     * 分页查询岗位
     * @param sysPost
     * @param pageable
     * @return
     */
    Result selectPostByLimit(SysPost sysPost, Pageable pageable);

    /**
     * 添加岗位
     * @param sysPost
     * @return
     */
    Result addPost(SysPost sysPost);

    /**
     * 修改
     * @param sysPost
     * @return
     */
    Result update(SysPost sysPost);


    /**
     * 岗位编码查重
     * @param sysPost
     * @param type
     * @return
     */
    Boolean checkPostCode(int type, SysPost sysPost);

    /**
     * 岗位名查重
     * @param sysPost
     * @param type
     * @return
     */
    Boolean checkPostName(int type, SysPost sysPost);


    /**
     * 根据id列表查询
     * @param postIds
     * @param
     * @return
     */
    List<SysPost> queryRoleById(ArrayList<Integer> postIds);

    /**
     * 获取所有角色
     * @return
     */
    List<SysPost> getPostLists();


    /**
     * 批量删除岗位
     * @param ids
     * @return
     */
    Result deletePost(List<Integer> ids);


    Result getAllPost();

}

