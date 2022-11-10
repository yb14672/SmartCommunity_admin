package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDeptService;
import com.zy_admin.util.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
     * 通过条件搜索数据
     *
     * @param sysDept
     * @return
     */
    @GetMapping("/getDeptList")
    public Result getDeptList(SysDept sysDept) {
        return sysDeptService.getDeptList(sysDept);
    }

    /**
     * 新增数据
     *
     * @param sysDept 实体对象
     * @return 新增结果
     */
    @PostMapping("/insertDept")
    public Result insertDept(@RequestBody SysDept sysDept, HttpServletRequest request) {
        Result result = new Result();
        //从token获值
        System.out.println(sysDept);
        String userId = JwtUtils.getMemberIdByJwtToken(request);
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
     * 修改数据
     *
     * @param sysDept 实体对象
     * @return 修改结果
     */
    @PutMapping("/updateDept")
    public Result updateDept(@RequestBody SysDept sysDept) {
        return this.sysDeptService.updateDept(sysDept);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/deleteDept")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteDept(@RequestParam String[] idList) {
        System.out.println(Arrays.toString(idList));
        List<Integer> idList1 = new ArrayList<Integer>();
        Result result = new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            for (String str : idList) {
                //把选中的id传到集合里面
                idList1.add(Integer.valueOf(str));
                //修改字典表
                result = this.sysDeptService.deleteDept(idList1);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

