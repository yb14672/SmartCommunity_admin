package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.util.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRoleExcelDto)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 获取所有角色
     *
     */

    Result getAllRole(SysRole sysRole);

    /**
     * 根据id列表查询
     *
     * @param roleIds
     * @return
     */
    List<SysRole> queryRoleById(ArrayList<Integer> roleIds);

    /**
     * 获取所有角色id
     *
     * @return
     */
    List<SysRole> getRoleLists();

    /**
     * 根据ID集合批量删除
     *
     * @param idList 角色ID列表
     * @return
     */
    Result deleteByIdList(List<Integer> idList);

    /**
     * 角色搜索并分页
     *
     * @param sysRole
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    Result selectRoleByLimit(SysRole sysRole, Pageable pageable, String startTime, String endTime);

    /**
     * 添加角色及其权限
     *
     * @param roleAndRoleMenu
     * @return
     */
    Result insert(RoleAndRoleMenu roleAndRoleMenu);

    /**
     * 修改角色及其权限
     *
     * @param roleAndRoleMenu
     * @return
     */
    Result update(RoleAndRoleMenu roleAndRoleMenu);

    /**
     * 修改角色状态
     *
     * @param sysRole
     * @return
     */
    Result changeStatus(SysRole sysRole);

    /**
     * 检查角色名是否唯一
     * @param type
     * @param roleAndRoleMenu
     * @return
     */
    Boolean checkRoleNameUnique(int type, RoleAndRoleMenu roleAndRoleMenu);

    /**
     * 检查角色名是否唯一
     * @param type
     * @param roleAndRoleMenu
     * @return
     */
    Boolean checkRoleKeyUnique(int type, RoleAndRoleMenu roleAndRoleMenu);
    /**
     * 获取所有除去管理员以外的角色并分页
     * @param page
     * @return
     */
    Result getRoleList(Page page);
}

