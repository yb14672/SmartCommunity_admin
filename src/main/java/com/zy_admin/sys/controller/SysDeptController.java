package com.zy_admin.sys.controller;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDeptService;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
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
    @DeleteMapping("/deleteDept")
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

