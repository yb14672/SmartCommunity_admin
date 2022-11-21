package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysMenuDao;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.dto.SysUserDto;
import com.zy_admin.sys.entity.MenuTree;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.sys.service.SysMenuService;
import com.zy_admin.util.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.Tree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    public Boolean checkNewParentId(SysMenu menu) {
        //获取其子集的菜单ID
        List<Long> childrenById = this.baseMapper.getChildrenById(menu.getMenuId()+"");
        //获取到要修改的父类ID
        long newParentId=menu.getParentId();
        //循环遍历，检查修改后的父类是否为子集
        for (int i = 0; i < childrenById.size(); i++) {
            //获取到子集菜单id，用于比较是否相等
            long childId = childrenById.get(i);
            if(newParentId==childId) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Result getAllMenu(String userId) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            SysUserDto userDto = sysUserDao.personal(userId);
            if (!"1".equals(userDto.getSysRole().getStatus())||!"2".equals(userDto.getSysRole().getDelFlag())) {
                List<MenuTree> menuList = this.baseMapper.getAllMenu(userId, userDto.getSysRole().getRoleId());
                Tree tree = new Tree(menuList);
                result.setData(tree.buildTree());
            }
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
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            List<MenuTree> menuList = this.baseMapper.queryAllMenu(menu);
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
    public Result insertMenu(SysMenu menu) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //因为菜单名为必填字段，所以判断是否为空
            if (menu.getMenuName() == null || "".equals(menu.getMenuName())) {
                result.setMeta(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                return result;
            } else {
                //判断菜单名称是否唯一
                if (checkMenuNameUnique(0, menu)) {
                    //当路由不为空时判断其路由是否重复
                    if (!"".equals(menu.getPath()) || !"#".equals(menu.getPath())) {
                        //不唯一即false，因此不唯一时提示并返回
                        if (!checkPathUnique(0, menu)) {
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
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断菜单的父类是否自己
            if(!menu.getParentId().equals(menu.getMenuId())){
                //因为菜单名为必填字段，所以判断是否为空
                if (menu.getMenuName() == null || "".equals(menu.getMenuName())) {
                    result.setMeta(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                    return result;
                } else {
                    //检查修改后的父类是否为子集
                    if(!checkNewParentId(menu)){
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
                    }else{
                        result.setMeta(ResultTool.fail(ResultCode.PARENT_CANNOT_BE_A_SUBSET));
                        return result;
                    }
                }
                menu.setUpdateTime(LocalDateTime.now().toString());
                int i = this.baseMapper.updateById(menu);
                if (i == 1) {
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                }
            }else{
                result.setMeta(ResultTool.fail(ResultCode.PARENT_CLASS_CANNOT_BE_ITSELF));
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
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //若为一个即单删除，否则批量删除
        if (idList.size() == 1) {
            //判断是否有子集，若有则提示
            Integer hasChildren = this.baseMapper.hasChildByMenuId(idList.get(0));
            result.setMeta(ResultTool.fail(ResultCode.MENU_HAVE_CHILDREN));
            //其子集小于1，则没有子集，执行删除
            if (hasChildren < 1) {
                int i = this.baseMapper.deleteBatchIds(idList);
                if (i >= 1) {
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
                }
            }
        } else {
            int i = this.baseMapper.deleteBatchIds(idList);
            if (i >= 1) {
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
            }
        }
        return result;
    }

    @Override
    public Result deteleById(Long id) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是否有子集，若有则提示
        Integer hasChildren = this.baseMapper.hasChildByMenuId(id);
        result.setMeta(ResultTool.fail(ResultCode.MENU_HAVE_CHILDREN));
        //其子集小于1，则没有子集，执行删除
        if (hasChildren < 1) {
            int i = this.baseMapper.deleteById(id);
            if (i == 1) {
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
            }
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

    @Override
    public Result getMenuTrees() {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            List<MenuTree> menuTree = this.baseMapper.getMenuTree();
            Tree tree = new Tree(menuTree);
            List<MenuTree> menuTrees = tree.buildTree();
            result.setData(menuTrees);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            return result;
        }
    }
}

