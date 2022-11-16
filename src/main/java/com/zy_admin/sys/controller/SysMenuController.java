package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.log.BusinessType;
import com.zy_admin.common.log.MyLog;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysMenuService;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtils;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
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
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysMenu 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysMenu> page, SysMenu sysMenu) {
        return success(this.sysMenuService.page(page, new QueryWrapper<>(sysMenu)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysMenuService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实体对象
     * @return 新增结果
     */
    @PostMapping("/addMenu")
    @MyLog(title = "新增菜单", optParam = "#{sysMenu}", businessType = BusinessType.OTHER)
    public Result insert(@RequestBody SysMenu sysMenu, HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        Result result = this.sysUserService.queryById(userId);
        try {
            SysUser user = (SysUser) result.getData();
            sysMenu.setCreateTime(LocalDateTime.now().toString());
            sysMenu.setCreateBy(user.getUserName());
            result=this.sysMenuService.insertMenu(sysMenu);
        } catch (Exception e) {
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }finally {
            return result;
        }
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实体对象
     * @return 修改结果
     */
    @PutMapping
    @MyLog(title = "修改菜单", optParam = "#{sysMenu}", businessType = BusinessType.OTHER)
    public R update(@RequestBody SysMenu sysMenu) {
        return success(this.sysMenuService.updateById(sysMenu));
    }

    /**
     * id删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById")
    @MyLog(title = "根据id删除菜单", optParam = "#{id}", businessType = BusinessType.OTHER)
    public Result deleteById(@RequestParam String id) {
        return this.sysMenuService.deteleById(Long.valueOf(id));
    }

    /**
     * 批量删除
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @MyLog(title = "批量删除菜单", optParam = "#{idList}", businessType = BusinessType.OTHER)
    public Result deleteByIdList(@RequestParam String[] idList) {
        List<Long> idList1=new ArrayList<Long>();
        for (String str : idList) {
            idList1.add(Long.valueOf(str));
        }
        Result result = this.sysMenuService.deleteByIdList(idList1);
        return result;
    }

    /**
     * 获取所有菜单
     * @return 菜单结果
     */
    @RequestMapping("/getMenus")
    public Result getMenuList(HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        return this.sysMenuService.getAllMenu(userId);
    }
    /**
     * 菜单的条件搜索
     * @param menu
     * @return
     */
    @GetMapping("/queryMenus")
    public Result queryAllMenu(SysMenu menu){
        return this.sysMenuService.queryAllMenu(menu);
    }

    /**
     * 修改菜单
     * @param sysMenu
     * @return
     */
    @PutMapping("/updateMenu")
    @MyLog(title = "修改菜单", optParam = "#{sysMenu}", businessType = BusinessType.OTHER)
    public Result updateMenu(@RequestBody SysMenu sysMenu){
        Result result = this.sysMenuService.updateMenu(sysMenu);
        return result;
    }

    @GetMapping("/getMenuTree")
    public Result getMenuTree(){
        return this.sysMenuService.getMenuTrees();
    }
}

