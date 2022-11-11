package com.zy_admin.sys.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.service.SysRoleMenuService;
import com.zy_admin.sys.service.SysRoleService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRoleExcelDto)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
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
     * 用于批量导出角色列表数据
     *
     * @param roleIds
     * @param response
     */
    @PostMapping("/getExcel")
    public void getExcel(@RequestBody ArrayList<Integer> roleIds, HttpServletResponse response) throws IOException {
        List<SysRole> sysRoles = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        //执行   查询角色列表的sql语句   但不包括del_flag为2的
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
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), SysRole.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(sysRoles);

    }

    /**
     * 分页查询所有数据
     *
     * @param pageable 分页对象
     * @param sysRole  查询实体
     * @return 所有数据
     */
    @GetMapping("/selectRoleByLimit")
    public Result selectRoleByLimit(SysRole sysRole, Pageable pageable, String startTime, String endTime) {
        Result result = sysRoleService.selectRoleByLimit(sysRole, pageable, startTime, endTime);
        System.out.println(result.toString());
        return result;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysRoleService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRole 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysRole sysRole) {
        return success(this.sysRoleService.save(sysRole));
    }

    /**
     * 修改数据
     *
     * @param sysRole 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody SysRole sysRole) {
        return this.sysRoleService.changeStatus(sysRole);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@RequestParam String[] idList) {
        List<Integer> idList1 = new ArrayList<Integer>();
        Result result = new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            for (String str : idList) {
                idList1.add(Integer.valueOf(str));
                if("1".equals(str)){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 添加角色及其权限
     *
     * @param roleAndRoleMenu
     * @return
     */
    @PostMapping("/addRole")
    public Result insert(@RequestBody RoleAndRoleMenu roleAndRoleMenu) {
        roleAndRoleMenu.setCreateTime(LocalDateTime.now().toString());
        roleAndRoleMenu.setDeptCheckStrictly(null);
        roleAndRoleMenu.setMenuCheckStrictly(null);
        roleAndRoleMenu.setDelFlag("0");
        Result insert = this.sysRoleService.insert(roleAndRoleMenu);
        System.out.println(insert);
        return insert;
    }

    /**
     * 修改角色及其权限
     *
     * @param roleAndRoleMenu
     * @return
     */
    @PutMapping("/updateRole")
    public Result update(@RequestBody RoleAndRoleMenu roleAndRoleMenu) {
        roleAndRoleMenu.setDeptCheckStrictly(null);
        roleAndRoleMenu.setMenuCheckStrictly(null);
        return sysRoleService.update(roleAndRoleMenu);
    }
}

