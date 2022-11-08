package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysRoleDao;
import com.zy_admin.sys.dao.SysRoleMenuDao;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.entity.SysRoleMenu;
import com.zy_admin.sys.service.SysRoleService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {


    @Resource
    private SysRoleDao sysRoleDao;
    @Resource
    private SysRoleMenuDao sysRoleMenuDao;

    /**
     *
     * @param roleAndRoleMenu
     * @return
     */
    @Override
    @Transactional
    public Result insert(RoleAndRoleMenu roleAndRoleMenu) {
        try {
            long id = this.baseMapper.insert(roleAndRoleMenu.getSysRole()); //获取自增主键
            sysRoleMenuDao.insertBatch(id,roleAndRoleMenu.getMenuIdList());
            return new Result(null,ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        }

    }

}

