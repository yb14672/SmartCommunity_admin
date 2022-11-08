package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysRoleDao extends BaseMapper<SysRole> {
    /**
     * 勾选用户获取excel
     * @param roleIds
     * @return
     */
    List<SysRole> queryRoleById(@Param("list") ArrayList<Integer> roleIds);

    /**
     * 所有用户获取excel
     * @return
     */

    List<SysRole> getRoleLists();
}

