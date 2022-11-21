package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.GetUnitExcelDto;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyUnitService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.*;
import org.springframework.beans.factory.annotation.Autowired;
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
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@RestController
@RequestMapping("zyUnit")
public class ZyUnitController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyUnitService zyUnitService;
    /**
     *
     */
    @Resource
    private RequestUtil requestUtil;

    @Autowired
    private SnowflakeManager snowflakeManager;

    /**
     * 单元楼查询分页
     */
    @GetMapping("/getUnitList")
    public Result getUnitList(ZyUnit zyUnit, Pageable pageable) {
        Result unitList = zyUnitService.getUnitList(zyUnit, pageable);
        return unitList;
    }

    /**
     * 新增单元楼
     *
     * @param request
     * @param zyUnit
     * @return
     */
    @PostMapping("/insertUnit")
    @MyLog(title = "单元信息", optParam = "#{zyUnit}", businessType = BusinessType.INSERT)
    public Result insertUnit(HttpServletRequest request, @RequestBody ZyUnit zyUnit) throws Exception {
        zyUnit.setUnitId(snowflakeManager.nextId() + "");
        SysUser user = this.requestUtil.getUser(request);
        zyUnit.setCreateBy(user.getUserName());
        zyUnit.setCreateTime(LocalDateTime.now().toString());
        return zyUnitService.insertUnit(zyUnit);
    }

    /**
     * 修改单元楼
     *
     * @param request 前端请求
     * @param zyUnit 要修改的单元信息
     * @return 成功或错误信息
     */
    @PutMapping("/updateUnit")
    @MyLog(title = "单元信息", optParam = "#{zyUnit}", businessType = BusinessType.UPDATE)
    public Result updateUnit(HttpServletRequest request, @RequestBody ZyUnit zyUnit) {
        SysUser user = this.requestUtil.getUser(request);
        zyUnit.setUpdateBy(user.getUserName());
        zyUnit.setUpdateTime(LocalDateTime.now().toString());
        return zyUnitService.updateUnit(zyUnit);
    }

    @DeleteMapping("/deleteUnit")
    @MyLog(title = "单元信息", optParam = "#{unitIds}", businessType = BusinessType.DELETE)
    public Result deleteUnit(@RequestBody List<String> unitIds) {
        return zyUnitService.deleteUnit(unitIds);
    }

    @GetMapping("/getExcel")
    @MyLog(title = "单元信息", optParam = "#{unitIds}", businessType = BusinessType.EXPORT)
    public Result getExcel(@RequestParam("unitIds") ArrayList<String> unitIds,@RequestParam("communityId") String communityId, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyUnit> zyUnits = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        //执行查询角色列表的sql语句,但不包括del_flag为2的
        if (unitIds == null || unitIds.size() == 0) {
            zyUnits = zyUnitService.getAll(communityId);
        } else {
            //执行查询角色列表的sql语句
            zyUnits = zyUnitService.getUnitById(unitIds);
        }
        String fileName = URLEncoder.encode("角色表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
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

    @GetMapping("/getBuildingList")
    public Result getBuildingList(ZyUnit zyUnit) {
        return zyUnitService.getBuildingList(zyUnit.getCommunityId());
    }
}

