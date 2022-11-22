package com.zy_admin.sys.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDeptService;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * 部门表(SysDept)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@Api(value = "sysDept", tags = {"部门表(SysDept)表控制层"})
@RestController
@RequestMapping("sysDept")
public class SysDeptController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private RequestUtil requestUtil;

    /**
     * 通过条件搜索部门
     * @param sysDept 部门对象
     * @return 查询到的部门结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysDept", name = "sysDept", value = "部门对象", required = true)
    })
    @ApiOperation(value = "获取部门列表", notes = "通过条件搜索部门", httpMethod = "GET")
    @GetMapping("/getDeptList")
    public Result getDeptList(SysDept sysDept) {
        return sysDeptService.getDeptList(sysDept);
    }
    /**
     * 新增部门
     * @param sysDept 新增的部门对象
     * @param request 前端请求
     * @return 新增部门结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDept", name = "sysDept", value = "新增的部门对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "添加部门", notes = "新增部门", httpMethod = "POST")
    @PostMapping("/insertDept")
    @MyLog(title = "部门管理", optParam = "#{sysDept}", businessType = BusinessType.INSERT)
    public Result insertDept(@RequestBody SysDept sysDept, HttpServletRequest request) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //从token获值
        try {
            SysUser user = requestUtil.getUser(request);
            sysDept.setDelFlag("0");
            sysDept.setStatus("0");
            sysDept.setCreateTime(LocalDateTime.now().toString());
            sysDept.setCreateBy(user.getUserName());
            result = this.sysDeptService.insertDept(sysDept);
        } catch (Exception e) {
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }
        return result;
    }
    /**
     * 修改部门
     * @param sysDept 存放部门对象
     * @param request 前端请求
     * @return 修改部门的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysDept", name = "sysDept", value = "存放部门对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "修改部门", notes = "修改部门", httpMethod = "PUT")
    @PutMapping("/updateDept")
    @MyLog(title = "部门管理", optParam = "#{sysDept}", businessType = BusinessType.UPDATE)
    public Result updateDept(@RequestBody SysDept sysDept, HttpServletRequest request) {
        SysUser user = requestUtil.getUser(request);
        sysDept.setUpdateBy(user.getUserName());
        sysDept.setUpdateTime(LocalDateTime.now().toString());
        return this.sysDeptService.updateDept(sysDept);
    }
    /**
     * 删除部门
     * @param idList 被删除部门的主键数组
     * @return 删除的部门结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String[]", name = "idList", value = "被删除部门的主键数组", required = true)
    })
    @ApiOperation(value = "删除部门", notes = "删除部门", httpMethod = "DELETE")
    @DeleteMapping("/deleteDept")
    @ApiImplicitParam(name="idList",value = "部门ID集合",required = true)
    @MyLog(title = "部门管理", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result deleteDept(@RequestParam String[] idList) {
        List<Integer> idList1 = new ArrayList<>();
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            for (String str : idList) {
                //把选中的id传到集合里面
                idList1.add(Integer.valueOf(str));
            }
            //修改字典表
            result = this.sysDeptService.deleteDept(idList1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

