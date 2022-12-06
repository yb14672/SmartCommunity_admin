package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.OwnerRoomExcel;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyOwnerService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 业主 (ZyOwner)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Api(value = "zyOwner", tags = {"业主 (ZyOwner)表控制层"})
@RestController
@RequestMapping("zyOwner")
public class ZyOwnerController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerService zyOwnerService;

    /**
     * 请求工具类
     */
    @Resource
    private RequestUtil requestUtil;




    /**
     *
     *
     * @param zyOwner 业主
     * @param request 请求
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwner", name = "zyOwner", value = "业主", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "更新业主头像", notes = "更新业主头像", httpMethod = "PUT")
    @PutMapping("/updateOwnerPortrait")

    public Result updateOwnerPortrait(@RequestBody ZyOwner zyOwner, HttpServletRequest request){
        return zyOwnerService.updateOwnerPortrait(zyOwner, request);
    }

    /**]l
     * 用户修改密码
     * @param zyOwner 新密码
     * @param request 请求
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwner", name = "zyOwner", value = "新密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "用户修改密码", notes = "用户修改密码", httpMethod = "PUT")
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody ZyOwner zyOwner, HttpServletRequest request){
        return zyOwnerService.updatePassword(zyOwner, request);
    }

    /**
     * 获取业主信息
     *
     * @param request 请求
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "获取业主信息", notes = "获取业主信息", httpMethod = "GET")
    @GetMapping("/getOwner")
    public Result getOwner(HttpServletRequest request){
        String ownerId = requestUtil.getOwnerId(request);
        return this.zyOwnerService.getOwnerById(ownerId);
    }

    /**
     * 修改数据
     *
     * @param zyOwner 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwner", name = "zyOwner", value = "实体对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
    @PutMapping("/update")
    public Result update(@RequestBody ZyOwner zyOwner, HttpServletRequest request) {
        ZyOwner owner = requestUtil.getOwner(request);
        zyOwner.setOwnerId(owner.getOwnerId());
        zyOwner.setUpdateBy(zyOwner.getOwnerNickname());
        return this.zyOwnerService.ownerUpdate(zyOwner);
    }
    /**
     * 用户登录
     * @param zyOwner 手机号和密码
     * @return 登陆结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwner", name = "zyOwner", value = "手机号和密码", required = true)
    })
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public Result login(@RequestBody ZyOwner zyOwner) {
        return zyOwnerService.ownerLogin(zyOwner);
    }

    /**
     * 新增数据
     *
     * @param zyOwner 业主对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwner", name = "zyOwner", value = "业主对象", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
    @PostMapping("/register")
    public Result insert(@RequestBody ZyOwner zyOwner) throws Exception {
        zyOwner.setOwnerType("qt");
        return zyOwnerService.ownerRegister(zyOwner);
    }


    /**
     * 获取户主信息并分页
     *
     * @param zyOwner     业主信息
     * @param pageable    分页对象
     * @param communityId 社区id
     * @return 查询户主结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyOwner", name = "zyOwner", value = "业主信息", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "communityId", value = "社区id", required = true)
    })
    @ApiOperation(value = "获取户主信息并分页", notes = "获取户主信息并分页", httpMethod = "GET")
    @GetMapping("/getOwnerList")
    @PreAuthorize("hasAnyAuthority('system:owner:query')")
    public Result getOwnerList(ZyOwner zyOwner, Pageable pageable,String communityId) {
        return zyOwnerService.getOwnerList(zyOwner, pageable,communityId);
    }


    /**
     * 删除业主房屋关联
     *
     * @param request     前端请求
     * @param ownerRoomId 业主id
     * @return 删除业主结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "ownerRoomId", value = "业主id", required = true)
    })
    @ApiOperation(value = "删除业主房屋关联", notes = "删除业主房屋关联", httpMethod = "DELETE")
    @MyLog(title = "房主信息", optParam = "#{ownerRoomId}", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteOwner")
    @PreAuthorize("hasAnyAuthority('system:owner:remove')")
    public Result deleteOwnerHome(HttpServletRequest request, String ownerRoomId) {
        return zyOwnerService.deleteOwenRome(request, ownerRoomId);
    }

    /**
     * 得到Excel表格
     *
     * @param ids      id
     * @param response 响应
     * @return {@link Result}
     * @throws IOException ioException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<String>", name = "ids", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "响应", required = true)
    })
    @ApiOperation(value = "得到Excel表格", notes = "得到Excel表格", httpMethod = "GET")
    @GetMapping("/getExcel")
    @MyLog(title = "房主信息", optParam = "#{ids}", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAnyAuthority('system:owner:export')")
    public Result getExcel(@RequestParam("ownerIds") List<String> ids, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<OwnerRoomExcel> ownerRoomExcelList;
        //如果前台传的集合为空或者长度为0.则全部导出。
        if (ids == null || ids.isEmpty()) {
            ownerRoomExcelList = zyOwnerService.getLists();
        } else {
            //执行查询用户列表的sql语句
            ownerRoomExcelList = zyOwnerService.queryOwnerById(ids);
        }
        String fileName = URLEncoder.encode("房主信息表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), OwnerRoomExcel.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("用户信息");
        excel.doWrite(ownerRoomExcelList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
}

