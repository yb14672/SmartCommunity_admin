package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.io.Serializable;
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
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "响应", required = true)
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
            @ApiImplicitParam(paramType = "query", dataType = "ZyVisitor", name = "zyVisitor", value = "zy访客", required = true)
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
       return   this.zyVisitorService.updateStatus(zyVisitor);
    }
    /**
     * 分页查询所有数据
     *
     * @param page      分页对象
     * @param zyVisitor 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page<ZyVisitor>", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "ZyVisitor", name = "zyVisitor", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    public R selectAll(Page<ZyVisitor> page, ZyVisitor zyVisitor) {
        return success(this.zyVisitorService.page(page, new QueryWrapper<>(zyVisitor)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Serializable", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyVisitorService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyVisitor 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyVisitor", name = "zyVisitor", value = "实体对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping
    public R insert(@RequestBody ZyVisitor zyVisitor) {
        return success(this.zyVisitorService.save(zyVisitor));
    }

    /**
     * 修改数据
     *
     * @param zyVisitor 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyVisitor", name = "zyVisitor", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping
    public R update(@RequestBody ZyVisitor zyVisitor) {
        return success(this.zyVisitorService.updateById(zyVisitor));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "主键结合", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyVisitorService.removeByIds(idList));
    }
}

