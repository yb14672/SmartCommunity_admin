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
import com.zy_admin.community.dto.CommunityExcel;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.service.ZyCommunityService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 小区 (ZyCommunityDto)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@RestController
@RequestMapping("zyCommunity")
public class ZyCommunityController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommunityService zyCommunityService;
    /**
     * 根据所得id导出小区信息
     * @param ids 查询的小区主键id
     * @param response 前端响应
     * @return 返回成功和错误信息
     * @throws IOException 抛出异常
     */
    @MyLog(title = "小区信息", optParam = "#{ids}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    public Result getExcel(@RequestParam("ids") ArrayList<Long> ids, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<CommunityExcel> communityExcelList = zyCommunityService.selectByIds(ids);
        String fileName = URLEncoder.encode("小区信息", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), CommunityExcel.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("小区信息");
        excel.doWrite(communityExcelList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 修改数据
     *  @param community 更新的小区对象
     * @param request 前端请求
     * @return 修改的小区结果集
     */
    @PutMapping("/updateCommunity")
    @MyLog(title = "小区信息", optParam = "#{community}", businessType = BusinessType.UPDATE)
    public Result update(@RequestBody ZyCommunity community,HttpServletRequest request) {
        return this.zyCommunityService.updateCommunityById(community,request);
    }
    /**
     * 新增数据
     * @param community 新增的小区对象
     * @param request 前端请求
     * @return 新增的小区结果集
     */
    @PostMapping("/insertCommunity")
    @MyLog(title = "小区信息", optParam = "#{community}", businessType = BusinessType.INSERT)
    public Result insert(@RequestBody ZyCommunity community, HttpServletRequest request) {
        return this.zyCommunityService.insertCommunity(community,request);
    }
    /**
     *  分页查询所有数据
     * @param community 查询的小区对象
     * @param pageable 查询的分页对象
     * @return 返回查询分页的结果集
     */
    @GetMapping("/selectAll")
    public Result selectAll(ZyCommunity community, Pageable pageable){
        return zyCommunityService.selectAllByLimit(community,pageable);
    }
    /**
     * 通过主键查询单条数据
     * @param id 查询的小区id
     * @return 查询数据结果集
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return new Result(this.zyCommunityService.getById(id),ResultTool.fail(ResultCode.SUCCESS));
    }
    /**
     * 删除数据
     * @param communityIds 存放小区id
     * @return 删除结果
     */
    @DeleteMapping("/deleteCommunity")
    @MyLog(title = "小区信息", optParam = "#{communityIds}", businessType = BusinessType.DELETE)
    public Result delete(@RequestBody List<String> communityIds) {
        return this.zyCommunityService.deleteByIds(communityIds);
    }
}

