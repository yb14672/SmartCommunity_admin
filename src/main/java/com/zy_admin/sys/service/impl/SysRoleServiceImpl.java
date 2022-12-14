package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.dao.SysRoleDao;
import com.zy_admin.sys.dao.SysRoleMenuDao;
import com.zy_admin.sys.dao.SysUserRoleDao;
import com.zy_admin.sys.dto.RoleAndRoleMenu;
import com.zy_admin.sys.dto.SysRoleDto;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.entity.SysUserRole;
import com.zy_admin.sys.service.SysRoleService;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
 * 角色信息表(SysRoleExcelDto)表服务实现类
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {
    /**
     * 服务角色
     */
    @Resource
    SysRoleMenuDao sysRoleMenuDao;
    @Resource
    SysUserRoleDao sysUserRoleDao;

    /**
     * 获取所有除去管理员以外的角色并分页
     * @param page 分页对象
     * @return 查询角色结果集
     */
    @Override
    public Result getRoleList(Page page) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        //因为超级管理员不允许分配，因此查询条件需要加上id不为1
        queryWrapper.ne(SysRole::getRoleId, 1);
        queryWrapper.ne(SysRole::getDelFlag, 2);
        queryWrapper.orderByAsc(SysRole::getRoleSort);
        Page page1 = this.baseMapper.selectPage(page, queryWrapper);
        if (page1.getSize() > 0) {
            result.setData(page1);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
    /**
     * 获取所有角色数据
     * @param sysRole 角色对象
     * @return 查询的所有角色结果集
     */
    @Override
    public Result getAllRole(SysRole sysRole) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysRole> allRole = this.baseMapper.getAllRole(sysRole);
        result.setData(allRole);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 根据id列表查询
     * @param roleIds 角色主键集合
     * @return 角色集合
     */
    @Override
    public List<SysRole> queryRoleById(ArrayList<Integer> roleIds) {
        if (roleIds != null) {
            roleIds = roleIds.size() == 0 ? null : roleIds;
        }
        return baseMapper.queryRoleById(roleIds);
    }
    /**
     * 获取所有角色id
     * @return 角色集合
     */
    @Override
    public List<SysRole> getRoleLists() {
        return baseMapper.getRoleLists();
    }
    /**
     * 根据ID集合批量删除
     * @param idList 角色ID集合
     * @return 删除的角色结果集
     */
    @Override
    public Result deleteByIdList(List<Integer> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //获取所有分配了的用户列表
        List<SysUserRole> list = this.sysUserRoleDao.getListByIds(idList);
        //若等于0则此次删除的角色没有被分配
        if (list.size() == 0) {
            int i = this.baseMapper.deleteByIdList(idList);
            if (i >= 1) {
                result.setData("删除成功，影响的行数：" + i);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.ROLE_HAS_BEEN_ASSIGNED));
        }
        return result;
    }
    /**
     * 选择角色限制
     * @param sysRole   系统作用
     * @param pageable  分页对象
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 查询角色结果集
     */
    @Override
    public Result selectRoleByLimit(SysRole sysRole, Pageable pageable, String startTime, String endTime) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //满足条件的总数据
        long total = this.baseMapper.count(sysRole, startTime, endTime);
        long pages;
        if (total > 0) {
            //计算出总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(Math.min(pageable.getPageNum(), pages));
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
     * 添加角色及其权限
     * @param roleAndRoleMenu 角色和角色菜单
     * @return 查询角色结果集
     */
    @Override
    public Result insert(RoleAndRoleMenu roleAndRoleMenu) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        if (checkRoleNameUnique(0, roleAndRoleMenu)) {
            if (checkRoleKeyUnique(0, roleAndRoleMenu)) {
                try {
                    //插入角色
                    this.baseMapper.insert(roleAndRoleMenu);
                    //判断是否有权限，有则插入
                    if (roleAndRoleMenu.getMenuIds().length != 0) {
                        sysRoleMenuDao.insertBatch(roleAndRoleMenu.getRoleId(), roleAndRoleMenu.getMenuIds());
                    }
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                } catch (Exception e) {
                    e.printStackTrace();
                    return result;
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_ROLE_KEY));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_ROLE_NAME));
        }
        return result;
    }
    /**
     * 更新角色及其权限
     * @param roleAndRoleMenu 角色和角色菜单
     * @return 更新角色结果集
     */
    @Override
    public Result update(RoleAndRoleMenu roleAndRoleMenu) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        if (checkRoleNameUnique(1, roleAndRoleMenu)) {
            if (checkRoleKeyUnique(1, roleAndRoleMenu)) {
                try {
                    //更新角色
                    int i = this.baseMapper.updateRoleById(roleAndRoleMenu);
                    if (i == 1) {
                        sysRoleMenuDao.deleteById(roleAndRoleMenu.getRoleId());
                        //判断是否有权限，有则更改
                        if (roleAndRoleMenu.getMenuIds().length != 0) {
                            sysRoleMenuDao.insertBatch(roleAndRoleMenu.getRoleId(), roleAndRoleMenu.getMenuIds());
                        }
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_ROLE_KEY));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_ROLE_NAME));
        }
        return result;
    }
    /**
     * 检查角色名唯一
     * @param type 类型用于新增为0或者修改1
     * @param roleAndRoleMenu 角色和角色菜单
     * @return 查询角色结果集
     */
    @Override
    public Boolean checkRoleKeyUnique(int type, RoleAndRoleMenu roleAndRoleMenu) {
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
    /**
     * 修改角色状态
     * @param sysRole 角色对象
     * @return 修改角色结果集
     */
    @Override
    public Result changeStatus(SysRole sysRole) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        int i = this.baseMapper.updateRole(sysRole);
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        if (i == 1) {
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 检查角色名唯一
     *
     * @param type            类型用于新增为0或者修改1
     * @param roleAndRoleMenu 角色和角色菜单
     * @return 角色结果集
     */
    @Override
    public Boolean checkRoleNameUnique(int type, RoleAndRoleMenu roleAndRoleMenu) {
        SysRole sysRole = this.baseMapper.selectRoleName(roleAndRoleMenu.getRoleName());
        //添加时--必须为空
        if (type == 0) {
            return sysRole == null || sysRole.getRoleId() == null;
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
    }
}

