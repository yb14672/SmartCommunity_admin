package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysRoleDeptDao;
import com.zy_admin.sys.entity.SysRoleDept;
import com.zy_admin.sys.service.SysRoleDeptService;
import org.springframework.stereotype.Service;

/**
 * 角色和部门关联表(SysRoleDept)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:41
 */
@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDept> implements SysRoleDeptService {

}

