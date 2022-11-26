package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.RepairDto;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyRepair;
import com.zy_admin.community.service.ZyRepairService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报修信息(ZyRepair)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Api(value = "zyRepair", tags = {"报修信息(ZyRepair)表控制层"})
@RestController
@RequestMapping("zyRepair")
public class ZyRepairController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyRepairService zyRepairService;
    @Resource
    private RequestUtil requestUtil;
    /**
     * 报修导出
     * @param repairIds 报修id
     * @param response 前端响应
     * @return 导出保修结果集
     * @throws IOException 输出流异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "repairIds", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "", required = true)
    })
    @ApiOperation(value = "报修导出", notes = "报修导出", httpMethod = "GET")
    @MyLog(title = "报修导出", optParam = "#{repairIds}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    public Result getExcel(@RequestParam("repairIds") ArrayList<String> repairIds, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<RepairDto> repairList;
        //如果前台传的集合为空或者长度为0.则全部导出。
        if (repairIds == null || repairIds.size() == 0) {
            repairList = zyRepairService.getAllRepairList(repairIds);
        } else {
            repairList = zyRepairService.getRepairById(repairIds);
        }
        String fileName = URLEncoder.encode("报修信息表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(),RepairDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("楼层信息");
        excel.doWrite(repairList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 删除报修
     * @param repairIds 报修id集合
     * @return 删除结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<Long>", name = "idList", value = "主键结合", required = true)
    })
    @ApiOperation(value = "删除报修", notes = "删除报修", httpMethod = "DELETE")
    @DeleteMapping("/deleteRepair")
    public Result deleteRepair(@RequestBody List<String> repairIds) {
        return zyRepairService.deleteRepair(repairIds);
    }
    /**
     * 修改报修
     * @param zyRepair 报修对象
     * @return 修改结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyRepair", name = "zyRepair", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改报修", notes = "修改报修", httpMethod = "PUT")
    @PutMapping("/updateRepair")
    public Result updateRepair(@RequestBody ZyRepair zyRepair, HttpServletRequest request) {
        SysUser user = requestUtil.getUser(request);
        zyRepair.setUpdateBy(user.getUserName());
        zyRepair.setUpdateTime(LocalDateTime.now().toString());
        //派单时间repair_state
        if ("Allocated".equals(zyRepair.getRepairState())){
            zyRepair.setAssignmentId(user.getUserId()+"");
            String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            zyRepair.setAssignmentTime(format);
        }
        //已处理时间complete_time
        if ("Processed".equals(zyRepair.getRepairState())){
            zyRepair.setCompletePhone(user.getPhonenumber()+"");
            zyRepair.setCompleteName(user.getUserName()+"");
            String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            zyRepair.setCompleteTime(format);
        }
        //已取消cancel_time
        if ("Cancelled".equals(zyRepair.getRepairState())){
            String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            zyRepair.setCancelTime(format);
        }
        zyRepair.setUpdateBy(user.getUserId()+"");
        return this.zyRepairService.updateRepair(zyRepair);
    }
    /**
     * 新增报修
     * @param zyRepair 查询报修对象
     * @return 新增结果级
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyRepair", name = "zyRepair", value = "查询报修对象", required = true)
    })
    @ApiOperation(value = "新增报修", notes = "新增报修", httpMethod = "POST")
    @PostMapping("/insertRepair")
    public Result insertRepair(@RequestBody ZyRepair zyRepair, HttpServletRequest request) {
        ZyOwner owner = requestUtil.getOwner(request);
        zyRepair.setCreateBy(owner.getOwnerRealName());
        zyRepair.setCreateTime(LocalDateTime.now().toString());
        zyRepair.setReceivingOrdersTime(LocalDateTime.now().toString());
        return this.zyRepairService.insertRepair(zyRepair,request);
    }
    /**
     * 分页查询所有报修数据
     * @param pageable  分页对象
     * @param repairDto 查询报修对象
     * @return 所有报修信息数据结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "RepairDto", name = "repairDto", value = "查询报修对象", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping("/getAllRepairs")
    public Result getAllRepairs(Pageable pageable, RepairDto repairDto) {
        return zyRepairService.getAllRepairs(pageable, repairDto);
    }
    /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Serializable", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyRepairService.getById(id));
    }


}

