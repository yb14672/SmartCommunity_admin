package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
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
import com.zy_admin.community.dto.ZyComplaintSuggestDto;
import com.zy_admin.community.entity.ZyComplaintSuggest;
import com.zy_admin.community.service.ZyComplaintSuggestService;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 投诉建议 (ZyComplaintSuggest)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Api(value = "zyComplaintSuggest", tags = {"投诉建议 (ZyComplaintSuggest)表控制层"})
@RestController
@RequestMapping("zyComplaintSuggest")
public class ZyComplaintSuggestController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyComplaintSuggestService zyComplaintSuggestService;

    /**
     * 导出投诉建议数据
     * @param suggestIds 获取投诉建议的所有id
     * @param response 前端响应
     * @return 导出的投诉建议结果集
     * @throws IOException 抛出数据流异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "suggestIds", value = "获取投诉建议id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端响应", required = true)
    })
    @ApiOperation(value = "用于批量导出投诉建议数据", notes = "用于批量导出投诉建议数据", httpMethod = "GET")
    @MyLog(title = "投诉建议导出", optParam = "#{suggestIds}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    public Result getExcel(@RequestParam("suggestIds") ArrayList<String> suggestIds, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyComplaintSuggestDto> zyComplaintSuggestList;
        //如果前台传的集合为空或者长度为0.则全部导出。
        if (suggestIds == null || suggestIds.size() == 0) {
            zyComplaintSuggestList = zyComplaintSuggestService.querySuggestAll();
        } else {
            zyComplaintSuggestList = zyComplaintSuggestService.querySuggestByIds(suggestIds);
        }
        String fileName = URLEncoder.encode("投诉建议信息表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), ZyComplaintSuggestDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("投诉建议信息");
        excel.doWrite(zyComplaintSuggestList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 分页查询
     * @param zyComplaintSuggest  投诉建议对象
     * @param pageable 分页对象
     * @return 返回成功或错误信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "zyComplaintSuggest", name = "zyComplaintSuggest", value = "投诉建议对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true)
    })
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
    @GetMapping("/selectSuggestLimit")
    public Result selectSuggestLimit(ZyComplaintSuggest zyComplaintSuggest, Pageable pageable){
        return zyComplaintSuggestService.selectSuggestLimit(zyComplaintSuggest, pageable);
    }

    /**
     * 分页查询所有数据
     *
     * @param page               分页对象
     * @param zyComplaintSuggest 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page<ZyComplaintSuggest>", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "ZyComplaintSuggest", name = "zyComplaintSuggest", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    public R selectAll(Page<ZyComplaintSuggest> page, ZyComplaintSuggest zyComplaintSuggest) {
        return success(this.zyComplaintSuggestService.page(page, new QueryWrapper<>(zyComplaintSuggest)));
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
        return success(this.zyComplaintSuggestService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyComplaintSuggest 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyComplaintSuggest", name = "zyComplaintSuggest", value = "实体对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping
    public R insert(@RequestBody ZyComplaintSuggest zyComplaintSuggest) {
        return success(this.zyComplaintSuggestService.save(zyComplaintSuggest));
    }

    /**
     * 修改数据
     *
     * @param zyComplaintSuggest 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyComplaintSuggest", name = "zyComplaintSuggest", value = "实体对象", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping
    public R update(@RequestBody ZyComplaintSuggest zyComplaintSuggest) {
        return success(this.zyComplaintSuggestService.updateById(zyComplaintSuggest));
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
        return success(this.zyComplaintSuggestService.removeByIds(idList));
    }
}

