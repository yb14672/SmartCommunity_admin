package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.DeptTree;
import com.zy_admin.sys.entity.SysDept;

import java.util.List;

/**
 * 部门表(SysDept)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
public interface SysDeptDao extends BaseMapper<SysDept> {

//    查所有
    List<DeptTree> getDeptTree();

//    删除
//    int deleteByIdList(@Param("idList") List<Integer> idList);

//    判断下面有没有子集
//    Integer hasChildDept(long DeptId);
}

