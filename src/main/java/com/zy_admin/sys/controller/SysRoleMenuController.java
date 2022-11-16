package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.log.BusinessType;
import com.zy_admin.common.core.log.MyLog;
import com.zy_admin.sys.entity.SysRoleMenu;
import com.zy_admin.sys.service.SysRoleMenuService;
import com.zy_admin.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:41
 */
@RestController
@RequestMapping("sysRoleMenu")
public class SysRoleMenuController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param sysRoleMenu 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysRoleMenu> page, SysRoleMenu sysRoleMenu) {
        return success(this.sysRoleMenuService.page(page, new QueryWrapper<>(sysRoleMenu)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysRoleMenuService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRoleMenu 实体对象
     * @return 新增结果
     */
    @PostMapping
    @MyLog(title = "新增角色菜单", optParam = "#{sysRoleMenu}", businessType = BusinessType.OTHER)
    public R insert(@RequestBody SysRoleMenu sysRoleMenu) {
        return success(this.sysRoleMenuService.save(sysRoleMenu));
    }

    /**
     * 修改数据
     *
     * @param sysRoleMenu 实体对象
     * @return 修改结果
     */
    @PutMapping
    @MyLog(title = "修改角色菜单", optParam = "#{sysRoleMenu}", businessType = BusinessType.OTHER)
    public R update(@RequestBody SysRoleMenu sysRoleMenu) {
        return success(this.sysRoleMenuService.updateById(sysRoleMenu));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @MyLog(title = "删除角色菜单", optParam = "#{idList}", businessType = BusinessType.OTHER)
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysRoleMenuService.removeByIds(idList));
    }

    @GetMapping("/getMenuIds")
    public Result getMenuIds(String roleId){
        return sysRoleMenuService.getMenuIdsByRoleId(roleId);
    }
}

