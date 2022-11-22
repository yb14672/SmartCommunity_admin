package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysMenuService;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表(SysMenu)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserService sysUserService;
    /**
     * 获取菜单树
     * @return 菜单树的结果集
     */
    @GetMapping("/getMenuTree")
    public Result getMenuTree() {
        return this.sysMenuService.getMenuTrees();
    }
    /**
     * 菜单的条件搜索
     * @param menu 查询的菜单对象
     * @return
     */
    @GetMapping("/queryMenus")
    public Result queryAllMenu(SysMenu menu) {
        return this.sysMenuService.queryAllMenu(menu);
    }

    /**
     * 获取所有菜单
     * @param request 前端请求
     * @return 所有菜单的结果集
     */
    @GetMapping("/getMenus")
    public Result getMenuList(HttpServletRequest request) {
        String userId = JwtUtil.getMemberIdByJwtToken(request);
        return this.sysMenuService.getAllMenu(userId);
    }
    /**
     * 新增数据
     * @param sysMenu 新增菜单对象
     * @param request 前端请求
     * @return 新增菜单结果集
     */
    @PostMapping("/addMenu")
    @MyLog(title = "菜单管理", optParam = "#{sysMenu}", businessType = BusinessType.INSERT)
    public Result insert(@RequestBody SysMenu sysMenu, HttpServletRequest request) {
        String userId = JwtUtil.getMemberIdByJwtToken(request);
        Result result = this.sysUserService.queryById(userId);
        try {
            SysUser user = (SysUser) result.getData();
            sysMenu.setCreateTime(LocalDateTime.now().toString());
            sysMenu.setCreateBy(user.getUserName());
            result = this.sysMenuService.insertMenu(sysMenu);
        } catch (Exception e) {
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
        return result;
    }

    /**
     * 修改数据
     * @param sysMenu 修改的菜单信息
     * @return 根据id修改菜单结果集
     */
    @PutMapping("/updateMenu")
    @MyLog(title = "菜单管理", optParam = "#{sysMenu}", businessType = BusinessType.UPDATE)
    public Result updateMenu(@RequestBody SysMenu sysMenu) {
        Result result = this.sysMenuService.updateMenu(sysMenu);
        return result;
    }
    /**
     * 根据id删除菜单
     * @param id 菜单id
     * @return 根据id查出的菜单结果集
     */
    @DeleteMapping("/deleteById")
    @MyLog(title = "菜单管理", optParam = "#{id}", businessType = BusinessType.DELETE)
    public Result deleteById(@RequestParam String id) {
        return this.sysMenuService.deteleById(Long.valueOf(id));
    }
    /**
     * 批量删除菜单
     * @param idList 菜单id数组
     * @return 逻辑删除菜单结果集
     */
    @DeleteMapping
    @MyLog(title = "菜单管理", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result deleteByIdList(@RequestParam String[] idList) {
        List<Long> idList1 = new ArrayList<Long>();
        for (String str : idList) {
            idList1.add(Long.valueOf(str));
        }
        return this.sysMenuService.deleteByIdList(idList1);
    }
}

