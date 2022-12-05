package com.zy_admin.security;
import com.zy_admin.UserException.UserDeletedException;
import com.zy_admin.UserException.UserLockedException;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 我用户详细信息服务impl
 *
 * @author 张友炜
 * @date 2022/12/04
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException , LockedException {
        SysUser sysUser = sysUserService.getByUsername(userName);
        if (sysUser==null){
            throw new UsernameNotFoundException(ResultCode.USER_WRONG_ACCOUNT_OR_PASSWORD+"");
        }else if ("1".equals(sysUser.getStatus())){
            throw new UserLockedException(ResultCode.USER_ACCOUNT_LOCKED+"");
        }else if ("2".equals(sysUser.getDelFlag()))
        {
            throw new UserDeletedException(ResultCode.USER_ACCOUNT_EXPIRED+"");
        }
        return new User(sysUser.getUserName(),sysUser.getPassword(),getUserAuthority(sysUser.getUserId()));
    }

    public List<GrantedAuthority> getUserAuthority(Long userId) {
        String authority = sysUserService.getUserAuthorityInfo(userId);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
