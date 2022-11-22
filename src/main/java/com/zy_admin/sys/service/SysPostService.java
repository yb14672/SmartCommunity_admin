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
     * 分页查询所有岗位数据
     * @param pageable    分页对象
     * @param sysPost 查询岗位对象
     * @return 所有查到的岗位结果集
     */
    Result selectPostByLimit(SysPost sysPost, Pageable pageable);
    /**
     * 添加岗位信息
     * @param sysPost 岗位对象
     * @return 添加的岗位结果集
     */
    Result addPost(SysPost sysPost);
    /**
     * 修改岗位信息
     * @param sysPost 岗位对象
     * @return 修改的岗位结果集
     */
    Result update(SysPost sysPost);
    /**
     * 岗位编码查重
     * @param sysPost 岗位对象
     * @param type 判断是新增0还是修改1
     * @return 成功或失败结果集
     */
    Boolean checkPostCode(int type, SysPost sysPost);
    /**
     * 岗位名查重
     * @param sysPost 岗位对象
     * @param type 判断是新增0还是修改1
     * @return  成功或失败结果集
     */
    Boolean checkPostName(int type, SysPost sysPost);
    /**
     * 根据id列表查询
     * @param postIds 岗位主键
     * @return  查询岗位的集合
     */
    List<SysPost> queryRoleById(ArrayList<Integer> postIds);
    /**
     * 获取所有岗位数据
     * @return 查询岗位的集合
     */
    List<SysPost> getPostLists();
    /**
     * 删除岗位
     * @param ids 岗位主键
     * @return 删除岗位结果集
     */
    Result deletePost(List<Integer> ids);
    /**
     * 获取所有的岗位
     * @return 返回所有岗位结果集
     */
    Result getAllPost();
}

