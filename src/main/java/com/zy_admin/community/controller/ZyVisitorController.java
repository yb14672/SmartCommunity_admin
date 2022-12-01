package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.VisitorGetExcelDto;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyVisitor;
import com.zy_admin.community.service.ZyVisitorService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 访客邀请 (ZyVisitor)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Api(value = "zyVisitor", tags = {"访客邀请 (ZyVisitor)表控制层"})
@RestController
@RequestMapping("zyVisitor")
public class ZyVisitorController extends ApiController {



    /**
     * 服务对象
     */
    @Resource
    private ZyVisitorService zyVisitorService;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private SnowflakeManager snowflakeManager;

    /**
     * 获取excel
     *
     * @param visitorIds 游客id
     * @param response   响应
     * @return {@link Result}
     * @throws IOException ioexception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<String>", name = "visitorIds", value = "游客id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "响应", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "communityId", value = "", required = true)
    })
    @ApiOperation(value = "获取excel", notes = "获取excel", httpMethod = "GET")
    @GetMapping("/getExcel")
    @MyLog(title = "访客管理", optParam = "#{visitorIds}", businessType = BusinessType.EXPORT)
    public Result getExcel(@RequestParam("visitorIds") List<String> visitorIds, HttpServletResponse response,String communityId) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<VisitorGetExcelDto> visitorGetExcelDtos;
        //如果前台传的集合为空或者长度为0.则全部导出。
        if (visitorIds == null || visitorIds.isEmpty()) {
            visitorGetExcelDtos = zyVisitorService.getLists(communityId);
        } else {
            //执行查询用户列表的sql语句
            visitorGetExcelDtos = zyVisitorService.queryVisitorById(visitorIds);
        }
        String fileName = URLEncoder.encode("访客信息表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), VisitorGetExcelDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("访客信息");
        excel.doWrite(visitorGetExcelDtos);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }


    /**
     * 游客邀请
     *
     * @param request   请求
     * @param zyVisitor zy访客
     * @return {@link Result}
     * @throws Exception 异常
     */

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "ZyVisitor", name = "zyVisitor", value = "zy访客", required = true)
    })
    @ApiOperation(value = "插入访客", notes = "插入访客", httpMethod = "POST")
    @PostMapping("/insertVisitor")
    public Result insertVisitor(HttpServletRequest request,   @RequestBody ZyVisitor zyVisitor) throws Exception {
        ZyOwner owner = this.requestUtil.getOwner(request);
        zyVisitor.setCreateBy(owner.getOwnerRealName());
        zyVisitor.setCreateById(owner.getOwnerId());
        zyVisitor.setCreateTime(LocalDateTime.now().toString());
        zyVisitor.setVisitorId(snowflakeManager.nextId()+"");
        //默认拒绝访客的邀请
        zyVisitor.setStatus("0");
        return zyVisitorService.insertVisitor(zyVisitor);
    }

    /**
     * 得到来访人名单
     *
     * @param zyVisitor 访客
     * @param pageable  可分页
     * @return {@link Result}
     */

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyVisitor", name = "zyVisitor", value = "访客", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "可分页", required = true)
    })
    @ApiOperation(value = "得到来访人名单", notes = "得到来访人名单", httpMethod = "GET")
    @GetMapping("/getVisitorList")
    public Result getVisitorList(ZyVisitor zyVisitor, Pageable pageable){
        Result visitorList = zyVisitorService.getVisitorList(zyVisitor, pageable);
        return visitorList;
    }

    /**
     * 更新状态
     *
     * @param zyVisitor 修改条件
     * @return {@link Result}
     */

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyVisitor", name = "zyVisitor", value = "修改条件", required = true)
    })
    @ApiOperation(value = "更新状态", notes = "更新状态", httpMethod = "PUT")
    @PutMapping("/updateStatus")
    @MyLog(title = "访客管理", optParam = "#{zyVisitor}", businessType = BusinessType.UPDATE)
    public Result updateStatus(@RequestBody ZyVisitor zyVisitor){
       return this.zyVisitorService.updateStatus(zyVisitor);
    }
}

