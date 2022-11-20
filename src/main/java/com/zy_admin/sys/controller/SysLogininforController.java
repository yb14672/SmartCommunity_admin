package com.zy_admin.sys.controller;


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
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.dto.LoginInForExcelDto;
import com.zy_admin.sys.entity.SysLogininfor;
import com.zy_admin.sys.service.SysLogininforService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统访问记录(SysLogininfor)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@RestController
@RequestMapping("sysLogininfor")
public class SysLogininforController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysLogininforService sysLogininforService;

    @MyLog(title = "登录日志", optParam = "#{ids}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
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

    @GetMapping("/queryLoginInfor")
    public Result queryLoginInfor(SysLogininfor sysLogininfor, Pageable pageable, String startTime, String endTime) {
        return sysLogininforService.queryLoginInfor(sysLogininfor,pageable,startTime,endTime);
    }

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param sysLogininfor 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysLogininfor> page, SysLogininfor sysLogininfor) {
        return success(this.sysLogininforService.page(page, new QueryWrapper<>(sysLogininfor)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysLogininforService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysLogininfor 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysLogininfor sysLogininfor) {
        return success(this.sysLogininforService.save(sysLogininfor));
    }

    /**
     * 修改数据
     *
     * @param sysLogininfor 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysLogininfor sysLogininfor) {
        return success(this.sysLogininforService.updateById(sysLogininfor));
    }

    /**
     * 删除数据
     *
     * @param infoIds 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/deleteByIds")
    @MyLog(title = "登录日志", optParam = "#{infoIds}", businessType = BusinessType.DELETE)
    public Result delete(@RequestBody int[] infoIds) {
        return sysLogininforService.deleteByIds(infoIds);
    }

    @DeleteMapping("/EmptyLogininfor")
    @MyLog(title = "登录日志", businessType = BusinessType.CLEAR)
    public Result EmptyLogininfor(){
        return sysLogininforService.EmptyLogininfor();
    }
}

