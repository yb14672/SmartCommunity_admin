package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.UserDto;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 登出
     * @param request
     * @return
     */
    Result logout(HttpServletRequest request);
    /**
     * 删除用户
     *
     * @param idList
     * @return
     */
    Result deleteUserById(List<Integer> idList);

    /**
     * 查询所有用户
     *
     * @param pageable
     * @param sysUser
     * @param startTime
     * @param endTime
     * @return
     */
    Result selectUsers(Pageable pageable, SysUser sysUser, String startTime, String endTime);

    /**
     * 管理员修改用户
     * @param
     * @return
     */
    Result adminUpdateUser(UserDto userDto);

    /**
     * 下载模板
     * @return
     */
    List<SysUser> uploadUserTemplate();

    /**
     *批量导入用户信息
     * @param file
     */
    Result importData(MultipartFile file);

    /**
     * 导出所有用户
     *
     * @return
     */
    List<SysUser> getUserLists();

    /**
     * 选中导出
     *
     * @param userIds
     * @return
     */
    List<SysUser> queryUserById(ArrayList<Integer> userIds);

    /**
     * 查询头像
     *
     * @param userId
     * @return
     */
    Result getAvatarById(String userId);

    /**
     * 登录
     *
     * @param sysUser
     * @return
     */
    Result login(SysUser sysUser);

    /**
     * 根据账号查询用户
     *
     * @param userName
     * @return
     */
    Result queryByName(String userName);

    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    Result queryById(String id);

    /**
     * 根据用户ID获取用户所有信息
     *
     * @param userId
     * @return
     */
    Result personal(String userId);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    Result updateUser(SysUser user);

    /**
     * 修改密码
     *
     * @param user 用户信息
     * @return 结果
     */
    Result resetPwd(SysUser user);

    /**
     * 查询所有用户
     *
     * @param page
     * @param sysUser
     * @return
     */
    Result selectAll(Page page, SysUser sysUser);

    /**
     * 根据用户ID获取其信息和对应的角色
     *
     * @param userId
     * @return
     */
    Result authRole(Long userId);

    /**
     * 根据用户ID修改其对应的角色列表
     *
     * @param userId
     * @param roleId
     * @return
     */
    Result insertAuthRole(Integer userId, String roleId) throws Exception;

    /**
     * 新增用户
     *
     * @param sysUserDto
     * @return
     */
    Result insertUser(UserDto sysUserDto);

    /**
     * 昵称查重
     *
     * @param type
     * @param sysUserDto
     * @return
     */
    Boolean checkNiceName(int type, UserDto sysUserDto);

    /**
     * 电话查重
     * @param type
     * @param sysUserDto
     * @return
     */
    Boolean checkPhone(int type, UserDto sysUserDto);

    /**
     * 邮箱查重
     * @param type
     * @param sysUserDto
     * @return
     */
    Boolean checkEmail(int type, UserDto sysUserDto);

    /**
     * 用户名查重
     * @param type
     * @param userDto
     * @return
     */
    Boolean checkUserName(int type, UserDto userDto);

    /**
     * 重置密码
     * @param sysUser
     * @return
     */
    Result resetPassword(SysUser sysUser);
}

