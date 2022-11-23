package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.SysUserDeptDto;
import com.zy_admin.sys.dto.SysUserDto;
import com.zy_admin.sys.dto.UserDto;
import com.zy_admin.sys.dto.UserRoleDto;
import com.zy_admin.sys.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserDao extends BaseMapper<SysUser> {
    /**
     * 根据ID获取用户信息--用户管理修改界面
     * @param id 用户id
     * @return 用户结果集
     */
    UserDto getUserInfo(String id);
    /**
     * 下载模板
     * @return 空的模板用户对象
     */
    SysUser uploadUserTemplate();
    /**
     * 删除用户id
     *
     * @param idList 用户id数组列表
     * @return 删除的用户结果集
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);
    /**
     * 查询所有用户
     *
     * @param pageable  分页对象
     * @param sysUser   用户对象
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 查询到的用户结果集
     */
    List<SysUserDeptDto> selectUsers(@Param("sysUser") SysUser sysUser, @Param("pageable") Pageable pageable, @Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 统计用户总量
     * @param sysUser 用户对象
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计的用户结果集
     */
    Long count(@Param("sysUser") SysUser sysUser, @Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 根据id获取用户信息
     * @param userId 用户id
     * @return 用户信息结果集
     */
    @Select("select * from sys_user where user_id = #{userId}")
    SysUser getUserById(String userId);
    /**
     * 选中用户id导出
     * @param userIds 用户id数组列表
     * @return 查询用户集合
     */
    List<SysUser> queryUserById(@Param("list") ArrayList<Integer> userIds);
    /**
     * 导出所有用户
     * @return 查询用户集合
     */
    List<SysUser> getUserLists();
    /**
     * 通过id获取Avatar
     * @param userId 用户id
     * @return Avatar字符串
     */
    String getAvatarById(String userId);
    /**
     * 按名称查询
     * @param userName 用户名
     * @return 查询的用户对象
     */
    SysUser queryByName(String userName);
    /**
     * 根据ID查询用户及其部门/角色/岗位
     * @param id 用户id
     * @return 查询的用户对象
     */
    SysUser queryById(String id);
    /**
     * 根据用户ID获取用户所有信息
     * @param userId 用户id
     * @return 查询用户dto对象
     */
    SysUserDto personal(String userId);
    /**
     * 修改用户信息
     * @param user 用户对象
     * @return 修改用户条数
     */
    int updateUser(SysUser user);
    /**
     * 登录
     * @param sysUser 用户对象
     * @return 查询用户对象
     */
    @Select("select * from sys_user where user_name=#{userName} and password=#{password}")
    SysUser login(SysUser sysUser);
    /**
     * 新增用户
     * @param sysUserDto 用户dto对象
     * @return 新增用户条数
     */
    int insertUser(UserDto sysUserDto);
    /**
     * 按账号名称查询用户
     * @param nickName 用户名
     * @return 查询的用户对象
     */
    @Select("select * from sys_user where nick_name = #{nickName} and del_flag != 2")
    SysUser checkNiceName(String nickName);
    /**
     * 检查电话
     * @param phonenumber 用户对象
     * @return 查询的用户对象
     */
    @Select("select * from sys_user where phonenumber = #{phonenumber} and del_flag != 2")
    SysUser checkPhone(String phonenumber);
    /**
     * 检查电子邮件
     * @param email 用户邮箱
     * @return 查询的用户对象
     */
    @Select("select * from sys_user where email = #{email} and del_flag != 2")
    SysUser checkEmail(String email);
    /**
     * 检查用户名是否唯一
     * @param userName 用户名
     * @return 用户对象
     */
    @Select("select * from sys_user where user_name = #{userName} and del_flag != 2")
    SysUser checkUserName(String userName);
    /**
     * 新增用户角色权限
     * @param userId 用户id
     * @param roleId 角色id
     */
    void insertRole(@Param("userId") Long userId, @Param("roleId") Integer roleId);
    /**
     * 新增用户岗位
     * @param userId 用户id
     * @param postId 角色id
     */
    void insertPost(@Param("userId") Long userId, @Param("postId") Integer postId);
    /**
     * 删除用户拥有的角色
     * @param userId  用户id
     */
    @Delete("delete  from sys_user_role where user_id=#{userId}")
    void deleteRole(long userId);
    /**
     * 删除用户具有的岗位
     * @param userId 用户id
     */
    @Delete("delete from sys_user_post where user_id =#{userId}")
    void deletePost(long userId);
    /**
     * 管理更新用户
     * @param userDto 用户dto
     */
    void adminUpdateUser(UserDto userDto);
    /**
     * 根据用户ID获取其信息和对应的角色
     * @param userId
     * @return 用户角色dto对象
     */
    UserRoleDto authRole(Long userId);
    /**
     * 循环插入数据
     * @param userEntityList
     * @return 成功或失败
     */
    boolean saveBatch(@Param("userEntityList") List<SysUser> userEntityList);
}

