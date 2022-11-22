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
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.sys.service.SysDictTypeService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RestController
@RequestMapping("sysDictType")
public class SysDictTypeController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictTypeService sysDictTypeService;
    /**
     * 批量导出字典类型表数据
     * @param dictIds  字典类型的主键
     * @param response 前端响应
     * @return 返回结果集
     * @throws IOException 抛出数据流异常
     */
    @MyLog(title = "字典类型", optParam = "#{dictIds}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
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
    @DeleteMapping
    @MyLog(title = "字典类型", optParam = "#{idList}", businessType = BusinessType.DELETE)
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
    @PutMapping("/updateDict")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "字典类型", optParam = "#{sysDictType}", businessType = BusinessType.UPDATE)
    public Result updateDict(@RequestBody SysDictType sysDictType) {
        return sysDictTypeService.updateDict(sysDictType);
    }
    /**
     * 新增字典
     * @param sysDictType 字典类型对象
     * @return 新增的字典类型结果集
     */
    @PostMapping("/addSysDict")
    @MyLog(title = "字典类型", optParam = "#{sysDictType}", businessType = BusinessType.INSERT)
    public Result insertDictType(@RequestBody SysDictType sysDictType) {
        sysDictType.setCreateTime(LocalDateTime.now().toString());
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
    @GetMapping("/selectDictByLimit")
    public Result selectDictByLimit(SysDictType sysDictType, Pageable pageable, String startTime, String endTime) {
        return sysDictTypeService.selectDictByLimit(sysDictType, pageable, startTime, endTime);
    }
    /**
     * 分页查询所有的字典类型数据
     * @return 所有查询的字典类型结果集
     */
    @GetMapping
    public Result selectAll() {
        return this.sysDictTypeService.selectDictAll();
    }
    /**
     * 通过主键查询单条数据
     * @param id 字典类型的主键
     * @return 单条数据结果集
     */
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable String id) {
        return this.sysDictTypeService.getDictTypeById(id);
    }
}

