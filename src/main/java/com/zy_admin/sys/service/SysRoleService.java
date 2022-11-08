package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysRoleService extends IService<SysRole> {
    List<SysRole> queryRoleById(ArrayList<Integer> roleIds);

    List<SysRole> getRoleLists();
}
