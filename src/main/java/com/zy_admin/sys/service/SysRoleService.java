package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.common.core.Result.Result;

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
     * 获取所有角色数据
     * @param sysRole 角色对象
     * @return 查询的所有角色结果集
     */
    Result getAllRole(SysRole sysRole);
    /**
     * 根据id列表查询
     * @param roleIds 角色主键集合
     * @return 角色集合
     */
    List<SysRole> queryRoleById(ArrayList<Integer> roleIds);
    /**
     * 获取所有角色id
     * @return 角色集合
     */
    List<SysRole> getRoleLists();
    /**
     * 根据ID集合批量删除
     * @param idList 角色ID集合
     * @return 删除的角色结果集
     */
    Result deleteByIdList(List<Integer> idList);
    /**
     * 选择角色限制
     * @param sysRole   系统作用
     * @param pageable  分页对象
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 查询角色结果集
     */
    Result selectRoleByLimit(SysRole sysRole, Pageable pageable, String startTime, String endTime);
    /**
     * 添加角色及其权限
     * @param roleAndRoleMenu 角色和角色菜单
     * @return 查询角色结果集
     */
    Result insert(RoleAndRoleMenu roleAndRoleMenu);
    /**
     * 更新角色及其权限
     * @param roleAndRoleMenu 角色和角色菜单
     * @return 更新角色结果集
     */
    Result update(RoleAndRoleMenu roleAndRoleMenu);
    /**
     * 修改角色状态
     * @param sysRole 角色对象
     * @return 修改角色结果集
     */
    Result changeStatus(SysRole sysRole);
    /**
     * 检查角色名唯一
     * @param type 类型用于新增为0或者修改1
     * @param roleAndRoleMenu 角色和角色菜单
     * @return 查询角色结果集
     */
    Boolean checkRoleNameUnique(int type, RoleAndRoleMenu roleAndRoleMenu);
    /**
     * 检查角色名是否唯一
     *
     * @param type 类型用于新增为0或者修改1
     * @param roleAndRoleMenu 角色和角色菜单
     * @return 成功或失败的结果集
     */
    Boolean checkRoleKeyUnique(int type, RoleAndRoleMenu roleAndRoleMenu);
    /**
     * 获取所有除去管理员以外的角色并分页
     * @param page 分页对象
     * @return 查询角色结果集
     */
    Result getRoleList(Page page);
}

