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
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.service.SysOperLogService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/**
 * 操作日志记录(SysOperLog)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@RestController
@RequestMapping("sysOperLog")
public class SysOperLogController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysOperLogService sysOperLogService;
    /**
     * 批量导出操作日志数据
     * @param operLogIds 操作日志主键
     * @param response 前端响应
     * @return 导出操作日志结果集
     * @throws IOException 抛出数据流异常
     */
    @MyLog(title = "操作日志", optParam = "#{operLogIds}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    public Result getExcel(@RequestParam("operLogIds") ArrayList<Integer> operLogIds, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysOperLog> sysOperLogList;
        //判断操作日志的集合为空或者长度为0.则全部导出。
        if (operLogIds == null || operLogIds.size() == 0) {
            sysOperLogList = sysOperLogService.getOperLogList();
        } else {
            //不为空就是选中导出操作日志
            sysOperLogList = sysOperLogService.getOperLogById(operLogIds);
        }
        String fileName = URLEncoder.encode("操作日志表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), SysOperLog.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("操作日志");
        excel.doWrite(sysOperLogList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 删除操作日志
     * @param logIds 操作日志主键
     * @return 删除操作日志结果集
     */
    @DeleteMapping("/deleteLog")
    @MyLog(title = "操作日志", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result deleteLog(@RequestParam("idList") List<Integer> logIds) {
        if (logIds.size()==0){
            return this.sysOperLogService.deleteLogs();
        }
        return sysOperLogService.deleteById(logIds);
    }
    /**
     * 分页查询所有操作日志数据
     * @param sysOperLog 查询操作日志对象
     * @param pageable 分页对象
     * @param startTime 开始时间对象
     * @param endTime 结束时间对象
     * @param orderByColumn 按列排序对象
     * @param isAsc 正序排序对象
     * @return 查询的操作日志结果集
     */
    @GetMapping("/getOperLogList")
    public Result getOperLogList(SysOperLog sysOperLog, Pageable pageable, String startTime, String endTime, @RequestParam(value = "orderByColumn",defaultValue = "oper_time") String orderByColumn, @RequestParam(value = "isAsc",defaultValue = "desc") String isAsc){
        return this.sysOperLogService.getOperLogList(sysOperLog, pageable, startTime, endTime, orderByColumn, isAsc);
    }
}

