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
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.sys.service.SysDictTypeService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型表(SysDictType)表控制层
 *
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
     * 用于批量导出字典列表数据
     *
     * @param dictIds
     * @param response
     */
    @GetMapping("/getExcel")
    public void getExcel( ArrayList<Integer> dictIds, HttpServletResponse response) throws IOException {
        List<SysDictType> sysDictTypeList = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        //执行   查询角色列表的sql语句   但不包括del_flag为2的
        if (dictIds == null || dictIds.size() == 0) {
            sysDictTypeList = sysDictTypeService.getDictLists();
        } else {
            //执行查询角色列表的sql语句
            sysDictTypeList = sysDictTypeService.queryDictById(dictIds);
        }
        String fileName = URLEncoder.encode("字典表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), SysDictType.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(sysDictTypeList);

    }

//    删除
    @DeleteMapping()
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@RequestParam String[] idList){
        List<Integer> idList1 = new ArrayList<Integer>();
        Result result = new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            for (String str : idList) {
//                把删除选上的id添加到idlist1的集合里
                idList1.add(Integer.valueOf(str));
            }
            //修改字典表
            result = this.sysDictTypeService.deleteByIdList(idList1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改
     * @return
     */
    @PutMapping("/updateDict")
    public Result updateDict(@RequestBody SysDictType sysDictType){
        return sysDictTypeService.updateDict(sysDictType);
    }
    /**
     * 新增字典
     * @param sysDictType
     * @return
     */
    @PostMapping("/addSysDict")
    public Result insertDictType(@RequestBody SysDictType sysDictType){
        System.out.println(sysDictType);
        sysDictType.setCreateTime(LocalDateTime.now().toString());
        return this.sysDictTypeService.insertOrUpdateBatch(sysDictType);
    }


    /**
     * 分页查询
     * @param sysDictType
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/selectDictByLimit")
    public Result selectDictByLimit(SysDictType sysDictType, Pageable pageable, String startTime, String endTime){
        System.out.println(sysDictType);
        Result result = sysDictTypeService.selectDictByLimit(sysDictType, pageable,startTime,endTime);
        System.out.println(result);
        return result;
    }

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param sysDictType 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysDictType> page, SysDictType sysDictType) {
        return success(this.sysDictTypeService.page(page, new QueryWrapper<>(sysDictType)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysDictTypeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysDictType 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysDictType sysDictType) {
        return success(this.sysDictTypeService.save(sysDictType));
    }

    /**
     * 修改数据
     *
     * @param sysDictType 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysDictType sysDictType) {
        return success(this.sysDictTypeService.updateById(sysDictType));
    }

}

