package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysPostDao;
import com.zy_admin.sys.dto.PostDto;
import com.zy_admin.sys.entity.SysPost;
import com.zy_admin.sys.service.SysPostService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 岗位信息表(SysPost)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysPostService")
public class SysPostServiceImpl extends ServiceImpl<SysPostDao, SysPost> implements SysPostService {

    @Resource
    private SysPostDao sysPostDao;

    @Override
    public Result selectPostByLimit(SysPost sysPost, Pageable pageable) {
        Result result = new Result();
        //获取总行数
        long total = this.baseMapper.count(sysPost);
        //总页数
        long pages = 0L;
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
        System.out.println(sysPosts.toString());
        PostDto postDto = new PostDto(sysPosts, pageable);
        result.setData(postDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));

        return result;


    }

    @Override
    public Result addPost(SysPost sysPost) {
        Result result = new Result();
        if (checkPostCode(0, sysPost)) {
            if (checkPostName(0, sysPost)) {
                this.baseMapper.insert(sysPost);
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                return result;

            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_POST_NAME));
                return result;
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_POST_CODE));
            return result;
        }
    }

    @Override
    public Result update(SysPost sysPost) {
        Result result = new Result();
        if (checkPostCode(1, sysPost)) {
            if (checkPostName(1, sysPost)) {
                this.baseMapper.updateById(sysPost);
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                return result;

            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_POST_NAME));
                return result;
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_POST_CODE));
            return result;
        }

    }


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


    @Override
    public List<SysPost> queryRoleById(ArrayList<Integer> roleIds) {
        if (roleIds != null) {
            roleIds = roleIds.size() == 0 ? null : roleIds;
        }
        return this.baseMapper.queryRoleById(roleIds);
    }

    @Override
    public List<SysPost> getRoleLists() {
        return this.baseMapper.getRoleLists();
    }

    @Override
    public Result deletePost(List<Integer> ids) {
        Result result = new Result();
        List<Integer> postIds = sysPostDao.getPostIdFromUserPost(ids);
        if (postIds.size() > 0) {
            result.setMeta(ResultTool.fail(ResultCode.DEPT_ASSIGNED));
        } else {
            this.baseMapper.deletePost(ids);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }


}

