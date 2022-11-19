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
     * @param id
     * @return
     */
    UserDto getUserInfo(String id);

    /**
     * 下载模板
     * @return
     */
    SysUser uploadUserTemplate();

    /**
     * 根据id删除用户
     * @param idList
     * @return
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);
    /**
     * 分页查询
     * @param sysUser
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    List<SysUserDeptDto> selectUsers(@Param("sysUser") SysUser sysUser, @Param("pageable") Pageable pageable, @Param("startTime") String startTime, @Param("endTime")String endTime);
    /**
     * 统计用户总量
     * @param sysUser
     * @param startTime
     * @param endTime
     * @return
     */
    Long count(@Param("sysUser")SysUser sysUser, @Param("startTime")String startTime, @Param("endTime")String endTime);
    /**
     * 根据id获取用户信息
     */
    @Select("select * from sys_user where user_id = #{userId}")
    SysUser getUserById(String userId);


    /**
     * 勾选用户获取excel
     * @param userIds
     * @return
     */
    List<SysUser> queryUserById(@Param("list") ArrayList<Integer> userIds);

    /**
     * 所有用户获取excel
     * @return
     */
    List<SysUser> getUserLists();

    /**
     * 查询头像
     * @param userId
     * @return
     */
    String getAvatarById(String userId);

    /**
     * 根据账号查询用户
     * @param userName
     * @return
     */
    SysUser queryByName(String userName);

    /**
     * 根据ID查询用户及其部门/角色/岗位
     * @param id
     * @return
     */
    SysUser queryById(String id);

    /**
     * 根据用户ID获取用户所有信息
     * @param userId
     * @return
     */
    SysUserDto personal(String userId);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);

    /**
     * 登录
     * @param sysUser
     * @return
     */
    @Select("select * from sys_user where user_name=#{userName} and password=#{password}")
    SysUser login(SysUser sysUser);

    /**
     * 新增用户并返回自增id
     * @param sysUserDao
     * @return
     */
    int insertUser(UserDto sysUserDao);

    /**
     * 昵称查重
     * @param nickName
     * @return
     */
    @Select("select * from sys_user where nick_name = #{nickName} and del_flag != 2")
    SysUser checkNiceName(String nickName);

    /**
     * 电话查重
     * @param phonenumber
     * @return
     */
    @Select("select * from sys_user where phonenumber = #{phonenumber} and del_flag != 2")
    SysUser checkPhone(String phonenumber);


    /**
     * 邮箱查重
     * @param email
     * @return
     */
    @Select("select * from sys_user where email = #{email} and del_flag != 2")
    SysUser checkEmail(String email);


    /**
     * 检查用户名是否唯一
     * @param userName
     * @return
     */
    @Select("select * from sys_user where user_name = #{userName} and del_flag != 2")
    SysUser checkUserName(String userName);


    /**
     * 新增用户角色权限
     * @param userId
     * @param roleId
     */
    void insertRole(@Param("userId") Long userId,@Param("roleId") Integer roleId);

    /**
     * 新增用户岗位
     * @param userId
     * @param postId
     */
    void insertPost(@Param("userId") Long userId,@Param("postId") Integer postId);

    /**
     * 删除用户拥有的角色
     * @param userId
     */
    @Delete("delete  from sys_user_role where user_id=#{userId}")
    void deleteRole(long userId);

    /**s
     * 删除用户具有的岗位
     * @param userId
     */
    @Delete("delete from sys_user_post where user_id =#{userId}")
    void deletePost(long userId);


    void adminUpdateUser(UserDto userDto);

    /**
     * 根据用户ID获取其信息和对应的角色
     * @param userId
     * @return
     */
    UserRoleDto authRole(Long userId);

    /**
     * 循环插入数据
     * @param userEntityList
     * @return
     */
    boolean saveBatch(List<SysUser> userEntityList);
}

