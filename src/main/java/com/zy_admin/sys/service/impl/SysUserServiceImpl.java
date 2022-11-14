package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.dao.SysUserRoleDao;
import com.zy_admin.sys.dto.SysUserDeptDto;
import com.zy_admin.sys.dto.SysUserDto;
import com.zy_admin.sys.dto.SysUsersDto;
import com.zy_admin.sys.dto.UserRoleDto;
import com.zy_admin.sys.dto.UserDto;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.RedisService;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtils;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Resource
    private RedisService redisService;
    /**
     * 删除用户
     * @param idList
     * @return
     */
    @Override
    public Result deleteUserById(List<Integer> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //若等于0则此次删除的用户
        int i = this.baseMapper.deleteByIdList(idList);
        if (i >= 1) {
            result.setData("删除成功，影响的行数：" + i);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }else{
            result.setMeta(ResultTool.fail(ResultCode.ROLE_HAS_BEEN_ASSIGNED));
        }
        return result;
    }
    /**
     * 分页插询
     * @param pageable
     * @param sysUser
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Result selectUsers(Pageable pageable, SysUser sysUser, String startTime, String endTime) {
        Result result = new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        //满足条件的总数据
        long total = this.baseMapper.count(sysUser, startTime, endTime);
        long pages = 0;
        if (total > 0) {
            //计算出总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<SysUserDeptDto> sysUserDeptDtoList = this.baseMapper.selectUsers(sysUser, pageable, startTime, endTime);
        SysUsersDto sysUsersDto = new SysUsersDto(sysUserDeptDtoList, startTime, endTime, pageable);
        result.setData(sysUsersDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 根据用户ID修改其对应的角色列表
     *
     * @param userId
     * @param roleIdList
     * @return
     */
    @Override
    @Transactional
    public Result insertAuthRole(Integer userId, List<Long> roleIdList) throws Exception {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //先删除原本用户拥有的所有角色
        int i = this.sysUserRoleDao.deleteByUserId(userId + "");
        if(i == 0) {
            throw new Exception("修改用户角色时出错，请稍后再试");
        }
        //再插入修改后的所有角色
        int i1 = this.sysUserRoleDao.insertBatchByRoleId(userId + "", roleIdList);
        if(i1<1){
            throw new Exception("修改用户角色时出错，请稍后再试");
        }
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 根据用户ID获取其信息和对应的角色
     * @param userId
     * @return
     */
    @Override
    public Result authRole(Long userId) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        UserRoleDto userRoleDto=this.baseMapper.authRole(userId);
        if(userRoleDto!= null||userRoleDto.getUserId()!=null){
            result.setData(userRoleDto);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }


    @Override
    public Result getAvatarById(String userId) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断传入的id是否为空
            if (userId == null || userId.isEmpty()) {
                result.setMeta(ResultTool.fail(ResultCode.USER_NOT_LOGIN));
                return result;
            }
            String avatar = this.baseMapper.getAvatarById(userId);
            if (avatar == null || avatar.isEmpty()) {
                result.setMeta(ResultTool.fail(ResultCode.USER_AVATAR_NULL));
                return result;
            }
            result.setData(avatar);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } catch (NullPointerException e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_IS_BLANK));
        } catch (ClassCastException e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_TYPE_ERROR));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        } finally {
            return result;
        }
    }

    @Override
    public Result login(SysUser sysUser) {
        SysUser user = baseMapper.login(sysUser);
        System.err.println(user);
        String jwtToken = "";
        if (user != null) {
            jwtToken = JwtUtils.getJwtToken(user.getUserId() + "", user.getNickName());
            redisService.set(jwtToken,sysUser.getUserName());
            if ("1".equals(user.getStatus())) {
                return new Result(jwtToken, ResultTool.fail(ResultCode.USER_ACCOUNT_LOCKED));
            }
            return new Result(jwtToken, ResultTool.success(ResultCode.SUCCESS));
        }
        return new Result(jwtToken, ResultTool.fail(ResultCode.USER_WRONG_ACCOUNT_OR_PASSWORD));
    }

    @Override
    public Result queryByName(String userName) {
        SysUser user = this.baseMapper.queryByName(userName);
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是否有查到对应用户
        if (user == null) {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        } else {
            result.setData(user);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result queryById(String id) {
        SysUser user = this.baseMapper.queryById(id);
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是否有查到对应用户
        if (user == null) {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        } else {
            result.setData(user);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result personal(String userId) {
        SysUserDto personal = this.baseMapper.personal(userId);
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是否有查到对应用户
        if (personal == null) {
            result.setJsonResult(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        } else {
            result.setData(personal);
            result.setJsonResult(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result updateUser(SysUser user) {
        int i = baseMapper.updateUser(user);
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        if (i == 1) {
            result.setData("用户ID为" + user.getUserId() + "的信息修改成功");
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } else {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        }
        return result;
    }

    @Override
    public Result resetPwd(SysUser user) {
        //加密密码--未来写
        SysUser sysUser = baseMapper.queryById(user.getUserId() + "");
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断旧密码是否一致
        if (!sysUser.getPassword().equals(user.getPassword())) {
            int i = baseMapper.updateUser(user);
            if (i == 1) {
                result.setData("用户ID为" + user.getUserId() + "的信息修改成功");
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_SAME_PASSWORD));
        }
        return result;
    }

    @Override
    public Result selectAll(Page page, SysUser sysUser) {
        return null;
    }



    /**
     * 新增用户
     *
     * @param sysUserDto
     * @return
     */
    @Override
    public Result insertUser(UserDto sysUserDto) {
        Result result = new Result();
        if (checkUserName(0,sysUserDto)) {
            if (checkNiceName(0, sysUserDto)) {
                if (checkPhone(0, sysUserDto)) {
                    if (checkEmail(0, sysUserDto)) {
                        if (sysUserDto.getPostIds().length != 0) {
                            this.baseMapper.insertPost(sysUserDto.getUserId(), sysUserDto.getPostIds());
                        }
                        if (sysUserDto.getRoleIds().length != 0) {
                            this.baseMapper.insertRole(sysUserDto.getUserId(), sysUserDto.getRoleIds());
                        }
                        this.baseMapper.insertUser(sysUserDto);
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    } else {
                        result.setMeta(ResultTool.fail(ResultCode.REPEAT_EMAIL));
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONENUMBER));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_NICK_NAME));
            }
        }else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_USER_NAME));
        }
        return result;
    }

    /**
     * 管理员修改用户
     *
     * @param userDto
     * @return
     */
    @Override
    public Result adminUpdateUser(UserDto userDto) {
        Result result = new Result();
        SysUser user = this.baseMapper.getUserById(userDto.getUserId() + "");
        if (user.getNickName().equals(userDto.getNickName()) && user.getPhonenumber().equals(userDto.getPhonenumber()) && user.getEmail().equals(userDto.getEmail()) && user.getDeptId().equals(userDto.getDeptId()) && user.getSex().equals(userDto.getSex()) && user.getStatus().equals(userDto.getStatus())) {
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            return result;
        }

        if (checkNiceName(1, userDto)) {
            if (checkPhone(1, userDto)) {
                if (checkEmail(1, userDto)) {
                    //判断postId有没有值
                    if (userDto.getPostIds() != null) {
                        this.baseMapper.deleteRole(userDto.getUserId());
                        this.baseMapper.insertRole(userDto.getUserId(), userDto.getRoleIds());
                    }
                    //判断roleId有没有值
                    if (userDto.getRoleIds() != null) {
                        this.baseMapper.deletePost(userDto.getUserId());
                        this.baseMapper.insertPost(userDto.getUserId(), userDto.getPostIds());
                    }
                    this.baseMapper.adminUpdateUser(userDto);


                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));


                } else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_EMAIL));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONENUMBER));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_NICK_NAME));
        }
        return result;
    }

    @Override
    public Boolean checkNiceName(int type, UserDto sysUserDto) {
        SysUser sysUser = this.baseMapper.checkNiceName(sysUserDto.getNickName());
        if (type == 0) {
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysUser.getUserId().equals(sysUserDto.getUserId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkPhone(int type, UserDto sysUserDto) {
        SysUser sysUser = this.baseMapper.checkPhone(sysUserDto.getPhonenumber());
        if (type == 0) {
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysUser.getUserId().equals(sysUserDto.getUserId())) {
                return false;
            }
            return true;
        }
        return false;
    }


    @Override
    public Boolean checkEmail(int type, UserDto sysUserDto) {
        SysUser sysUser = this.baseMapper.checkEmail(sysUserDto.getEmail());
        if (type == 0) {
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysUser.getUserId().equals(sysUserDto.getUserId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkUserName(int type, UserDto userDto) {
        SysUser sysUser = this.baseMapper.checkUserName(userDto.getUserName());
        if (type == 0) {
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysUser.getUserId().equals(userDto.getUserId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 重置密码
     *
     * @param sysUser
     * @return
     */
    @Override
    public Result resetPassword(SysUser sysUser) {
        Result result = new Result();
        SysUser user = this.baseMapper.getUserById(sysUser.getUserId() + "");

        if (!sysUser.getPassword().equals(user.getPassword())) {
            this.baseMapper.updateUser(sysUser);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } else {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_SAME_PASSWORD));
        }
        return result;
    }
}

