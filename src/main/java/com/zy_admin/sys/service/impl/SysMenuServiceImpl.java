package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysMenuDao;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.dto.SysUserDto;
import com.zy_admin.sys.entity.MenuTree;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.sys.service.SysMenuService;
import com.zy_admin.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Resource
    private SysUserDao sysUserDao;
    @Override
    public Result getAllMenu(String userId) {
        Result result = new Result();
        try {
            SysUserDto userDto = sysUserDao.personal(userId);
            List<MenuTree> menuList = this.baseMapper.getAllMenu(userId,userDto.getSysRole().getRoleId());
            Tree tree = new Tree(menuList);
            result.setData(tree.buildTree());
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
    }

    @Override
    public Result queryAllMenu(SysMenu menu) {
        Result result = new Result();
        try {
            List<MenuTree> menuList = this.baseMapper.queryAllMenu(menu);
            Tree1 tree = new Tree1(menuList);
            result.setData(tree.buildTree());
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
    }

    @Override
    public Result insertMenu(SysMenu menu) {
        Result result = new Result();
        try {
            //因为菜单名为必填字段，所以判断是否为空
            if (menu.getMenuName() == null || "".equals(menu.getMenuName())) {
                result.setMeta(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                return result;
            } else {
                //判断菜单名称是否唯一
                if (checkMenuNameUnique(1, menu)) {
                    //当路由不为空时判断其路由是否重复
                    if (!"".equals(menu.getPath()) || !"#".equals(menu.getPath())) {
                        //不唯一即false，因此不唯一时提示并返回
                        if (!checkPathUnique(1, menu)) {
                            result.setMeta(ResultTool.fail(ResultCode.REPEAT_MENUPATH));
                            return result;
                        }
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_MENUNAME));
                    return result;
                }
            }
            int i = this.baseMapper.insert(menu);
            if (i == 1) {
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
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
    public Result updateMenu(SysMenu menu) {
        Result result = new Result();
        try {
            //因为菜单名为必填字段，所以判断是否为空
            if (menu.getMenuName() == null || "".equals(menu.getMenuName())) {
                result.setMeta(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                return result;
            } else {
                //判断菜单名称是否唯一
                if (checkMenuNameUnique(1, menu)) {
                    //当路由不为空时判断其路由是否重复
                    if (!"".equals(menu.getPath()) || !"#".equals(menu.getPath())) {
                        //不唯一即false，因此不唯一时提示并返回
                        if (!checkPathUnique(1, menu)) {
                            result.setMeta(ResultTool.fail(ResultCode.REPEAT_MENUPATH));
                            return result;
                        }
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_MENUNAME));
                    return result;
                }
            }
            int i = this.baseMapper.updateById(menu);
            if (i == 1) {
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
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
    public Result deleteByIdList(List<Long> idList) {
        Result result = new Result();
        int i = this.baseMapper.deleteBatchIds(idList);
        if (i >= 1) {
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } else {
            result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
        }
        return result;
    }

    @Override
    public Result deteleById(Serializable id) {
        Result result = new Result();
        int i = this.baseMapper.deleteById(id);
        if (i == 1) {
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } else {
            result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
        }
        return result;
    }

    @Override
    public Boolean checkMenuNameUnique(int type, SysMenu menu) {
        SysMenu sysMenu = this.baseMapper.checkMenuNameUnique(menu);
        //添加时--必须为空
        if (type == 0) {
            if (sysMenu == null || sysMenu.getMenuId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysMenu == null || sysMenu.getMenuId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysMenu.getMenuId().equals(menu.getMenuId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkPathUnique(int type, SysMenu menu) {
        SysMenu sysMenu = this.baseMapper.checkPathUnique(menu);
        //添加时--必须为空
        if (type == 0) {
            if (sysMenu == null || sysMenu.getMenuId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重复，即唯一
            if (sysMenu == null || sysMenu.getMenuId() == null) {
                return true;
                //判断ID是否一致，若否，则重复，即不唯一
            } else if (!sysMenu.getMenuId().equals(menu.getMenuId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkComponentUnique(int type, SysMenu menu) {
        SysMenu sysMenu = this.baseMapper.checkComponentUnique(menu);
        //添加时--必须为空
        if (type == 0) {
            if (sysMenu == null || sysMenu.getMenuId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysMenu == null || sysMenu.getMenuId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysMenu.getMenuId().equals(menu.getMenuId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkPermsUnique(int type, SysMenu menu) {
        SysMenu sysMenu = this.baseMapper.checkPermsUnique(menu);
        //添加时--必须为空
        if (type == 0) {
            if (sysMenu == null || sysMenu.getMenuId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重复，即唯一
            if (sysMenu == null || sysMenu.getMenuId() == null) {
                return true;
                //判断ID是否一致，若否，则重复，即不唯一
            } else if (!sysMenu.getMenuId().equals(menu.getMenuId())) {
                return false;
            }
            return true;
        }
        return false;
    }
}

