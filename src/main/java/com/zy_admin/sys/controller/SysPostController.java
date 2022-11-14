package com.zy_admin.sys.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysPost;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysPostService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.JwtUtils;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 岗位信息表(SysPost)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@RestController
@RequestMapping("sysPost")
public class SysPostController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysPostService sysPostService;
    @Resource
    private RequestUtil requestUtil;


    @GetMapping("/getAllPost")
    public Result getAllPost()
    {
        Result allPost = sysPostService.getAllPost();
        return allPost;
    }
    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysPost 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysPost> page, SysPost sysPost) {
        return success(this.sysPostService.page(page, new QueryWrapper<>(sysPost)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysPostService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysPost 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysPost sysPost) {
        return success(this.sysPostService.save(sysPost));
    }

    /**
     * 修改数据
     *
     * @param sysPost 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysPost sysPost) {
        return success(this.sysPostService.updateById(sysPost));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysPostService.removeByIds(idList));
    }


    @GetMapping("/getPostList")
    public Result getPostList(SysPost sysPost, Pageable pageable) {
        Result result = this.sysPostService.selectPostByLimit(sysPost, pageable);
        return result;
    }

    /**
     * 添加岗位
     *
     * @param request
     * @param sysPost
     * @return
     */
    @PostMapping("/addPost")
    public Result addPost(HttpServletRequest request, @RequestBody SysPost sysPost) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        SysUser user = this.requestUtil.getUser(request);
        sysPost.setCreateTime(LocalDateTime.now().toString());
        sysPost.setCreateBy(user.getUserName());
        Result result = this.sysPostService.addPost(sysPost);

        return result;
    }

    @PutMapping("/updatePost")
    public Result updatePost(HttpServletRequest request, @RequestBody SysPost sysPost) {
        SysUser user = this.requestUtil.getUser(request);
        sysPost.setUpdateBy(user.getUserName());
        sysPost.setUpdateTime(LocalDateTime.now().toString());
        return this.sysPostService.update(sysPost);
    }


    @PostMapping("/getExcel")
    public void getExcel(@RequestBody ArrayList<Integer> postIds, HttpServletResponse response) throws IOException {
        List<SysPost> sysPosts = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        //执行查询角色列表的sql语句,但不包括del_flag为2的
        if (postIds == null || postIds.size() == 0) {
            sysPosts = sysPostService.getPostLists();
        } else {
            //执行查询角色列表的sql语句
            sysPosts = sysPostService.queryRoleById(postIds);
        }
        String fileName = URLEncoder.encode("角色表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), SysPost.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(sysPosts);

    }

    @DeleteMapping("/deletePost")
    public Result deletePost(@RequestParam("ids") List<Integer> postIds) {
        return sysPostService.deletePost(postIds);
    }


}

