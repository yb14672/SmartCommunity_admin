package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.UserDto;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.common.core.Result.Result;
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
     * 注销
     * @param request 前端请求
     * @return 用户结果集
     */
    Result logout(HttpServletRequest request);
    /**
     * 删除用户id
     * @param idList 用户id数组列表
     * @return 删除的用户结果集
     */
    Result deleteUserById(List<Integer> idList);
    /**
     * 查询所有用户
     * @param pageable  分页对象
     * @param sysUser   用户对象
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 查询到的用户结果集
     */
    Result selectUsers(Pageable pageable, SysUser sysUser, String startTime, String endTime);
    /**
     * 管理员修改用户
     * @param userDto 用户dto
     * @return 修改后的用户结果集
     */
    Result adminUpdateUser(UserDto userDto);
    /**
     * 批量导入用户信息
     * @param file 文件
     * @return 导出的用户结果集
     */
    Result importData(MultipartFile file);
    /**
     * 导出所有用户
     * @return 查询用户集合
     */
    List<SysUser> getUserLists();
    /**
     * 选中用户id导出
     * @param userIds 用户id数组列表
     * @return 查询用户集合
     */
    List<SysUser> queryUserById(ArrayList<Integer> userIds);
    /**
     * 通过id查询头像
     * @param userId 用户id对象
     * @return 查询用户头像结果集
     */
    Result getAvatarById(String userId);
    /**
     * 登录
     * @param sysUser 用户对象
     * @return 查询用户结果集
     */
    Result login(SysUser sysUser);
    /**
     * 按账号名称查询用户
     * @param userName 用户名
     * @return 查询用户结果集
     */
    Result queryByName(String userName);
    /**
     * 根据ID查询用户
     * @param id 用户id对象
     * @return 查询用户结果集
     */
    Result queryById(String id);
    /**
     * 根据用户ID获取用户所有信息
     * @param userId 用户id对象
     * @return 查询用户结果集
     */
    Result personal(String userId);
    /**
     * 修改用户信息
     * @param user 用户对象
     * @return 修改用户结果集
     */
    Result updateUser(SysUser user);
    /**
     * 重置密码
     * @param user 用户对象
     * @return 修改用户密码结果集
     */
    Result resetPwd(SysUser user);
    /**
     * 查询所有用户
     * @param page    分页对象
     * @param sysUser 用户对象
     * @return 查询用户结果集
     */
    Result selectAll(Page page, SysUser sysUser);
    /**
     * 根据用户ID获取其信息和对应的角色
     * @param userId 用户id对象
     * @return 查询用户结果集
     */
    Result authRole(Long userId);
    /**
     * 根据用户ID修改其对应的角色列表
     * @param userId 用户id
     * @param roleId 角色id
     * @return 修改用户结果集
     * @throws Exception 异常
     */
    Result insertAuthRole(Integer userId, String roleId) throws Exception;
    /**
     * 新增用户
     * @param sysUserDto 系统用户dto
     * @return 新增用户结果集
     */
    Result insertUser(UserDto sysUserDto);
    /**
     * 昵称查重
     * @param type       判断是新增0或者修改1
     * @param sysUserDto 用户dto对象
     * @return 成功或失败的结果集
     */
    Boolean checkNiceName(int type, UserDto sysUserDto);
    /**
     * 检查电话
     * @param type       判断是新增0或者修改1
     * @param sysUserDto 用户dto对象
     * @return 成功或失败的结果集
     */
    Boolean checkPhone(int type, UserDto sysUserDto);
    /**
     * 检查电子邮件
     * @param type       判断是新增0或者修改1
     * @param sysUserDto 用户dto对象
     * @return 成功或失败的结果集
     */
    Boolean checkEmail(int type, UserDto sysUserDto);
    /**
     * 检查用户名
     * @param type    判断是新增0或者修改1
     * @param userDto 用户dto对象
     * @return 成功或失败的结果集
     */
    Boolean checkUserName(int type, UserDto userDto);
    /**
     * 重置密码
     * @param sysUser 用户对象
     * @return {@code Result}
     */
    Result resetPassword(SysUser sysUser);

    SysUser getByUsername(String username);

    String getUserAuthorityInfo(Long userId);
}

