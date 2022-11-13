package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.dao.SysUserRoleDao;
import com.zy_admin.sys.dto.SysUserDeptDto;
import com.zy_admin.sys.dto.SysUserDto;
import com.zy_admin.sys.dto.SysUsersDto;
import com.zy_admin.sys.dto.UserRoleDto;
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
     * 根据用户ID修改其对应的角色列表
     *
     * @param userId
     * @param roleIdList
     * @return
     */
    @Override
    @Transactional
    public Result insertAuthRole(Integer userId, List<Long> roleIdList) throws Exception {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //先删除原本用户拥有的所有角色
        int i = this.sysUserRoleDao.deleteByUserId(userId + "");
        if (i == 0) {
            throw new Exception("修改用户角色时出错，请稍后再试");
        }
        //再插入修改后的所有角色
        int i1 = this.sysUserRoleDao.insertBatchByRoleId(userId + "", roleIdList);
        if (i1 < 1) {
            throw new Exception("修改用户角色时出错，请稍后再试");
        }
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }


    /**
     * 根据用户ID获取其信息和对应的角色
     *
     * @param userId
     * @return
     */
    @Override
    public Result authRole(Long userId) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        UserRoleDto userRoleDto = this.baseMapper.authRole(userId);
        if (userRoleDto != null || userRoleDto.getUserId() != null) {
            result.setData(userRoleDto);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

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
        SysUser user = baseMapper.login(sysUser);
        String jwtToken = "";
        if (user != null) {
            jwtToken = JwtUtils.getJwtToken(user.getUserId() + "", user.getNickName());
            redisService.set(jwtToken, sysUser.getUserName());
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
}

