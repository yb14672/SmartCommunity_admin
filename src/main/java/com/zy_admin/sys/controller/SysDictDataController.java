package com.zy_admin.sys.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.log.BusinessType;
import com.zy_admin.common.log.MyLog;
import com.zy_admin.sys.dto.DataDictExcelDto;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDictDataService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.Result;
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
 *
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
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param sysDictData 查询实体
     * @return 所有数据
     */
    @GetMapping()
    public Result selectAll(SysDictData sysDictData, Page page) {
        return this.sysDictDataService.selectDictDataLimit(sysDictData, page);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable String id) {
        return this.sysDictDataService.getDictDataById(id);
    }

    /**
     * 新增数据
     *
     * @param sysDictData 实体对象
     * @return 新增结果
     */
    @PostMapping
    @MyLog(title = "新增字典数据", optParam = "#{sysDictData}", businessType = BusinessType.OTHER)
    public Result insert(@RequestBody SysDictData sysDictData, HttpServletRequest request) {
        //获取当前登录的用户，用于添加创建人
        SysUser user = requestUtil.getUser(request);
        sysDictData.setIsDefault("N");
        return this.sysDictDataService.insert(sysDictData, user);
    }

    /**
     * 修改数据
     *
     * @param sysDictData 实体对象
     * @return 修改结果
     */
    @PutMapping
    @MyLog(title = "修改字典数据", optParam = "#{sysDictData}", businessType = BusinessType.OTHER)
    public Result update(@RequestBody SysDictData sysDictData, HttpServletRequest request) {
        //获取当前登录的用户，用于添加创建人
        SysUser user = requestUtil.getUser(request);
        return this.sysDictDataService.updateDictData(sysDictData, user);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @MyLog(title = "删除选中字典", optParam = "#{idList}", businessType = BusinessType.OTHER)
    public Result delete(@RequestParam("idList") List<String> idList) {
        return this.sysDictDataService.removeDictDataByIds(idList);
    }

    /**
     * 根据字典类型获取所有字典数据
     *
     * @param dictType
     * @return
     */
    @GetMapping("/getDict")
    @MyLog(title = "根据字典类型获取所有字典数据", optParam = "#{dictType}", businessType = BusinessType.OTHER)
    public Result getDict(String dictType) {
        System.out.println(dictType);
        return this.sysDictDataService.getDict(dictType);
    }

    /**
     * 导出字典数据
     *
     * @param ids
     * @param response
     * @return
     */
    @GetMapping("/export")
    public void export(@RequestParam("ids") ArrayList<Integer> ids,@RequestParam("dictType") String dictType, HttpServletResponse response) throws IOException {
        //用于存储要导出的数据列表
        List<DataDictExcelDto> sysDictDataList = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        //判断idlist是否为空，若为空则没选要导出的，即导出全部
        if (ids == null || ids.size() == 0) {
            sysDictDataList = sysDictDataService.getDictList(dictType);
        } else {
            //执行查询角色列表的sql语句
            sysDictDataList = sysDictDataService.getDictListById(ids);
        }
        String fileName = URLEncoder.encode("字典数据表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), DataDictExcelDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(sysDictDataList);
    }
}

