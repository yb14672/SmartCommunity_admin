package com.zy_admin.sys.controller;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.service.SysRoleMenuService;
import com.zy_admin.sys.service.SysRoleService;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
 * 角色信息表(SysRoleExcelDto)表控制层
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Api(value = "sysRole", tags = {"角色信息表(SysRoleExcelDto)表控制层"})
@RestController
@RequestMapping("sysRole")
public class SysRoleController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    SysRoleService sysRoleService;
    @Resource
    SysRoleMenuService sysRoleMenuService;
    /**
     * 获取所有角色数据
     * @param sysRole 角色对象
     * @return 查询的所有角色结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysRole", name = "sysRole", value = "角色对象", required = true)
    })
    @ApiOperation(value = "获取所有角色数据", notes = "获取所有角色数据", httpMethod = "GET")
    @GetMapping("/getAllRole")
    public Result getAllRole(SysRole sysRole) {
        return sysRoleService.getAllRole(sysRole);
    }
    /**
     * 获取所有除去管理员以外的角色并分页
     * @param page 分页对象
     * @return 查询所有角色结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page", name = "page", value = "分页对象", required = true)
    })
    @ApiOperation(value = "获取所有除去管理员以外的角色并分页", notes = "获取所有除去管理员以外的角色并分页", httpMethod = "GET")
    @GetMapping("/getRoleList")
    public Result getRoleList(Page page) {
        return this.sysRoleService.getRoleList(page);
    }
    /**
     * 分页查询所有数据
     * @param pageable 分页对象
     * @param sysRole  查询角色对象
     * @return 所有查询的角色数据结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysRole", name = "sysRole", value = "查询角色对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "startTime", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "endTime", value = "", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping("/selectRoleByLimit")
    public Result selectRoleByLimit(SysRole sysRole, Pageable pageable, String startTime, String endTime) {
        return sysRoleService.selectRoleByLimit(sysRole, pageable, startTime, endTime);
    }
    /**
     * 用于批量导出角色列表数据
     * @param roleIds 角色主键
     * @param response 前端响应
     * @return 导出的角色信息结果集
     * @throws IOException 抛出数据流异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<Integer>", name = "roleIds", value = "角色主键", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端响应", required = true)
    })
    @ApiOperation(value = "用于批量导出角色列表数据", notes = "用于批量导出角色列表数据", httpMethod = "GET")
    @MyLog(title = "角色管理", optParam = "#{roleIds}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    public Result getExcel(@RequestParam("roleIds") ArrayList<Integer> roleIds, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysRole> sysRoles;
        //如果前台传的集合为空或者长度为0.则全部导出。
        //执行查询角色列表的sql语句，但不包括del_flag为2的
        if (roleIds == null || roleIds.size() == 0) {
            sysRoles = sysRoleService.getRoleLists();
        } else {
            //执行查询角色列表的sql语句
            sysRoles = sysRoleService.queryRoleById(roleIds);
        }
        String fileName = URLEncoder.encode("角色表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), SysRole.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("角色信息");
        excel.doWrite(sysRoles);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 修改角色信息
     * @param sysRole 角色对象
     * @return 修改角色信息结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysRole", name = "sysRole", value = "角色对象", required = true)
    })
    @ApiOperation(value = "修改角色信息", notes = "修改角色信息", httpMethod = "PUT")
    @PutMapping
    @MyLog(title = "角色管理", optParam = "#{sysRole}", businessType = BusinessType.UPDATE)
    public Result update(@RequestBody SysRole sysRole) {
        return this.sysRoleService.changeStatus(sysRole);
    }
    /**
     * 删除角色信息
     * @param idList 角色主键
     * @return 删除角色信息结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String[]", name = "idList", value = "角色主键", required = true)
    })
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息", httpMethod = "DELETE")
    @DeleteMapping
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "角色管理", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result delete(@RequestParam String[] idList) {
        List<Integer> idList1 = new ArrayList<>();
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            for (String str : idList) {
                idList1.add(Integer.valueOf(str));
                if ("1".equals(str)) {
                    result.setMeta(ResultTool.fail(ResultCode.ADMIN_NOT_ALLOWED_DELETE));
                    return result;
                }
            }
            //修改角色表
            result = this.sysRoleService.deleteByIdList(idList1);
            //删除权限表
            this.sysRoleMenuService.deleteByIdList(idList1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 添加角色及其权限
     * @param roleAndRoleMenu 角色和角色菜单对象
     * @return 返回添加角色及其权限的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "RoleAndRoleMenu", name = "roleAndRoleMenu", value = "角色和角色菜单对象", required = true)
    })
    @ApiOperation(value = "添加角色及其权限", notes = "添加角色及其权限", httpMethod = "POST")
    @PostMapping("/addRole")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "角色管理", optParam = "#{roleAndRoleMenu}", businessType = BusinessType.INSERT)
    public Result insert(@RequestBody RoleAndRoleMenu roleAndRoleMenu) {
        roleAndRoleMenu.setCreateTime(LocalDateTime.now().toString());
        roleAndRoleMenu.setDeptCheckStrictly(null);
        roleAndRoleMenu.setMenuCheckStrictly(null);
        roleAndRoleMenu.setDelFlag("0");
        return this.sysRoleService.insert(roleAndRoleMenu);
    }

    /**
     * 修改角色及其权限
     * @param roleAndRoleMenu 角色和角色菜单对象
     * @return 返回修改角色及其权限的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "RoleAndRoleMenu", name = "roleAndRoleMenu", value = "角色和角色菜单对象", required = true)
    })
    @ApiOperation(value = "修改角色及其权限", notes = "修改角色及其权限", httpMethod = "PUT")
    @PutMapping("/updateRole")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "角色管理", optParam = "#{roleAndRoleMenu}", businessType = BusinessType.UPDATE)
    public Result update(@RequestBody RoleAndRoleMenu roleAndRoleMenu) {
        roleAndRoleMenu.setDeptCheckStrictly(null);
        roleAndRoleMenu.setMenuCheckStrictly(null);
        return sysRoleService.update(roleAndRoleMenu);
    }
}

