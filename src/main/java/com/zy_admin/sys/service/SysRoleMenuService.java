package com.zy_admin.sys.service;

import com.zy_admin.sys.entity.SysRoleMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务接口
 *
 * @author makejava
 * @since 2022-11-08 14:56:27
 */
public interface SysRoleMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    SysRoleMenu queryById(Long roleId);

    /**
     * 分页查询
     *
     * @param sysRoleMenu 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<SysRoleMenu> queryByPage(SysRoleMenu sysRoleMenu, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param sysRoleMenu 实例对象
     * @return 实例对象
     */
    SysRoleMenu insert(SysRoleMenu sysRoleMenu);

    /**
     * 修改数据
     *
     * @param sysRoleMenu 实例对象
     * @return 实例对象
     */
    SysRoleMenu update(SysRoleMenu sysRoleMenu);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    boolean deleteById(Long roleId);

    /**
     * 批量删除角色权限
     * @param idList
     * @return
     */
    int deleteByIdList(List<Integer> idList);
}
