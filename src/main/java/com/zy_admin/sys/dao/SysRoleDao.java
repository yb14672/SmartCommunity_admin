package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     * 得到角色信息
     * 根据ID获取对应的角色和权限
     * @param roleId 角色id
     * @return 角色和对应权限对象
     */
    RoleAndRoleMenu getRoleInfo(String roleId);
    /**
     * 查询总数据量
     * @param sysRole   系统作用
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 总数据条数
     */
    Long count(@Param("sysRole") SysRole sysRole, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 修改用户
     * @param sysRole 系统角色
     * @return 更新条数
     */
    int updateRole(SysRole sysRole);

    /**
     * 分页查询角色
     * @param sysRole   系统角色对象
     * @param pageable  分页对象
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 角色集合
     */
    List<SysRole> selectRoleByLimit(@Param("sysRole") SysRole sysRole, @Param("pageable") Pageable pageable, @Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 勾选用户获取excel
     * @param roleIds 角色id
     * @return {@code List<SysRole>}
     */
    List<SysRole> queryRoleById(@Param("list") ArrayList<Integer> roleIds);
    /**
     * 根据ID集合循环删除
     * @param idList 角色ID列表
     * @return 删除条数
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);
    /**
     * 所有用户获取excel
     * @return 系统角色集合
     */
    List<SysRole> getRoleLists();
    /**
     * 添加角色及其权限
     * @param RoleAndRoleMenu 角色和相关权限
     * @return int 添加条数
     */
    int insert(RoleAndRoleMenu RoleAndRoleMenu);
    /**
     * 修改角色及其权限
     * @param RoleAndRoleMenu 角色和相关权限
     * @return 修改条数
     */
    int updateRoleById(RoleAndRoleMenu RoleAndRoleMenu);
    /**
     * 选择角色名称是否唯一
     * @param roleName 角色名
     * @return 系统角色对象
     */
    @Select("select * from sys_role where role_name = #{roleName} and del_flag != 2")
    SysRole selectRoleName(String roleName);

    /**
     * 检查权限标识是否唯一
     * @param roleKey 关键角色
     * @return 系统角色对象
     */
    @Select("select * from sys_role where role_key=#{roleKey} and del_flag != 2")
    SysRole selectRoleKey(String roleKey);
    /**
     * 得到所有角色的数据
     * @param sysRole 系统角色对象
     * @return 角色对象集合
     */
    @Select("select * from sys_role where role_id!=1 and del_flag != 2")
    List<SysRole> getAllRole(SysRole sysRole);
}

