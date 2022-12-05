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
import com.zy_admin.community.dto.GetUnitExcelDto;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyUnitService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
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
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * 单元 (ZyUnit)表控制层
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Api(value = "zyUnit", tags = {"单元 (ZyUnit)表控制层"})
@RestController
@RequestMapping("zyUnit")
public class ZyUnitController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyUnitService zyUnitService;
    @Resource
    private RequestUtil requestUtil;
    @Resource
    private SnowflakeManager snowflakeManager;
    /**
     * 分页查询单元楼信息
     * @param zyUnit   单元对象
     * @param pageable 分页对象
     * @return 返回单元的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyUnit", name = "zyUnit", value = "单元对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true)
    })
    @ApiOperation(value = "分页查询单元楼信息", notes = "分页查询单元楼信息", httpMethod = "GET")
    @GetMapping("/getUnitList")
    @PreAuthorize("hasAnyAuthority('ROLE_common','ROLE_admin')")
    public Result getUnitList(ZyUnit zyUnit, Pageable pageable) {
        return zyUnitService.getUnitList(zyUnit, pageable);
    }
    /**
     * 新增单元楼
     * @param request 前端请求
     * @param zyUnit 新增的单元信息
     * @return 新增结果集
     * @throws Exception 抛出异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "ZyUnit", name = "zyUnit", value = "新增的单元信息", required = true)
    })
    @ApiOperation(value = "新增单元楼", notes = "新增单元楼", httpMethod = "POST")
    @PostMapping("/insertUnit")
    @MyLog(title = "单元信息", optParam = "#{zyUnit}", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAnyAuthority('system:unit:add')")
    public Result insertUnit(HttpServletRequest request, @RequestBody ZyUnit zyUnit) throws Exception {
        zyUnit.setUnitId(snowflakeManager.nextId() + "");
        SysUser user = this.requestUtil.getUser(request);
        zyUnit.setCreateBy(user.getUserName());
        zyUnit.setCreateTime(LocalDateTime.now().toString());
        return zyUnitService.insertUnit(zyUnit);
    }
    /**
     * 修改单元楼
     * @param request 前端请求
     * @param zyUnit 要修改的单元信息
     * @return 成功或错误信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "ZyUnit", name = "zyUnit", value = "要修改的单元信息", required = true)
    })
    @ApiOperation(value = "修改单元楼", notes = "修改单元楼", httpMethod = "PUT")
    @PutMapping("/updateUnit")
    @MyLog(title = "单元信息", optParam = "#{zyUnit}", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyAuthority('system:unit:edit')")
    public Result updateUnit(HttpServletRequest request, @RequestBody ZyUnit zyUnit) {
        SysUser user = this.requestUtil.getUser(request);
        zyUnit.setUpdateBy(user.getUserName());
        zyUnit.setUpdateTime(LocalDateTime.now().toString());
        return zyUnitService.updateUnit(zyUnit);
    }
    /**
     * 删除单元
     * @param unitIds 需要被删除的单元id
     * @return 返回删除结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "unitIds", value = "需要被删除的单元id", required = true)
    })
    @ApiOperation(value = "删除单元", notes = "删除单元", httpMethod = "DELETE")
    @DeleteMapping("/deleteUnit")
    @MyLog(title = "单元信息", optParam = "#{unitIds}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAnyAuthority('system:unit:remove')")
    public Result deleteUnit(@RequestBody List<String> unitIds) {
        return zyUnitService.deleteUnit(unitIds);
    }
    /**
     * 单元导出
     * @param unitIds 存放需要导出的单元id
     * @param communityId 存放需要导出的小区id
     * @param response 前端请求
     * @return 返回导出结果集
     * @throws IOException 抛出数据流异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "unitIds", value = "存放需要导出的单元id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "communityId", value = "存放需要导出的小区id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端请求", required = true)
    })
    @ApiOperation(value = "单元导出", notes = "单元导出", httpMethod = "GET")
    @GetMapping("/getExcel")
    @MyLog(title = "单元信息", optParam = "#{unitIds}", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAnyAuthority('system:unit:export')")
    public Result getExcel(@RequestParam("unitIds") ArrayList<String> unitIds,@RequestParam("communityId") String communityId, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyUnit> zyUnits;
        if (unitIds == null || unitIds.size() == 0) {
            //获取指定小区的单元信息
            zyUnits = zyUnitService.getAll(communityId);
        } else {
            //根据单元id获取单元信息
            zyUnits = zyUnitService.getUnitById(unitIds);
        }
        String fileName = URLEncoder.encode("单元信息表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), GetUnitExcelDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("单元信息");
        excel.doWrite(zyUnits);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 通过社区id获取楼栋
     * @param zyUnit 存放小区信息
     * @return 返回楼栋的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyUnit", name = "zyUnit", value = "存放小区信息", required = true)
    })
    @ApiOperation(value = "通过社区id获取楼栋", notes = "通过社区id获取楼栋", httpMethod = "GET")
    @GetMapping("/getBuildingList")
    @PreAuthorize("hasAnyAuthority('system:unit:query')")
    public Result getBuildingList(ZyUnit zyUnit) {
        return zyUnitService.getBuildingList(zyUnit.getCommunityId());
    }
}

