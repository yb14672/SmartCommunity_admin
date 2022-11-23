package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysPostDao;
import com.zy_admin.sys.dto.PostDto;
import com.zy_admin.sys.entity.SysPost;
import com.zy_admin.sys.service.SysPostService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * sys post service impl
 * 岗位信息表(SysPost)表服务实现类
 *
 * @author makejava
 * @date 2022/11/22
 * @since 2022-11-01 19:49:40
 */
@Service("sysPostService")
public class SysPostServiceImpl extends ServiceImpl<SysPostDao, SysPost> implements SysPostService {
    /**
     * 分页查询所有岗位数据
     * @param pageable    分页对象
     * @param sysPost 查询岗位对象
     * @return 所有查到的岗位结果集
     */
    @Override
    public Result selectPostByLimit(SysPost sysPost, Pageable pageable) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //获取总行数
        long total = this.baseMapper.count(sysPost);
        long pages = 0;
        if (total > 0) {
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<SysPost> sysPosts = this.baseMapper.queryAllByLimit(sysPost, pageable);
        PostDto postDto = new PostDto(sysPosts, pageable);
        result.setData(postDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 添加岗位信息
     * @param sysPost 岗位对象
     * @return 添加的岗位结果集
     */
    @Override
    public Result addPost(SysPost sysPost) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        if (checkPostCode(0, sysPost)) {
            if (checkPostName(0, sysPost)) {
                this.baseMapper.insert(sysPost);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_POST_NAME));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_POST_CODE));
        }
        return result;
    }
    /**
     * 修改岗位信息
     * @param sysPost 岗位对象
     * @return 修改的岗位结果集
     */
    @Override
    public Result update(SysPost sysPost) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        SysPost oldPost = this.baseMapper.queryPostById(sysPost.getPostId());
        //需要判断的字段名
        String[] fields = new String[]{"postCode", "postName", "postSort", "remark", "status"};
        if (ObjUtil.checkEquals(sysPost, oldPost,fields)){
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            return result;
        }
        if (checkPostCode(1, sysPost)) {
            if (checkPostName(1, sysPost)) {
                //修改前的数据
                SysPost post = this.baseMapper.selectPostById(sysPost.getPostId()+"");
                //判断是否有更改状态，若有则去查询
                if(!post.getStatus().equals(sysPost.getStatus())) {
                    List<Integer> ids=new ArrayList<Integer>();
                    ids.add(Math.toIntExact(post.getPostId()));
                    List<Integer> postIds = baseMapper.getPostIdFromUserPost(ids);
                    //判断在用户表中是否有配分配，若有则提示
                    if (postIds.size() > 0) {
                        result.setMeta(ResultTool.fail(ResultCode.POST_ASSIGNED));
                        return result;
                    }
                }
                this.baseMapper.updateById(sysPost);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_POST_NAME));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_POST_CODE));
        }
        return result;
    }

    /**
     * 岗位名查重
     * @param sysPost 岗位对象
     * @param type 判断是新增0还是修改1
     * @return  成功或失败结果集
     */
    @Override
    public Boolean checkPostName(int type, SysPost sysPost) {
        SysPost checkPostName = this.baseMapper.selectPostName(sysPost.getPostName());
        //添加时--必须为空
        if (type == 0) {
            if (checkPostName == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (checkPostName == null || checkPostName.getPostId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!checkPostName.getPostId().equals(sysPost.getPostId())) {
                return false;
            }
            return true;
        }
        return false;
    }
    /**
     * 岗位编码查重
     * @param sysPost 岗位对象
     * @param type 判断是新增0还是修改1
     * @return 成功或失败结果集
     */
    @Override
    public Boolean checkPostCode(int type, SysPost sysPost) {
        SysPost postCode = this.baseMapper.selectPostCode(sysPost.getPostCode());
        //添加时--必须为空
        if (type == 0) {
            if (postCode == null || postCode.getPostId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (postCode == null || postCode.getPostId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!postCode.getPostId().equals(sysPost.getPostId())) {
                return false;
            }
            return true;
        }
        return false;
    }
    /**
     * 根据id列表查询
     * @param postIds 岗位主键
     * @return  查询岗位的集合
     */
    @Override
    public List<SysPost> queryRoleById(ArrayList<Integer> postIds) {
        if (postIds != null) {
            postIds = postIds.size() == 0 ? null : postIds;
        }
        return this.baseMapper.queryRoleById(postIds);
    }
    /**
     * 获取所有岗位数据
     * @return 查询岗位的集合
     */
    @Override
    public List<SysPost> getPostLists() {
        return this.baseMapper.getPostLists();
    }
    /**
     * 删除岗位
     * @param ids 岗位主键
     * @return 删除岗位结果集
     */
    @Override
    public Result deletePost(List<Integer> ids) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        List<Integer> postIds = baseMapper.getPostIdFromUserPost(ids);
        if (postIds.size() > 0) {
            result.setMeta(ResultTool.fail(ResultCode.POST_ASSIGNED));
        } else {
            this.baseMapper.deletePost(ids);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
    /**
     * 获取所有的岗位
     * @return 返回所有岗位结果集
     */
    @Override
    public Result getAllPost() {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysPost> postLists = this.baseMapper.getPostLists();
        result.setData(postLists);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }


}

