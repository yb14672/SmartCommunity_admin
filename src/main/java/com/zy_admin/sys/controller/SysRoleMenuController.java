package com.zy_admin.sys.controller;

import com.zy_admin.sys.entity.SysRoleMenu;
import com.zy_admin.sys.service.SysRoleMenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色和菜单关联表(SysRoleMenu)表控制层
 *
 * @author makejava
 * @since 2022-11-08 14:56:26
 */
@RestController
@RequestMapping("sysRoleMenu")
public class SysRoleMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询
     *
     * @param sysRoleMenu 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<SysRoleMenu>> queryByPage(SysRoleMenu sysRoleMenu, PageRequest pageRequest) {
        return ResponseEntity.ok(this.sysRoleMenuService.queryByPage(sysRoleMenu, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<SysRoleMenu> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.sysRoleMenuService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRoleMenu 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<SysRoleMenu> add(SysRoleMenu sysRoleMenu) {
        return ResponseEntity.ok(this.sysRoleMenuService.insert(sysRoleMenu));
    }

    /**
     * 编辑数据
     *
     * @param sysRoleMenu 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<SysRoleMenu> edit(SysRoleMenu sysRoleMenu) {
        return ResponseEntity.ok(this.sysRoleMenuService.update(sysRoleMenu));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.sysRoleMenuService.deleteById(id));
    }

}

