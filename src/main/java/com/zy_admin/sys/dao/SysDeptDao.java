package com.zy_admin.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.dto.DeptTreeDto;
import com.zy_admin.sys.entity.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门表(SysDept)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
public interface SysDeptDao extends BaseMapper<SysDept> {
    /**
     * 根据条件查部门
     *
     * @param sysDept
     * @return
     */
    List<DeptTreeDto> getDeptList(SysDept sysDept);

    /**
     * 添加部门
     *
     * @param sysDept
     * @return
     */
    Integer insertDept(SysDept sysDept);

    /**
     * 根据id查询出他的子集
     *
     * @param deptId
     * @return
     */
    List<Long> getDeptIdList(Long deptId);

    /**
     * 部门名称验重
     *
     * @param sysDept
     * @return
     */
    SysDept checkDeptNameUnique(SysDept sysDept);

    /**
     * 修改部门
     *
     * @param sysDept
     * @return
     */
    Integer updateDept(SysDept sysDept);

    int changeStatus(@Param("status") String status,@Param("menuId") String menuId);

    /**
     * 删除
     *
     * @param idList
     * @return
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);

    /**
     * 判断下面有没有子集
     *
     * @param DeptId
     * @return
     */
    Integer hasChildDept(long DeptId);

    /**
     * 判断下面有没有用户
     *
     * @param idList
     * @return
     */
    Integer hasUserDept(@Param("idList") List<Integer> idList);

    /**
     * 根据ID获取部门
     * @param deptId
     * @return
     */
    SysDept getDeptById(Long deptId);
    /**
     * 根据id查询出他的子集
     * @param deptId
     * @return
     */
    List<Long> getDeptIdList(Long deptId);
}

