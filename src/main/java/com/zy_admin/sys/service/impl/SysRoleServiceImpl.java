package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysRoleDao;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.service.SysRoleService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> queryRoleById(ArrayList<Integer> roleIds) {
        if (roleIds != null) {
            roleIds = roleIds.size() == 0 ? null : roleIds;
        }
        return baseMapper.queryRoleById(roleIds);
    }

    @Override
    public List<SysRole> getRoleLists() {
        return baseMapper.getRoleLists();
    }

    @Override
    public Result deleteByIdList(List<Integer> idList) {
        Result result = new Result();
        try {
            //批量删除角色表
            int i=this.baseMapper.deleteByIdList(idList);
            result.setMeta(ResultTool.fail());
            if(i>=1){
                result.setData("删除成功，影响的行数："+i);
                result.setMeta(ResultTool.success(ResultTool.success(ResultCode.SUCCESS)));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

