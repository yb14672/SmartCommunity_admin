package com.zy_admin.community.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.ZyParkDto;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyParkService;
import com.zy_admin.sys.entity.SysUser;
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
import java.util.ArrayList;
import java.util.List;

/**
 * (ZyPark)表控制层
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
@Api(value = "zyPark", tags = {"(ZyPark)表控制层"})
@RestController
@RequestMapping("zyPark")
public class ZyParkController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyParkService zyParkService;
    @Resource
    private RequestUtil requestUtil;
    @Resource
    private SnowflakeManager snowflakeManager;

    /**
     * 导出
     *
     * @param ids         id列表--没有导出当前全部车位
     * @param communityId 小区id
     * @param response    相应对象
     * @return Excel表格
     * @throws IOException ioException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "ids", value = "id列表--没有导出当前全部车位", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "communityId", value = "小区id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "相应对象", required = true)
    })
    @ApiOperation(value = "导出", notes = "导出", httpMethod = "GET")
    @GetMapping("/export")
    @MyLog(title = "车位信息", optParam = "#{ids},#{communityId}", businessType = BusinessType.EXPORT)
    public Result getExcel(@RequestParam("ids") ArrayList<String> ids, String communityId, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        Result result1 = zyParkService.getListByIdList(ids, communityId);
        List<ZyParkDto> list = (List<ZyParkDto>) result1.getData();
        String fileName = URLEncoder.encode("车位信息", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), ZyParkDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("车位信息");
        excel.doWrite(list);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 批量插入
     *
     * @param zyPark  停车位
     * @param number  数量
     * @param request 请求
     * @return {@link Result}
     * @throws Exception 异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyPark", name = "zyPark", value = "停车位", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "number", value = "数量", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "批量插入", notes = "批量插入", httpMethod = "POST")
    @PostMapping("/batchInsert")
    public Result batchInsert(@RequestBody ZyPark zyPark, Integer number, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyPark.setCreateBy(user.getUserName());
        zyPark.setCreateTime(LocalDateTime.now().toString());
        System.out.println(zyPark.toString());
        return zyParkService.batchInsert(zyPark, number);
    }

    /**
     * 新增停车位
     *
     * @param zyPark  停车位
     * @param request 请求
     * @return {@link Result}
     * @throws Exception 异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyPark", name = "zyPark", value = "停车位", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "新增停车位", notes = "新增停车位", httpMethod = "POST")
    @PostMapping("/insertPark")
    public Result insertPark(@RequestBody ZyPark zyPark, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyPark.setCreateBy(user.getUserName());
        zyPark.setCreateTime(LocalDateTime.now().toString());
        zyPark.setParkId(snowflakeManager.nextId() + "");
        long now = System.currentTimeMillis();
        zyPark.setParkCode("PK_" + Long.toString(now).substring(0, 13));
        return zyParkService.insertPark(zyPark);
    }

    /**
     * 修改车位
     *
     * @param request 请求
     * @param zyPark  停车位
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyPark", name = "zyPark", value = "停车位", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "修改车位", notes = "修改车位", httpMethod = "PUT")
    @PutMapping("/updatePark")
    public Result updatePark(@RequestBody ZyPark zyPark,HttpServletRequest request) {
        SysUser user = requestUtil.getUser(request);
        zyPark.setUpdateBy(user.getUserName());
        zyPark.setUpdateTime(LocalDateTime.now().toString());
        return this.zyParkService.updatePark(zyPark);
    }

    /**
     * 删除停车位
     *
     * @param parkIds 停车位
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<String>", name = "parkIds", value = "停车位", required = true)
    })
    @ApiOperation(value = "删除停车位", notes = "删除停车位", httpMethod = "DELETE")
    @DeleteMapping
    public Result deletePark(@RequestParam("parkIds") List<String> parkIds) {
        return this.zyParkService.deletePark(parkIds);
    }

    /**
     * 查询车位状态是启用0的
     * @return {@link Result}
     */
    @ApiOperation(value = "查询车位状态是启用的", notes = "查询车位状态是启用的", httpMethod = "GET")
    @GetMapping("/selectParkStatusOpen")
    public Result selectParkStatusOpen(){
        return this.zyParkService.selectParkStatusOpen();
    }

    /**
     * 分页查询
     *
     * @param zyPark 筛选条件
     * @param page   分页对象
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyParkDto", name = "zyPark", value = "筛选条件", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Page", name = "page", value = "分页对象", required = true)
    })
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
    @GetMapping
    public Result queryByPage(ZyParkDto zyPark, Page page) {
        return this.zyParkService.queryByPage(zyPark, page);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("{id}")
    public Result queryById(@PathVariable("id") String id) {
        return this.zyParkService.queryById(id);
    }
}

