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
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.service.ZyBuildingService;
import com.zy_admin.sys.entity.SysUser;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 楼栋 (ZyBuilding)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@Api(value = "zyBuilding", tags = {"楼栋 (ZyBuilding)表控制层"})
@RestController
@RequestMapping("zyBuilding")
public class ZyBuildingController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyBuildingService zyBuildingService;
    @Resource
    private RequestUtil requestUtil;

    /**
     * 用于批量导出楼层数据
     * @param buildingIds 获取楼层id
     * @param communityId 获取小区id
     * @param response 前端响应
     * @return 导出的楼层结果集
     * @throws IOException 抛出数据流异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "buildingIds", value = "获取楼层id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "communityId", value = "获取小区id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端响应", required = true)
    })
    @ApiOperation(value = "用于批量导出楼层数据", notes = "用于批量导出楼层数据", httpMethod = "GET")
    @MyLog(title = "楼层导出", optParam = "#{buildingIds}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    @PreAuthorize("hasAnyAuthority('system:building:export')")
    public Result getExcel(@RequestParam("buildingIds") ArrayList<String> buildingIds, @RequestParam("communityId") String communityId, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyBuilding> zyBuildingList;
        //如果前台传的集合为空或者长度为0.则全部导出。
        if (buildingIds == null || buildingIds.size() == 0) {
            zyBuildingList = zyBuildingService.getBuildingLists(communityId);
        } else {
            zyBuildingList = zyBuildingService.queryZyBuildingById(buildingIds);
        }
        String fileName = URLEncoder.encode("楼层信息表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), ZyBuilding.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("楼层信息");
        excel.doWrite(zyBuildingList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 删除楼层
     * @param idList 要删除的楼层id
     * @return 返回
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "idList", value = "要删除的楼层id", required = true)
    })
    @ApiOperation(value = "删除楼层", notes = "删除楼层", httpMethod = "DELETE")
    @DeleteMapping
    @MyLog(title = "楼层信息", optParam = "#{idList}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAnyAuthority('system:building:remove')")
    public Result delete(@RequestParam("idList") ArrayList<String> idList){
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //修改楼层表
            result = this.zyBuildingService.deleteByIdList(idList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 更新楼层信息
     * @param zyBuilding 要更新的楼层信息
     * @param request 前端请求
     * @return 更新楼层结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyBuilding", name = "zyBuilding", value = "要更新的楼层信息", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "更新楼层信息", notes = "更新楼层信息", httpMethod = "PUT")
    @PutMapping("/updateZyBuilding")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "楼层信息", optParam = "#{zyBuilding}", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyAuthority('system:building:edit')")
    public Result updateZyBuilding(@RequestBody ZyBuilding zyBuilding,HttpServletRequest request){
        SysUser user = requestUtil.getUser(request);
        zyBuilding.setUpdateBy(user.getUserName());
        return zyBuildingService.updateZyBuilding(zyBuilding);
    }

    /**
     * 新增楼层
     * @param zyBuilding 要新增的楼层信息
     * @param request 前端请求
     * @return  查询的楼层结果集
     * @throws Exception 将存在的异常抛出
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyBuilding", name = "zyBuilding", value = "要新增的楼层信息", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "新增楼层", notes = "新增楼层", httpMethod = "POST")
    @PostMapping("/addZyBuilding")
    @MyLog(title = "楼层信息", optParam = "#{zyBuilding}", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAnyAuthority('system:building:add')")
    public Result insertDictType(@RequestBody ZyBuilding zyBuilding, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyBuilding.setCreateBy(user.getUserName());
        return this.zyBuildingService.insertZyBuilding(zyBuilding);
    }

    /**
     * 分页查询
     * @param zyBuilding  查询的楼层对象
     * @param pageable 分页对象
     * @return 返回成功或错误信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyBuilding", name = "zyBuilding", value = "查询的楼层对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true)
    })
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
    @GetMapping("/selectBuildLimit")
    @PreAuthorize("hasAnyAuthority('ROLE_common','ROLE_admin')")
    public Result selectBuildLimit(ZyBuilding zyBuilding, Pageable pageable){
        return zyBuildingService.selectBuildLimit(zyBuilding, pageable);
    }

    /**
     * 通过主键查询单条数据
     * @param id 查询的楼层主键id
     * @return 返回查询数据条数
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "查询的楼层主键id", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_common','ROLE_admin')")
    public Result selectOne(@PathVariable String id) {
        return zyBuildingService.queryById(id);
    }
    /**
     * 根据小区ID获取下面的楼栋、单元集合
     * @param id 查询的小区主键id
     * @return 返回查询数据条数
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "查询的小区主键id", required = true)
    })
    @ApiOperation(value = "根据小区ID获取下面的楼栋、单元集合", notes = "根据小区ID获取下面的楼栋、单元集合", httpMethod = "GET")
    @GetMapping("/buildingList/{id}")
    public Result getBuildingAndUnitListByCommunityId(@PathVariable String id) {
        return zyBuildingService.getBuildingAndUnitListByCommunityId(id);
    }
}

