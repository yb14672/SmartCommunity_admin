package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.CommunityExcel;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.service.ZyCommunityService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 小区 (ZyCommunityDto)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Api(value = "zyCommunity", tags = {"小区 (ZyCommunityDto)表控制层"})
@RestController
@RequestMapping("zyCommunity")
public class ZyCommunityController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommunityService zyCommunityService;/**
     * 服务对象
     */
    @Resource
    private RequestUtil requestUtil;

    /**
     * 根据登录的管理员获取所在公司负责的小区
     *
     * @param request 前端请求
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "根据登录的管理员获取所在公司负责的小区", notes = "根据登录的管理员获取所在公司负责的小区", httpMethod = "GET")
    @GetMapping("/getCommunityIdByUserId")
    @PreAuthorize("hasAnyAuthority('system:community:query')")
    public Result getCommunityIdByUserId(HttpServletRequest request){
        String userId = requestUtil.getUserId(request);
        return this.zyCommunityService.getCommunityIdByUserId(userId);
    }

    /**
     * 根据所得id导出小区信息
     * @param ids 查询的小区主键id
     * @param response 前端响应
     * @return 返回成功和错误信息
     * @throws IOException 抛出异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<Long>", name = "ids", value = "查询的小区主键id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端响应", required = true)
    })
    @ApiOperation(value = "根据所得id导出小区信息", notes = "根据所得id导出小区信息", httpMethod = "GET")
    @MyLog(title = "小区信息", optParam = "#{ids}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    @PreAuthorize("hasAnyAuthority('system:community:export')")
    public Result getExcel(@RequestParam("ids") ArrayList<Long> ids, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<CommunityExcel> communityExcelList = zyCommunityService.selectByIds(ids);
        String fileName = URLEncoder.encode("小区信息", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), CommunityExcel.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("小区信息");
        excel.doWrite(communityExcelList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 修改数据
     *  @param community 更新的小区对象
     * @param request 前端请求
     * @return 修改的小区结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyCommunity", name = "community", value = "更新的小区对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping("/updateCommunity")
    @MyLog(title = "小区信息", optParam = "#{community}", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyAuthority('system:community:edit')")
    public Result update(@RequestBody ZyCommunity community,HttpServletRequest request) {
        SysUser user = requestUtil.getUser(request);
        community.setUpdateBy(user.getUserName());
        return this.zyCommunityService.updateCommunityById(community);
    }
    /**
     * 新增数据
     * @param community 新增的小区对象
     * @param request 前端请求
     * @return 新增的小区结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyCommunity", name = "community", value = "新增的小区对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping("/insertCommunity")
    @MyLog(title = "小区信息", optParam = "#{community}", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAnyAuthority('system:community:add')")
    public Result insert(@RequestBody ZyCommunity community, HttpServletRequest request) {
        SysUser user = requestUtil.getUser(request);
        community.setCreateBy(user.getUserName());
        return this.zyCommunityService.insertCommunity(community);
    }
    /**
     *  分页查询所有数据
     * @param community 查询的小区对象
     * @param pageable 查询的分页对象
     * @return 返回查询分页的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyCommunity", name = "community", value = "查询的小区对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "查询的分页对象", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping("/selectAll")
    @PreAuthorize("hasAnyAuthority('system:community:query')")
    public Result selectAll(ZyCommunity community, Pageable pageable){
        return zyCommunityService.selectAllByLimit(community,pageable);
    }
    /**
     * 通过主键查询单条数据
     * @param id 查询的小区id
     * @return 查询数据结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Serializable", name = "id", value = "查询的小区id", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('system:community:query')")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(this.zyCommunityService.getById(id),ResultTool.fail(ResultCode.SUCCESS));
    }
    /**
     * 删除数据
     * @param communityIds 存放小区id
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "communityIds", value = "存放小区id", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping("/deleteCommunity")
    @MyLog(title = "小区信息", optParam = "#{communityIds}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAnyAuthority('system:community:remove')")
    public Result delete(@RequestBody List<String> communityIds) {
        return this.zyCommunityService.deleteByIds(communityIds);
    }
}

