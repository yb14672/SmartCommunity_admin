package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysRoleMenuDao;
import com.zy_admin.sys.entity.SysRoleMenu;
import com.zy_admin.sys.service.SysRoleMenuService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public Result getMenuIdsByRoleId(String id) {
        Result result = new Result();
        try{
            List<Integer> menuIds = this.baseMapper.getMenuIdsByRoleId(id);
            result.setData(menuIds);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        }catch (Exception e){
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        return result;
        }
    }

    @Override
    public int deleteByIdList(List<Integer> idList) {
        return this.baseMapper.deleteByIdList(idList);
    }
}

