package com.zy_admin.sys.controller;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.entity.SysPost;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysPostService;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * 岗位信息表(SysPost)表控制层
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
    /**
     * 获取所有的岗位
     * @return 返回所有岗位结果集
     */
    @GetMapping("/getAllPost")
    public Result getAllPost() {
        return sysPostService.getAllPost();
    }
    /**
     * 分页查询所有岗位数据
     * @param pageable    分页对象
     * @param sysPost 查询岗位对象
     * @return 所有查到的岗位结果集
     */
    @GetMapping("/getPostList")
    public Result getPostList(SysPost sysPost, Pageable pageable) {
        return this.sysPostService.selectPostByLimit(sysPost, pageable);
    }
    /**
     * 添加岗位信息
     * @param request 前端请求
     * @param sysPost 岗位对象
     * @return 添加的岗位结果集
     */
    @PostMapping("/addPost")
    @MyLog(title = "岗位管理", optParam = "#{sysPost}", businessType = BusinessType.INSERT)
    public Result addPost(HttpServletRequest request, @RequestBody SysPost sysPost) {
        SysUser user = this.requestUtil.getUser(request);
        sysPost.setCreateTime(LocalDateTime.now().toString());
        sysPost.setCreateBy(user.getUserName());
        return this.sysPostService.addPost(sysPost);
    }
    /**
     * 修改岗位信息
     * @param request 前端请求
     * @param sysPost 岗位对象
     * @return 修改的岗位结果集
     */
    @PutMapping("/updatePost")
    @MyLog(title = "岗位管理", optParam = "#{sysPost}", businessType = BusinessType.UPDATE)
    public Result updatePost(HttpServletRequest request, @RequestBody SysPost sysPost) {
        SysUser user = this.requestUtil.getUser(request);
        sysPost.setUpdateBy(user.getUserName());
        sysPost.setUpdateTime(LocalDateTime.now().toString());
        return this.sysPostService.update(sysPost);
    }
    /**
     * 岗位信息导出
     * @param postIds 岗位主键
     * @param response 前端响应
     * @return 导出的岗位信息结果集
     * @throws IOException 抛出数据流异常
     */
    @MyLog(title = "岗位管理", optParam = "#{postIds}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    public Result getExcel(@RequestParam("postIds") ArrayList<Integer> postIds, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysPost> sysPosts;
        if (postIds == null || postIds.size() == 0) {
            sysPosts = sysPostService.getPostLists();
        } else {
            //执行查询角色列表的sql语句
            sysPosts = sysPostService.queryRoleById(postIds);
        }
        String fileName = URLEncoder.encode("岗位信息表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), SysPost.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("岗位信息");
        excel.doWrite(sysPosts);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 删除岗位
     * @param postIds 岗位主键
     * @return 删除岗位结果集
     */
    @DeleteMapping("/deletePost")
    @MyLog(title = "岗位管理", optParam = "#{postIds}", businessType = BusinessType.DELETE)
    public Result deletePost(@RequestParam("ids") List<Integer> postIds) {
        return sysPostService.deletePost(postIds);
    }
}

