package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.service.ZyBuildingService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 楼栋 (ZyBuilding)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@RestController
@RequestMapping("zyBuilding")
public class ZyBuildingController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyBuildingService zyBuildingService;

    /**
     * 用于批量导出楼层数据
     *
     * @param buildingIds
     * @param response
     */
    @GetMapping("/getExcel")
    @MyLog(title = "导出楼层", optParam = "#{buildingIds}", businessType = BusinessType.EXPORT)
    public void getExcel(@RequestParam("buildingIds") ArrayList<String> buildingIds, HttpServletResponse response) throws IOException {
        List<ZyBuilding> zyBuildingList = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        if (buildingIds == null || buildingIds.size() == 0) {
            zyBuildingList = zyBuildingService.getBuildingLists();
        } else {
            //执行查询角色列表的sql语句
            zyBuildingList = zyBuildingService.queryZyBuildingById(buildingIds);
        }
        String fileName = URLEncoder.encode("楼层表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), ZyBuilding.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(zyBuildingList);
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @DeleteMapping
    @MyLog(title = "删除楼层类型", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result delete(@RequestParam("idList") ArrayList<String> idList){

        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {

            //修改楼层表
            result = this.zyBuildingService.deleteByIdList(idList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    /**
     * 修改楼层
     * @return
     */
    @PutMapping("/updateZyBuilding")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "修改楼层", optParam = "#{zyBuilding}", businessType = BusinessType.UPDATE)
    public Result updateZyBuilding(@RequestBody ZyBuilding zyBuilding,HttpServletRequest request){
        System.out.println(zyBuilding);
        System.out.println("controller");
        return zyBuildingService.updateZyBuilding(zyBuilding,request);
    }

    /**
     * 新增楼层
     * @param zyBuilding
     * @return
     */
    @PostMapping("/addZyBuilding")
    @MyLog(title = "新增楼层", optParam = "#{zyBuilding}", businessType = BusinessType.INSERT)
    public Result insertDictType(@RequestBody ZyBuilding zyBuilding, HttpServletRequest request) throws Exception {
        return this.zyBuildingService.insertZyBuilding(zyBuilding,request);
    }

    /**
     * 分页查询
     * @param zyBuilding
     * @param pageable
     * @return
     */
    @GetMapping("/selectBuildLimit")
    public Result selectBuildLimit(ZyBuilding zyBuilding, Pageable pageable){
        Result result = zyBuildingService.selectBuildLimit(zyBuilding, pageable);
        return result;
    }

    /**
     * 通过主键查询单条数据,修改里面加上id
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable String id) {
        return zyBuildingService.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param zyBuilding 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyBuilding zyBuilding) {
        return success(this.zyBuildingService.save(zyBuilding));
    }

    /**
     * 修改数据
     *
     * @param zyBuilding 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyBuilding zyBuilding) {
        return success(this.zyBuildingService.updateById(zyBuilding));
    }

}

