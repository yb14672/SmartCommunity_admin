package com.zy_admin.sys.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.log.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.service.SysOperLogService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ExcelUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
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
     *
     * @param operLogIds
     * @param response
     */
    @GetMapping("/getExcel")
    @MyLog(title = "操作日志", optParam = "#{operLogIds}", businessType = BusinessType.EXPORT)
    public void getExcel(@RequestParam("operLogIds") ArrayList<Integer> operLogIds, HttpServletResponse response) throws IOException {
        List<SysOperLog> sysOperLogList = new ArrayList<>();
        //判断操作日志的集合为空或者长度为0.则全部导出。
        if (operLogIds == null || operLogIds.size() == 0) {
            sysOperLogList = sysOperLogService.getOperLogList();
        } else {
            //不为空就是选中导出操作日志
            System.out.println(operLogIds);
            sysOperLogList = sysOperLogService.getOperLogById(operLogIds);
        }
        String fileName = URLEncoder.encode("操作日志表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), SysOperLog.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(sysOperLogList);
    }

    /**
     * 新增操作日志
     * @param sysOperLog
     * @return
     */
    public void addOperlog(SysOperLog sysOperLog) {
         sysOperLogService.addOperlog(sysOperLog);
    }

  /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param sysOperLog 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysOperLog> page, SysOperLog sysOperLog) {
        return success(this.sysOperLogService.page(page, new QueryWrapper<>(sysOperLog)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysOperLogService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysOperLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysOperLog sysOperLog) {
        return success(this.sysOperLogService.save(sysOperLog));
    }

    /**
     * 修改数据
     *
     * @param sysOperLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysOperLog sysOperLog) {
        return success(this.sysOperLogService.updateById(sysOperLog));
    }

    /**
     * 删除数据
     *
     * @param LogIds 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/deleteLog")
    @MyLog(title = "操作日志", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result deleteLog(@RequestParam("idList") List<Integer> LogIds) {
        System.err.println(LogIds);
        if (LogIds.size()==0){
            return this.sysOperLogService.deleteLogs();
        }
        return sysOperLogService.deleteById(LogIds);
    }
    /**
     * 分页查询所有数据
     *
     * @param pageable       分页对象
     * @param sysOperLog 查询实体
     * @return 所有数据
     */

    @GetMapping("/getOperLogList")
    public Result getOperLogList(SysOperLog sysOperLog, Pageable pageable, String startTime, String endTime, @RequestParam(value = "orderByColumn",defaultValue = "oper_time") String orderByColumn, @RequestParam(value = "isAsc",defaultValue = "desc") String isAsc){
        Result operLogList = this.sysOperLogService.getOperLogList(sysOperLog, pageable, startTime, endTime, orderByColumn, isAsc);
        return operLogList;
    }
}

