package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.RedisService;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtils;
import com.zy_admin.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 用户信息表(SysUser)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysUser 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysUser> page, SysUser sysUser) {
        return success(this.sysUserService.page(page, new QueryWrapper<>(sysUser)));
    }

    /**
     * 根据用户ID获取头像
     * @param request
     * @return
     */
    @GetMapping("/getAvatarById")
    public Result getAvatarById(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        Result avatarById = this.sysUserService.getAvatarById(id);
        return avatarById;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysUserService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysUser 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysUser sysUser) {
        return success(this.sysUserService.save(sysUser));
    }

    /**
     * 修改数据
     *
     * @param sysUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysUser sysUser) {
        return success(this.sysUserService.updateById(sysUser));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysUserService.removeByIds(idList));
    }

    /**
     * 登录
     * @param sysUser
     * @return
     */
    @PostMapping("/login")
    public Result login(SysUser sysUser,HttpServletRequest request) {
        Result result = sysUserService.login(sysUser);
        return result;
    }

    /**
     * 根据ID获取用户信息
     * @param request 用户ID
     * @return 查询的用户结果+http状态
     */
    @GetMapping("/personal")
    public Result personal(HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        return this.sysUserService.personal(memberIdByJwtToken);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/id/{id}")
    public Result selectOne(@PathVariable String id) {
        return this.sysUserService.queryById(id);
    }

    /**
     * 通过账号查询单条数据
     *
     * @param name 主键
     * @return 单条数据
     */
    @GetMapping("/name/{name}")
    public Result queryByName(@PathVariable String name) {
        return this.sysUserService.queryByName(name);
    }

    /**
     * 修改用户基本信息
     * @param sysUser
     * @return
     */
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody SysUser sysUser){
        return this.sysUserService.updateUser(sysUser);
    }

    /**
     * 修改密码
     * @param sysUser
     * @return
     */
    @PutMapping("/resetPwd")
    public Result resetPwd(@RequestBody SysUser sysUser){
        return this.sysUserService.resetPwd(sysUser);
    }
}

