package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysMenuDao;
import com.zy_admin.sys.entity.MenuTree;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.sys.service.SysMenuService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.Tree;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Override
    public Result getAllMenu() {
        Result result = new Result();
        try {
            List<MenuTree> menuList = this.baseMapper.getAllMenu();
            Tree tree = new Tree(menuList);
            result.setData(tree.buildTree());
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        } finally {
            return result;
        }
    }

    @Override
    public Result getMenu() {
        Result result = new Result();
        try {
            List<MenuTree> menuList = this.baseMapper.getMenu();
            Tree tree = new Tree(menuList);
            result.setData(tree.buildTree());
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        } finally {
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
            } else {
                if(checkMenuNameUnique(0,menu)){
                    //判断是否查询成功
                    SysMenu sysMenu = this.baseMapper.insertMenu(menu);
                    if (sysMenu != null && sysMenu.getMenuId() != null) {
                        result.setData(sysMenu);
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    } else {
                        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
                    }
                }else{
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_MENU));
                }
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
            } else {
                //判断该菜单是否为空
                if (checkMenuNameUnique(1, menu)) {
                    //判断是否查询成功
                    int i = this.baseMapper.updateMenu(menu);
                    if (i == 1) {
                        result.setData("修改成功");
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    } else {
                        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
                    }
                }else{
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_MENU));
                }
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
    public Boolean checkMenuNameUnique(int type, SysMenu menu) {
        SysMenu sysMenu = this.baseMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
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


}

