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
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
    }
}

