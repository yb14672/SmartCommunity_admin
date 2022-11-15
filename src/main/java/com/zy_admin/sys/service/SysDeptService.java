package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.util.Result;

import java.util.List;

/**
 * 部门表(SysDept)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
public interface SysDeptService extends IService<SysDept> {
    /**
     * 检查两个data是否一致
     *
     * @param updateDept
     * @param originalDept
     * @return
     */
    Boolean checkEquals(SysDept updateDept, SysDept originalDept);
    /**
     * 根据条件查询部门
     * @param sysDept
     * @return
     */
    Result getDeptList(SysDept sysDept);
    /**
     * 添加部门
     * @param sysDept
     * @return
     */
    Result insertDept(SysDept sysDept);
    /**
     * 部门名称验重
     * @param sysDept
     * @return
     */
    Boolean checkDeptNameUnique(int type, SysDept sysDept);

    /**
     * 修改部门
     * @param sysDept
     * @return
     */
    Result updateDept(SysDept sysDept);

    /**
     * 删除部门
      * @param idList
     * @return
     */
    Result deleteDept(List<Integer> idList);
}

