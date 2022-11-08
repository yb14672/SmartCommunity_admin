package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysRoleDao;
import com.zy_admin.sys.dao.SysRoleMenuDao;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.dto.SysRoleDto;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.service.SysRoleService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRoleExcelDto)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {
    @Resource
    SysRoleDao sysRoleDao;
    @Resource
    SysRoleMenuDao sysRoleMenuDao;


    @Override
    public List<SysRole> queryRoleById(ArrayList<Integer> roleIds) {
        if (roleIds != null) {
            roleIds = roleIds.size() == 0 ? null : roleIds;
        }
        return sysRoleDao.queryRoleById(roleIds);
    }

    @Override
    public List<SysRole> getRoleLists() {
        return sysRoleDao.getRoleLists();
    }

    @Override
    public Result deleteByIdList(List<Integer> idList) {
        Result result = new Result();
        int i = this.baseMapper.deleteByIdList(idList);
        result.setMeta(ResultTool.fail());
        if (i >= 1) {
            result.setData("删除成功，影响的行数：" + i);
            result.setMeta(ResultTool.success(ResultTool.success(ResultCode.SUCCESS)));
        }
        return result;
    }

    @Override
    public Result selectRoleByLimit(SysRole sysRole, Pageable pageable, String startTime, String endTime) {
        Result result = new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        //满足条件的总数据
        long total = this.baseMapper.count(sysRole, startTime, endTime);
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
        List<SysRole> sysRoleList = this.baseMapper.selectRoleByLimit(sysRole, pageable, startTime, endTime);
        SysRoleDto sysRoleDto = new SysRoleDto(sysRoleList, startTime, endTime, pageable);
        result.setData(sysRoleDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insert(RoleAndRoleMenu roleAndRoleMenu) {
        try {
            this.baseMapper.insert(roleAndRoleMenu);
            if (roleAndRoleMenu.getMenuIds().length != 0) {
                sysRoleMenuDao.insertBatch(roleAndRoleMenu.getRoleId(), roleAndRoleMenu.getMenuIds());
            }
            return new Result(null, ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result update(RoleAndRoleMenu roleAndRoleMenu) {
        Result result = new Result();
        int i = this.baseMapper.updateRoleById(roleAndRoleMenu);
        if (i == 1) {
            sysRoleMenuDao.deleteById(roleAndRoleMenu.getRoleId());
            if (roleAndRoleMenu.getMenuIds().length != 0) {
                sysRoleMenuDao.insertBatch(roleAndRoleMenu.getRoleId(), roleAndRoleMenu.getMenuIds());
            }
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        }
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        return result;
    }

    @Override
    public Result changeStatus(SysRole sysRole) {
        Result result = new Result();
        int i = this.baseMapper.updateRole(sysRole);
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        if (i == 1) {
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        }
        return result;
    }
}

