package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.util.Result;

import java.util.List;

import java.util.ArrayList;

/**
 * 角色信息表(SysRoleExcelDto)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysRoleService extends IService<SysRole> {
    List<SysRole> queryRoleById(ArrayList<Integer> roleIds);

    List<SysRole> getRoleLists();
    /**
     * 根据ID集合批量删除
     * @param idList 角色ID列表
     * @return
     */
    Result deleteByIdList(List<Integer> idList);

    Result selectRoleByLimit(SysRole sysRole, Pageable pageable, String startTime, String endTime);

    Result insert(RoleAndRoleMenu roleAndRoleMenu);

    Result update(RoleAndRoleMenu roleAndRoleMenu);

    Result changeStatus(SysRole sysRole);
}

