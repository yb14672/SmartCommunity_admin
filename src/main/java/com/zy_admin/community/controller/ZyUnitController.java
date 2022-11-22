package com.zy_admin.community.controller;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
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
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
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
    @GetMapping("/getUnitList")
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
    /**
     * 删除单元
     * @param unitIds 需要被删除的单元id
     * @return 返回删除结果集
     */
    @DeleteMapping("/deleteUnit")
    @MyLog(title = "单元信息", optParam = "#{unitIds}", businessType = BusinessType.DELETE)
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
    @GetMapping("/getExcel")
    @MyLog(title = "单元信息", optParam = "#{unitIds}", businessType = BusinessType.EXPORT)
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
    @GetMapping("/getBuildingList")
    public Result getBuildingList(ZyUnit zyUnit) {
        return zyUnitService.getBuildingList(zyUnit.getCommunityId());
    }
}

