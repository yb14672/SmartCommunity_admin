package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysRoleDao;
import com.zy_admin.sys.dao.SysRoleMenuDao;
import com.zy_admin.sys.dao.SysUserRoleDao;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.dto.SysRoleDto;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.entity.SysUserRole;
import com.zy_admin.sys.service.SysRoleService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

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
    SysRoleMenuDao sysRoleMenuDao;
    @Resource
    SysUserRoleDao sysUserRoleDao;

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
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //获取所有分配了的用户列表
        List<SysUserRole> list = this.sysUserRoleDao.getListByIds(idList);
        //若等于0则此次删除的角色没有被分配
        if(list.size() == 0){
            int i = this.baseMapper.deleteByIdList(idList);
            if (i >= 1) {
                result.setData("删除成功，影响的行数：" + i);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
        }else{
            result.setMeta(ResultTool.fail(ResultCode.ROLE_HAS_BEEN_ASSIGNED));
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

    /**
     * 新增角色
     *
     * @param roleAndRoleMenu
     * @return
     */

    @Override
    public Result insert(RoleAndRoleMenu roleAndRoleMenu) {
        if (checkRoleNameUnique(0,roleAndRoleMenu)) {
            if (checkRoleKeyUnique(0,roleAndRoleMenu)) {
                try {
                    //插入角色
                    this.baseMapper.insert(roleAndRoleMenu);
                    //判断是否有权限，有则插入
                    if (roleAndRoleMenu.getMenuIds().length != 0) {
                        sysRoleMenuDao.insertBatch(roleAndRoleMenu.getRoleId(), roleAndRoleMenu.getMenuIds());
                    }
                    return new Result(null, ResultTool.fail(ResultCode.SUCCESS));
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
                }
            } else {
                return new Result(null, ResultTool.fail(ResultCode.REPEAT_ROLE_KEY));
            }
        } else {
            return new Result(null, ResultTool.fail(ResultCode.REPEAT_ROLE_NAME));
        }
    }

    @Override
    public Result update(RoleAndRoleMenu roleAndRoleMenu) {
        Result result = new Result();
        if (checkRoleNameUnique(1,roleAndRoleMenu)) {
            if (checkRoleKeyUnique(1,roleAndRoleMenu)) {
                try {
                    //更新角色
                    int i = this.baseMapper.updateRoleById(roleAndRoleMenu);
                    if (i == 1) {
                        sysRoleMenuDao.deleteById(roleAndRoleMenu.getRoleId());
                        //判断是否有权限，有则更改
                        if (roleAndRoleMenu.getMenuIds().length != 0) {
                            sysRoleMenuDao.insertBatch(roleAndRoleMenu.getRoleId(), roleAndRoleMenu.getMenuIds());
                        }
                        result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                        return result;
                    }
                    return new Result(null, ResultTool.fail(ResultCode.SUCCESS));
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
                }
            } else {
                return new Result(null, ResultTool.fail(ResultCode.REPEAT_ROLE_KEY));
            }
        } else {
            return new Result(null, ResultTool.fail(ResultCode.REPEAT_ROLE_NAME));
        }
    }

    @Override
    public Boolean checkRoleNameUnique(int type, RoleAndRoleMenu roleAndRoleMenu){
        SysRole sysRole = this.baseMapper.selectRoleName(roleAndRoleMenu.getRoleName());
        //添加时--必须为空
        if (type == 0) {
            if (sysRole == null || sysRole.getRoleId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysRole == null || sysRole.getRoleId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysRole.getRoleId().equals(roleAndRoleMenu.getRoleId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkRoleKeyUnique(int type, RoleAndRoleMenu roleAndRoleMenu){
        SysRole sysRole = this.baseMapper.selectRoleKey(roleAndRoleMenu.getRoleKey());
        //添加时--必须为空
        if (type == 0) {
            if (sysRole == null || sysRole.getRoleId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysRole == null || sysRole.getRoleId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysRole.getRoleId().equals(roleAndRoleMenu.getRoleId())) {
                return false;
            }
            return true;
        }
        return false;
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

