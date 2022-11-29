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
import com.zy_admin.community.dto.ZyComplaintSuggestDto;
import com.zy_admin.community.entity.ZyComplaintSuggest;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyComplaintSuggestService;
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
 * 投诉建议 (ZyComplaintSuggest)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Api(value = "zyComplaintSuggest", tags = {"投诉建议 (ZyComplaintSuggest)表控制层"})
@RestController
@RequestMapping("zyComplaintSuggest")
@CrossOrigin
public class ZyComplaintSuggestController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyComplaintSuggestService zyComplaintSuggestService;

    /**
     * 请求工具类
     */
    @Resource
    private RequestUtil requestUtil;

    /**
     * 雪花算法
     */
    @Resource
    private SnowflakeManager snowflakeManager;

    /**
     * 根据id查询
     * @param suggestId  id
     * @return 返回成功或错误信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "suggestId", name = "suggestId", value = "投诉建议id", required = true),
    })
    @ApiOperation(value = "查询", notes = "查询", httpMethod = "GET")
    @GetMapping("/selectSuggestById")
    public Result selectSuggestById(String suggestId){
        return zyComplaintSuggestService.selectSuggestById(suggestId);
    }

    /**
     * 删除投诉和建议
     * @param idList 要删除的投诉建议id
     * @return 返回
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "idList", value = "要删除的投诉建议的id", required = true)
    })
    @ApiOperation(value = "删除投诉建议", notes = "删除投诉建议", httpMethod = "DELETE")
    @DeleteMapping
    public Result deleteSuggestByIds(@RequestParam("idList") ArrayList<String> idList){
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            result = this.zyComplaintSuggestService.deleteSuggestByIds(idList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 回复投诉建议
     * @param zyComplaintSuggest 要更新的投诉建议对象
     * @param request 前端请求
     * @return 更新投诉建议结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyComplaintSuggest", name = "zyComplaintSuggest", value = "要更新的投诉建议对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "回复投诉建议", notes = "回复投诉建议", httpMethod = "PUT")
    @PutMapping("/updateSuggest")
    public Result updateSuggest(@RequestBody ZyComplaintSuggest zyComplaintSuggest, HttpServletRequest request){
        SysUser user = this.requestUtil.getUser(request);
        zyComplaintSuggest.setUpdateBy(user.getUserName());
        zyComplaintSuggest.setUserId(user.getUserId()+"");
        zyComplaintSuggest.setUpdateTime(LocalDateTime.now().toString());
        return zyComplaintSuggestService.updateSuggest(zyComplaintSuggest);
    }

    /**
     * 更新投诉和建议
     * @param zyComplaintSuggest 要更新的投诉建议对象
     * @param request 前端请求
     * @return 更新投诉建议结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyComplaintSuggest", name = "zyComplaintSuggest", value = "要更新的投诉建议对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "更新投诉建议", notes = "更新投诉建议", httpMethod = "PUT")
    @PutMapping("/updateSuggestOwner")
    public Result updateSuggestOwner(@RequestBody ZyComplaintSuggest zyComplaintSuggest, HttpServletRequest request){
        ZyOwner owner = this.requestUtil.getOwner(request);
        zyComplaintSuggest.setUpdateBy(owner.getOwnerRealName());
        zyComplaintSuggest.setUserId(owner.getOwnerId());
        zyComplaintSuggest.setUpdateTime(LocalDateTime.now().toString());
        return zyComplaintSuggestService.updateSuggest(zyComplaintSuggest);
    }

    /**
     * 新增投诉建议
     * @param zyComplaintSuggest 要新增的投诉建议
     * @param request 前端请求
     * @return  查询的投诉建议结果集
     * @throws Exception 将存在的异常抛出
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyComplaintSuggest", name = "zyComplaintSuggest", value = "要新增的投诉建议信息", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "新增投诉建议", notes = "新增投诉建议", httpMethod = "POST")
    @PostMapping("/insertSuggest")
    public Result insertSuggest(@RequestBody ZyComplaintSuggest zyComplaintSuggest, HttpServletRequest request) throws Exception {
        ZyOwner owner = this.requestUtil.getOwner(request);
        zyComplaintSuggest.setCreateBy(owner.getOwnerRealName());
        zyComplaintSuggest.setCreateTime(LocalDateTime.now().toString());
        zyComplaintSuggest.setComplaintSuggestId(snowflakeManager.nextId()+"");
        zyComplaintSuggest.setUserId(owner.getOwnerId());
        return this.zyComplaintSuggestService.insertSuggest(zyComplaintSuggest);
    }

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
        Result result = zyComplaintSuggestService.selectSuggestLimit(zyComplaintSuggest, pageable);
        return zyComplaintSuggestService.selectSuggestLimit(zyComplaintSuggest, pageable);
    }
}

