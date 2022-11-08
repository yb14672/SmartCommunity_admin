package com.zy_admin.sys.service.impl;

import com.zy_admin.sys.entity.SysRoleMenu;
import com.zy_admin.sys.dao.SysRoleMenuDao;
import com.zy_admin.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-08 14:56:27
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Resource
    private SysRoleMenuDao sysRoleMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public SysRoleMenu queryById(Long roleId) {
        return this.sysRoleMenuDao.queryById(roleId);
    }

    /**
     * 分页查询
     *
     * @param sysRoleMenu 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<SysRoleMenu> queryByPage(SysRoleMenu sysRoleMenu, PageRequest pageRequest) {
        long total = this.sysRoleMenuDao.count(sysRoleMenu);
        return new PageImpl<>(this.sysRoleMenuDao.queryAllByLimit(sysRoleMenu, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param sysRoleMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysRoleMenu insert(SysRoleMenu sysRoleMenu) {
        this.sysRoleMenuDao.insert(sysRoleMenu);
        return sysRoleMenu;
    }

    /**
     * 修改数据
     *
     * @param sysRoleMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysRoleMenu update(SysRoleMenu sysRoleMenu) {
        this.sysRoleMenuDao.update(sysRoleMenu);
        return this.queryById(sysRoleMenu.getRoleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long roleId) {
        return this.sysRoleMenuDao.deleteById(roleId) > 0;
    }

    @Override
    public int deleteByIdList(List<Integer> idList) {
        if()
        return 0;
    }
}
