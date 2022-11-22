package com.zy_admin.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.dto.DeptTreeDto;
import com.zy_admin.sys.entity.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门表(SysDept)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@Repository
public interface SysDeptDao extends BaseMapper<SysDept> {
    /**
     * 根据id查数据
     * @param deptId 部门id
     * @return 部门对象
     */
    SysDept getDeptByDeptId(String deptId);
    /**
     * 根据条件查部门
     * @param sysDept 系统部门对象
     * @return 部门树集合
     */
    List<DeptTreeDto> getDeptList(SysDept sysDept);
    /**
     * 添加部门
     * @param sysDept 系统部门
     * @return 部门条数
     */
    Integer insertDept(SysDept sysDept);
    /**
     * 部门名称验重
     * @param sysDept 系统部门对象
     * @return 部门对象
     */
    SysDept checkDeptNameUnique(SysDept sysDept);
    /**
     * 修改部门
     * sysDept 系统部门对象
     * @return 修改部门的条数
     */
    Integer updateDept(SysDept sysDept);
    /**
     * 改变部门状态
     * @param status 部门状态
     * @param menuId 菜单id
     * @return 修改部门状态条数
     */
    int changeStatus(@Param("status") String status, @Param("menuId") String menuId);
    /**
     * 通过id列表删除
     * @param idList 部门id列表
     * @return 删除部门条数
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);
    /**
     * 判断下面有没有子集
     * @param DeptId 部门id
     * @return 子级数量
     */
    Integer hasChildDept(long DeptId);
    /**
     * 判断下面有没有用户
     * @param idList 部门id集合
     * @return 用户个数
     */
    Integer hasUserDept(@Param("idList") List<Integer> idList);
    /**
     * 根据ID获取部门
     * @param deptId 部门id
     * @return 部门对象
     */
    SysDept getDeptById(Long deptId);
    /**
     * 更新子级停用
     * @param status    部门状态
     * @param ancestors 祖级关系
     * @return 更新条数
     */
    int updateDeptSon(@Param("status") String status, @Param("ancestors") String ancestors);
    /**
     * 检查修改后的菜单是否和子集一致
     * @param deptId 部门Id
     * @return 该部门的子集id列表
     */
    List<Long> getChildrenById(String deptId);
}

