package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.dto.SysUserDto;
import com.zy_admin.sys.dto.userDto;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtils;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;



    @Override
    public Result getAvatarById(String userId) {
        Result result = new Result();
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
        SysUser user = sysUserDao.login(sysUser);
        String jwtToken = "";
        if (user != null) {
            jwtToken = JwtUtils.getJwtToken(user.getUserId() + "", user.getNickName());
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
        Result result = new Result();
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
        Result result = new Result();
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
        Result result = new Result();
        //判断是否有查到对应用户
        if (personal == null) {
            result.setJsonResult(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        } else {
            result.setData(personal);
            result.setJsonResult(ResultTool.success(ResultCode.SUCCESS));
        }
         return result;
    }

    /**
     * 个人中心修改
     * @param user 用户信息
     * @return
     */
    @Override
    public Result updateUser(SysUser user) {
        int i = baseMapper.updateUser(user);
        Result result = new Result();
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
        Result result = new Result();
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

    /**
     * 新增用户
     *
     * @param sysUserDto
     * @return
     */
    @Override
    public Result insertUser(userDto sysUserDto) {
        Result result = new Result();
        if (checkNiceName(0,sysUserDto))
        {
            if (checkPhone(0,sysUserDto))
            {
                if (checkEmail(0,sysUserDto))
                {
                     this.baseMapper.insertUser(sysUserDto);
                     this.baseMapper.insertRole(sysUserDto.getUserId(),sysUserDto.getRoleIds());
                     this.baseMapper.insertPost(sysUserDto.getUserId(),sysUserDto.getPostIds());

                     result.setMeta(ResultTool.success(ResultCode.SUCCESS));


                }else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_EMAIL));
                }
            }else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONENUMBER));
            }
        }else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_NICK_NAME));
        }
        return result;
    }

    /**
     * 管理员修改用户
     * @param userDto
     * @return
     */
    @Override
    public Result adminUpdateUser(userDto userDto) {
        Result result = new Result();
        if (checkNiceName(1,userDto))
        {
            if (checkPhone(1,userDto))
            {
                if (checkEmail(1,userDto))
                {
                    this.baseMapper.deleteRole(userDto.getUserId());
                    this.baseMapper.deletePost(userDto.getUserId());
                    this.baseMapper.insertUser(userDto);
                    this.baseMapper.insertRole(userDto.getUserId(),userDto.getRoleIds());
                    this.baseMapper.insertPost(userDto.getUserId(),userDto.getPostIds());

                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));


                }else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_EMAIL));
                }
            }else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONENUMBER));
            }
        }else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_NICK_NAME));
        }
        return result;

    }

    @Override
    public Boolean checkNiceName(int type, userDto sysUserDto) {
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
    public Boolean checkPhone(int type, userDto sysUserDto) {
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
    public Boolean checkEmail(int type, userDto sysUserDto) {
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
}




