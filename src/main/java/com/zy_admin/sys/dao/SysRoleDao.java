package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRoleExcelDto)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
public interface SysRoleDao extends BaseMapper<SysRole> {
    /**
     * 查询总数据量
     * @param sysRole
     * @param startTime
     * @param endTime
     * @return
     */
    Long count(@Param("sysRole") SysRole sysRole,@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 修改用户
     * @param sysRole
     * @return
     */
    int updateRole(SysRole sysRole);

    /**
     * 分页查询
     * @param sysRole
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    List<SysRole> selectRoleByLimit(@Param("sysRole") SysRole sysRole, @Param("pageable") Pageable pageable, @Param("startTime")String startTime, @Param("endTime")String endTime);
    /**
     * 勾选用户获取excel
     * @param roleIds
     * @return
     */
    List<SysRole> queryRoleById(@Param("list") ArrayList<Integer> roleIds);

    /**
     * 根据ID集合循环删除
     * @param idList 角色ID列表
     * @return
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);
    /**
     * 所有用户获取excel
     * @return
     */

    List<SysRole> getRoleLists();

    int insert(RoleAndRoleMenu RoleAndRoleMenu);

    int updateRoleById(RoleAndRoleMenu RoleAndRoleMenu);
}

