package com.zy_admin.community.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.OwnerParkExcelDto;
import com.zy_admin.community.dto.OwnerParkListDto;
import com.zy_admin.community.dto.ZyOwnerParkDto;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.service.ZyOwnerParkService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
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
 * 房屋绑定表 (ZyOwnerPark)表控制层
 *
 * @author makejava
 * @since 2022-12-01 15:50:35
 */
@Api(value = "zyOwnerPark", tags = {"房屋绑定表 (ZyOwnerPark)表控制层"})
@RestController
@RequestMapping("zyOwnerPark")
public class ZyOwnerParkController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerParkService zyOwnerParkService;

    /**
     * 请求
     */
    @Resource
    private RequestUtil requestUtil;

    /**
     * 查询记录所有者公园
     *
     * @param request 请求
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "查询记录所有者公园", notes = "查询记录所有者公园")
    @GetMapping("/queryOwnerParkRecord")
    public Result queryOwnerParkRecord(HttpServletRequest request){
        String ownerId = requestUtil.getOwnerId(request);
        return this.zyOwnerParkService.queryOwnerParkRecordByOwnerId(ownerId);
    }

    /**
     * 导出下面的
     *
     * @param ids         id列表--没有导出当前全部车位
     * @param communityId 小区id
     * @param response    相应对象
     * @return Excel表格
     * @throws IOException ioException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "ids", value = "id列表--没有导出当前全部车位", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "communityId", value = "小区id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "相应对象", required = true)
    })
    @ApiOperation(value = "导出", notes = "导出", httpMethod = "GET")
    @GetMapping("/export")
    @MyLog(title = "车位信息", optParam = "#{ids},#{communityId}", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAnyAuthority('system:parkOwner:export')")
    public Result getExcel(@RequestParam("ids") ArrayList<String> ids, String communityId, HttpServletResponse response) throws IOException {
        System.out.println(ids+" "+communityId);
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        Result result1 = zyOwnerParkService.getListByIdList(ids, communityId);
        List<OwnerParkExcelDto> list = (List<OwnerParkExcelDto>) result1.getData();
        String fileName = URLEncoder.encode("车位信息", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), OwnerParkExcelDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("车位信息");
        excel.doWrite(list);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 查询未被绑定和启用的车位
     *
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "查询未被绑定和启用的车位", notes = "查询未被绑定和启用的车位", httpMethod = "GET")
    @GetMapping("/selectNoBindingAndStatusPark")
    public Result selectNoBindingAndStatusPark(HttpServletRequest request){
        String ownerId = requestUtil.getOwnerId(request);
        return this.zyOwnerParkService.selectNoBindingAndStatusPark(ownerId);
    }

    /**
     * 删除车位审核
     * @param idList 要删除的投诉建议id
     * @return 返回
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "idList", value = "要删除的投诉建议id", required = true)
    })
    @ApiOperation(value = "删除车位审核", notes = "删除车位审核", httpMethod = "DELETE")
    @DeleteMapping
    public Result deleteOwnerParkByIds(@RequestParam("idList") ArrayList<String> idList){
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            result = this.zyOwnerParkService.deleteOwnerParkByIds(idList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "ownerParkId", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "DELETE")
    @DeleteMapping("/deleteOwnerPark")
    public Result deleteOwnerPark(HttpServletRequest request,String ownerParkId) throws Exception {
        return zyOwnerParkService.deleteOwnerPark(ownerParkId,request);
    }
    /**
     * 修改车位审核
     * @param zyOwnerPark 要更新的车位审核对象
     * @param request 前端请求
     * @return 更新车位审核结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwnerPark", name = "zyOwnerPark", value = "要更新的车位审核对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "修改车位审核", notes = "修改车位审核", httpMethod = "PUT")
    @PutMapping("/updateOwnerPark")
    @PreAuthorize("hasAnyAuthority('system:parkOwner:edit')")
    public Result updateOwnerPark(@RequestBody ZyOwnerPark zyOwnerPark, HttpServletRequest request){
        ZyOwner owner = this.requestUtil.getOwner(request);
        zyOwnerPark.setUpdateBy(owner.getOwnerRealName());
        zyOwnerPark.setOwnerId(owner.getOwnerId());
        zyOwnerPark.setUpdateTime(LocalDateTime.now().toString());
        return zyOwnerParkService.updateOwnerPark(zyOwnerPark);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "OwnerParkListDto", name = "ownerParkListDto", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Page", name = "page", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/getOwnerParkList")
    public Result getOwnerParkList(OwnerParkListDto ownerParkListDto, Page page ) {
        return zyOwnerParkService.getOwnerParkList(ownerParkListDto, page);
    }
    /**
     * 新增车位审核
     * @param zyOwnerPark 要新增的车位审核
     * @param request 前端请求
     * @return  查询的车位审核结果集
     * @throws Exception 将存在的异常抛出
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwnerPark", name = "zyOwnerPark", value = "要新增的车位审核", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "新增车位审核", notes = "新增车位审核", httpMethod = "POST")
    @PostMapping("/insertOwnerPark")
    @PreAuthorize("hasAnyAuthority('system:parkOwner:add')")
    public Result insertOwnerPark(@RequestBody ZyOwnerPark zyOwnerPark, HttpServletRequest request) throws Exception {
        System.err.println(zyOwnerPark);
        ZyOwner owner = this.requestUtil.getOwner(request);
        zyOwnerPark.setCreateBy(owner.getOwnerRealName());
        zyOwnerPark.setCreateTime(LocalDateTime.now().toString());
        zyOwnerPark.setOwnerId(owner.getOwnerId());
        zyOwnerPark.setParkOwnerStatus("Auditing");
        return this.zyOwnerParkService.insertOwnerPark(zyOwnerPark);
    }

    /**
     * 更新车位审核状态
     *
     * @param zyOwnerPark        车位审核对象
     * @param recordAuditOpinion 记录审计意见
     * @param status             状态
     * @param request            请求
     * @return {@link Result}
     * @throws Exception 异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwnerPark", name = "zyOwnerPark", value = "车位审核对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "recordAuditOpinion", value = "记录审计意见", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "status", value = "状态", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "修改车位审核的状态", notes = "修改车位审核的状态", httpMethod = "PUT")
    @PutMapping("/updateOwnerParkStatus")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "车位审核", optParam = "#{zyOwnerPark}", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyAuthority('system:parkOwner:edit')")
    public Result updateOwnerParkStatus(@RequestBody ZyOwnerPark zyOwnerPark, String recordAuditOpinion, String status, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyOwnerPark.setUpdateBy(user.getUserName());
        //从前端把那个更新的状态带过来
        zyOwnerPark.setParkOwnerStatus(status);
        return zyOwnerParkService.updateOwnerParkStatus(zyOwnerPark, recordAuditOpinion);
    }

    /**
     * 分页和查询车位审核
     * @param zyOwnerParkDto 车位审核对象
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyOwnerParkDto", name = "zyOwnerParkDto", value = "车位审核对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Page", name = "page", value = "", required = true)
    })
    @ApiOperation(value = "分页和查询车位审核", notes = "分页和查询车位审核", httpMethod = "GET")
    @GetMapping("selectAllParkLimit")
    @PreAuthorize("hasAnyAuthority('system:parkOwner:query')")
    public Result selectAllParkLimit(ZyOwnerParkDto zyOwnerParkDto, Page page){
        return zyOwnerParkService.selectAllParkLimit(zyOwnerParkDto,page);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("{id}")
    public ResponseEntity<ZyOwnerPark> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.zyOwnerParkService.queryById(id));
    }
}

