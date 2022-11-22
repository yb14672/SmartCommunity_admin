package com.zy_admin.sys.controller;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.dto.DataDictExcelDto;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDictDataService;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/**
 * 字典数据表(SysDictData)表控制层
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@RestController
@RequestMapping("/sysDictData")
public class SysDictDataController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictDataService sysDictDataService;
    @Resource
    private RequestUtil requestUtil;
    /**
     * 分页查询所有字典数据
     * @param page        分页对象
     * @param sysDictData 查询字典数据对象
     * @return 分页查询的结果集
     */
    @GetMapping
    public Result selectAll(SysDictData sysDictData, Page page) {
        return this.sysDictDataService.selectDictDataLimit(sysDictData, page);
    }
    /**
     * 通过字典数据id查询单条数据
     * @param id 字典数据主键
     * @return 单条数据结果集
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable String id) {
        return this.sysDictDataService.getDictDataById(id);
    }
    /**
     * 新增字典数据
     * @param sysDictData 字典数据对象
     * @param request 前端请求
     * @return 新增的字典数据结果集
     */
    @PostMapping
    @MyLog(title = "字典数据", optParam = "#{sysDictData}", businessType = BusinessType.INSERT)
    public Result insert(@RequestBody SysDictData sysDictData, HttpServletRequest request) {
        //获取当前登录的用户，用于添加创建人
        SysUser user = requestUtil.getUser(request);
        sysDictData.setIsDefault("N");
        return this.sysDictDataService.insert(sysDictData, user);
    }
    /**
     * 修改字典数据
     * @param sysDictData 字典数据对象
     * @param request 前端请求
     * @return 修改的字典数据结果集
     */
    @PutMapping
    @MyLog(title = "字典数据", optParam = "#{sysDictData}", businessType = BusinessType.UPDATE)
    public Result update(@RequestBody SysDictData sysDictData, HttpServletRequest request) {
        //获取当前登录的用户，用于添加创建人
        SysUser user = requestUtil.getUser(request);
        return this.sysDictDataService.updateDictData(sysDictData, user);
    }
    /**
     * 删除字典数据
     * @param idList 字典数据主键集合
     * @return 删除的字典结果集
     */
    @DeleteMapping
    @MyLog(title = "字典数据", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result delete(@RequestParam("idList") List<String> idList) {
        return this.sysDictDataService.removeDictDataByIds(idList);
    }
    /**
     * 根据字典类型获取所有字典数据
     * @param dictType 字典类型
     * @return 根据字典类型获取所有字典数据结果集
     */
    @GetMapping("/getDict")
    public Result getDict(String dictType) {
        return this.sysDictDataService.getDict(dictType);
    }
    /**
     * 导出字典数据
     * @param ids 字典数据主键
     * @param response 前端响应
     * @return 所查询的字典数据结果集
     */
    @GetMapping("/export")
    @MyLog(title = "字典数据", optParam = "#{ids}", businessType = BusinessType.EXPORT)
    public Result export(@RequestParam("ids") ArrayList<Integer> ids, @RequestParam("dictType") String dictType, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //用于存储要导出的数据列表
        List<DataDictExcelDto> sysDictDataList;
        if (ids == null || ids.size() == 0) {
            sysDictDataList = sysDictDataService.getDictList(dictType);
        } else {
            sysDictDataList = sysDictDataService.getDictListById(ids);
        }
        String fileName = URLEncoder.encode("字典数据表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), DataDictExcelDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("字典数据");
        excel.doWrite(sysDictDataList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
}

