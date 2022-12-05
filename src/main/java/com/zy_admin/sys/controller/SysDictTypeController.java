package com.zy_admin.sys.controller;

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
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDictTypeService;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
 * 字典类型表(SysDictType)表控制层
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@Api(value = "sysDictType", tags = {"字典类型表(SysDictType)表控制层"})
@RestController
@RequestMapping("sysDictType")
public class SysDictTypeController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictTypeService sysDictTypeService;
    /**
     * 请求工具类
     */
    @Resource
    private RequestUtil requestUtil;
    /**
     * 批量导出字典类型表数据
     * @param dictIds  字典类型的主键
     * @param response 前端响应
     * @return 返回结果集
     * @throws IOException 抛出数据流异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<Integer>", name = "dictIds", value = "字典类型的主键", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端响应", required = true)
    })
    @ApiOperation(value = "批量导出字典类型表数据", notes = "批量导出字典类型表数据", httpMethod = "GET")
    @MyLog(title = "字典类型", optParam = "#{dictIds}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    @PreAuthorize("hasAnyAuthority('system:config:export')")
    public Result getExcel(@RequestParam("dictIds") ArrayList<Integer> dictIds, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysDictType> sysDictTypeList;
        if (dictIds == null || dictIds.size() == 0) {
            sysDictTypeList = sysDictTypeService.getDictLists();
        } else {
            sysDictTypeList = sysDictTypeService.queryDictById(dictIds);
        }
        String fileName = URLEncoder.encode("字典类型表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), SysDictType.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("字典类型");
        excel.doWrite(sysDictTypeList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 删除字典类型
     * @param idList 字典类型的主键数组
     * @return 删除的字典类型结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String[]", name = "idList", value = "字典类型的主键数组", required = true)
    })
    @ApiOperation(value = "删除字典类型", notes = "删除字典类型", httpMethod = "DELETE")
    @DeleteMapping
    @MyLog(title = "字典类型", optParam = "#{idList}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAnyAuthority('system:config:remove')")
    public Result delete(@RequestParam String[] idList) {
        List<Integer> idList1 = new ArrayList<>();
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            for (String str : idList) {
                // 把删除选上的id添加到idlist1的集合里
                idList1.add(Integer.valueOf(str));
            }
            // 修改字典表
            result = this.sysDictTypeService.deleteByIdList(idList1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 修改字典类型
     * @param sysDictType 字典类型对象
     * @return 修改的字典类型结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDictType", name = "sysDictType", value = "字典类型对象", required = true)
    })
    @ApiOperation(value = "修改字典类型", notes = "修改字典类型", httpMethod = "PUT")
    @PutMapping("/updateDict")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "字典类型", optParam = "#{sysDictType}", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyAuthority('system:config:edit')")
    public Result updateDict(@RequestBody SysDictType sysDictType) {
        return sysDictTypeService.updateDict(sysDictType);
    }
    /**
     * 新增字典
     * @param sysDictType 字典类型对象
     * @return 新增的字典类型结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDictType", name = "sysDictType", value = "字典类型对象", required = true)
    })
    @ApiOperation(value = "新增字典", notes = "新增字典", httpMethod = "POST")
    @PostMapping("/addSysDict")
    @MyLog(title = "字典类型", optParam = "#{sysDictType}", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAnyAuthority('system:config:add')")
    public Result insertDictType(@RequestBody SysDictType sysDictType, HttpServletRequest request) {
        SysUser user = requestUtil.getUser(request);
        sysDictType.setCreateTime(LocalDateTime.now().toString());
        sysDictType.setCreateBy(user.getUserName());
        return this.sysDictTypeService.insertOrUpdateBatch(sysDictType);
    }
    /**
     * 分页查询字典类型
     * @param sysDictType 字典类型数据对象
     * @param pageable 分页对象
     * @param startTime 开始时间对象
     * @param endTime 结束时间对象
     * @return 查询的字典类型数据对象
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysDictType", name = "sysDictType", value = "字典类型数据对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "startTime", value = "开始时间对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "endTime", value = "结束时间对象", required = true)
    })
    @ApiOperation(value = "分页查询字典类型", notes = "分页查询字典类型", httpMethod = "GET")
    @GetMapping("/selectDictByLimit")
    @PreAuthorize("hasAnyAuthority('system:dict:query')")
    public Result selectDictByLimit(SysDictType sysDictType, Pageable pageable, String startTime, String endTime) {
        return sysDictTypeService.selectDictByLimit(sysDictType, pageable, startTime, endTime);
    }
    /**
     * 分页查询所有的字典类型数据
     * @return 所有查询的字典类型结果集
     */
    @ApiOperation(value = "分页查询所有的字典类型数据", notes = "分页查询所有的字典类型数据", httpMethod = "GET")
    @GetMapping
    public Result selectAll() {
        return this.sysDictTypeService.selectDictAll();
    }
    /**
     * 通过主键查询单条数据
     * @param id 字典类型的主键
     * @return 单条数据结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "字典类型的主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('system:dict:query')")
    public Result selectOne(@PathVariable String id) {
        return this.sysDictTypeService.getDictTypeById(id);
    }
}

