package com.zy_admin.sys.controller;

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
import com.zy_admin.sys.dto.LoginInForExcelDto;
import com.zy_admin.sys.entity.SysLogininfor;
import com.zy_admin.sys.service.SysLogininforService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/**
 * 系统访问记录(SysLogininfor)表控制层
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@Api(value = "sysLogininfor", tags = {"系统访问记录(SysLogininfor)表控制层"})
@RestController
@RequestMapping("sysLogininfor")
public class SysLogininforController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysLogininforService sysLogininforService;
    /**
     * 导出登录日志信息
     * @param ids 登录日志id数组
     * @param response 前端响应
     * @return 登录日志结果集
     * @throws IOException 导出异常信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<Integer>", name = "ids", value = "登录日志id数组", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端响应", required = true)
    })
    @ApiOperation(value = "导出登录日志信息", notes = "导出登录日志信息", httpMethod = "GET")
    @MyLog(title = "登录日志", optParam = "#{ids}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    @PreAuthorize("hasAnyAuthority('monitor:logininfor:export')")
    public Result getExcel(@RequestParam("ids") ArrayList<Integer> ids, HttpServletResponse response)throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //导出数据
        List<LoginInForExcelDto> loginInForExcelDtoList = sysLogininforService.queryLogininfor(ids);
        String fileName = URLEncoder.encode("日志数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), LoginInForExcelDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("登录日志");
        excel.doWrite(loginInForExcelDtoList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 查询登录日志
     * @param sysLogininfor 登录日志对象
     * @param pageable 分页对象
     * @param startTime 开始时间对象
     * @param endTime 结束时间对象
     * @return 所查询的登录日志结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysLogininfor", name = "sysLogininfor", value = "登录日志对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "startTime", value = "开始时间对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "endTime", value = "结束时间对象", required = true)
    })
    @ApiOperation(value = "查询登录日志", notes = "查询登录日志", httpMethod = "GET")
    @GetMapping("/queryLoginInfor")
    @PreAuthorize("hasAnyAuthority('ROLE_common','ROLE_admin')")
    public Result queryLoginInfor(SysLogininfor sysLogininfor, Pageable pageable, String startTime, String endTime) {
        return sysLogininforService.queryLoginInfor(sysLogininfor,pageable,startTime,endTime);
    }
    /**
     * 删除数据
     * @param infoIds 登录日志id数组
     * @return 删除登录日志结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "int[]", name = "infoIds", value = "登录日志id数组", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping("/deleteByIds")
    @MyLog(title = "登录日志", optParam = "#{infoIds}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAnyAuthority('monitor:logininfor:remove')")
    public Result delete(@RequestBody int[] infoIds) {
        return sysLogininforService.deleteByIds(infoIds);
    }
    /**
     * 清空日志
     * @return 成功或失败的结果集
     */
    @ApiOperation(value = "清空日志", notes = "清空日志", httpMethod = "DELETE")
    @DeleteMapping("/EmptyLogininfor")
    @MyLog(title = "登录日志", businessType = BusinessType.CLEAR)
    public Result EmptyLogininfor(){
        return sysLogininforService.EmptyLogininfor();
    }
}

