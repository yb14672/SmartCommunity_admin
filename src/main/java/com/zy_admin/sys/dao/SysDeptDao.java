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
     * @param deptId
     * @return
     */
    SysDept getDeptByDeptId(String deptId);
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
     * 更新子级停用
     *
     * @param status
     * @param ancestors
     * @return
     */
    int updateDeptSon(@Param("status") String status,@Param("ancestors") String ancestors);

    /**
     * 检查修改后的菜单是否和子集一致
     * @param deptId 部门Id
     * @return 该部门的子集id列表
     */
    List<Long> getChildrenById(String deptId);
}

