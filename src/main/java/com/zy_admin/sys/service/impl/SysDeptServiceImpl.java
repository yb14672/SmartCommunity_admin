package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysDeptDao;
import com.zy_admin.sys.entity.DeptTree;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.service.SysDeptService;
import com.zy_admin.util.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门表(SysDept)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDept> implements SysDeptService {

    /**
     * 查询树结构
     * @return
     */
    @Override
    public Result getDeptTree() {
        Result result = new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            List<DeptTree> deptTree = this.baseMapper.getDeptTree();
            Tree2 tree = new Tree2(deptTree);
            result.setData(tree.buildTree());
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
    }


//    //    删除多个
//    @Override
//    public Result deleteByIdList(List<Integer> idList) {
//        Result result = new Result();
////        判断是删除单个还是多个
//        if (idList.size()==1){
////            判断有没有子集
//            Integer hasChildDept = this.baseMapper.hasChildDept(idList.get(0));
////            小于1说明没有子集，就可以删
//            if (hasChildDept<1){
//                int i = this.baseMapper.deleteByIdList(idList);
//                System.out.println(i);
//                result.setData("删除成功，影响的行数：" + i);
//                result.setMeta(ResultTool.success(ResultTool.success(ResultCode.SUCCESS)));
//            }else {
//                result.setMeta(ResultTool.fail(ResultCode.DEPT_HAVE_CHILDREN));
//            }
////            多个就是批量删除
//        }else {
//            int i = this.baseMapper.deleteByIdList(idList);
//            if (i >= 1) {
//                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
//            } else {
//                result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
//            }
//        }
//        return result;
//    }
}

