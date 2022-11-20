package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.GetUnitExcelDto;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyUnitService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 单元 (ZyUnit)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@RestController
@RequestMapping("zyUnit")
public class ZyUnitController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyUnitService zyUnitService;
    /**
     *
     */
    @Resource
    private RequestUtil requestUtil;

    @Autowired
    private SnowflakeManager snowflakeManager;
    /**
     * 分页查询所有数据
     *
     * @param page   分页对象
     * @param zyUnit 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyUnit> page, ZyUnit zyUnit) {
        return success(this.zyUnitService.page(page, new QueryWrapper<>(zyUnit)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyUnitService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyUnit 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyUnit zyUnit) {
        return success(this.zyUnitService.save(zyUnit));
    }

    /**
     * 修改数据
     *
     * @param zyUnit 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyUnit zyUnit) {
        return success(this.zyUnitService.updateById(zyUnit));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyUnitService.removeByIds(idList));
    }

    /**
     * 单元楼查询分页

     */
    @GetMapping("/getUnitList")
    public Result getUnitList( ZyUnit zyUnit, Pageable pageable){
        Result unitList = zyUnitService.getUnitList(zyUnit, pageable);
        return unitList;

    }

    /**
     * 新增单元楼
     * @param request
     * @param zyUnit
     * @return
     */
    @PostMapping("/insertUnit")
    public Result insertUnit(HttpServletRequest request,@RequestBody ZyUnit zyUnit) throws Exception {
        zyUnit.setUnitId(snowflakeManager.nextId()+"");
        System.err.println(zyUnit.toString());
        SysUser user = this.requestUtil.getUser(request);
        zyUnit.setCreateBy(user.getUserName());
        zyUnit.setCreateTime(LocalDateTime.now().toString());
        return zyUnitService.insertUnit(zyUnit);
    }

    /**
     * 修改单元楼
     * @param request
     * @param zyUnit
     * @return
     */
    @PutMapping("/updateUnit")
    public Result updateUnit(HttpServletRequest request,@RequestBody ZyUnit zyUnit)
    {
        SysUser user = this.requestUtil.getUser(request);
        zyUnit.setUpdateBy(user.getUserName());
        zyUnit.setUpdateTime(LocalDateTime.now().toString());
        return zyUnitService.updateUnit(zyUnit);
    }

    @DeleteMapping("/deleteUnit")
    public Result deleteUnit(@RequestBody List<String> unitIds){
        System.err.println(unitIds);
         return zyUnitService.deleteUnit(unitIds);
    }


    @GetMapping("/getExcel")
    public void getExcel(@RequestParam("unitIds") ArrayList<Integer> unitIds, HttpServletResponse response) throws IOException {
        List<ZyUnit> zyUnits = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        //执行查询角色列表的sql语句,但不包括del_flag为2的
        if (unitIds == null || unitIds.size() == 0) {
            zyUnits = zyUnitService.getAll();
        } else {
            //执行查询角色列表的sql语句
            zyUnits = zyUnitService.getUnitById(unitIds);
        }
        String fileName = URLEncoder.encode("角色表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), GetUnitExcelDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(zyUnits);

    }

}

