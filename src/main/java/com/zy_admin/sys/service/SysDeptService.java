package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.common.core.Result.Result;

import java.util.List;

/**
 * 部门表(SysDept)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
public interface SysDeptService extends IService<SysDept> {
    /**
     * 检查修改后的菜单是否和子集一致
     * @param dept 修改后的部门数据
     * @return true--父类是子集
     */
    Boolean checkNewParentId(SysDept dept);
    /**
     * 通过条件搜索部门
     * @param sysDept 部门对象
     * @return 查询到的部门结果集
     */
    Result getDeptList(SysDept sysDept);
    /**
     * 新增部门
     * @param sysDept 新增的部门对象
     * @return 新增部门结果集
     */
    Result insertDept(SysDept sysDept);
    /**
     * 部门名验重方法
     * @param type 判断是新增0或者修改1
     * @param sysDept 部门对象
     * @return 成功或失败的结果集
     */
    Boolean checkDeptNameUnique(int type, SysDept sysDept);
    /**
     * 修改部门
     * @param sysDept 存放部门对象
     * @return 修改部门的结果集
     */
    Result updateDept(SysDept sysDept);
    /**
     * 删除部门
     * @param idList 被删除部门的主键数组
     * @return 删除的部门结果集
     */
    Result deleteDept(List<Integer> idList);
    /**
     * 根据ID获取部门
     * @param deptId 部门主键
     * @return 部门对象
     */
    SysDept getDeptById(Long deptId);
}

