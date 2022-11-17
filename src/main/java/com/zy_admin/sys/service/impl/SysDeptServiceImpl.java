package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysDeptDao;
import com.zy_admin.sys.dto.DeptTree;
import com.zy_admin.sys.dto.DeptTreeDto;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.service.SysDeptService;
import com.zy_admin.util.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
     * 根据条件查询部门数据
     *
     * @param sysDept
     * @return
     */
    @Override
    public Result getDeptList(SysDept sysDept) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        try {

            List<DeptTreeDto> deptList = this.baseMapper.getDeptList(sysDept);
            DeptTree tree = new DeptTree(deptList);
            result.setData(tree.buildTree());
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
    }

    /**
     * 新增部门
     *
     * @param sysDept
     * @return
     */
    @Override
    public Result insertDept(SysDept sysDept) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断部门名是否为空
            if (sysDept.getDeptName() == null || "".equals(sysDept.getDeptName())) {
                result.setData(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                return result;
            } else {
                //判断部门名称是否唯一，不唯一时提示并返回
                if (!checkDeptNameUnique(0, sysDept)) {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_DEPTNAME));
                    return result;
                }
            }
            String ancestors = ancestors(sysDept);
            sysDept.setAncestors(ancestors);
            Integer i = baseMapper.insertDept(sysDept);
            if (sysDept.getDeptId() != null) {
                result.setData("新增成功，影响的行数："+ i);
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
    /**
     * 修改部门
     *
     * @param sysDept
     * @return
     */
    @Override
    public Result updateDept(SysDept sysDept) {
        Result result = new Result();
        try {
            //判断是否没有修改就提交
            SysDept DeptById =this.baseMapper.getDeptByDeptId(sysDept.getDeptId()+"");
            if (!checkEquals(sysDept, DeptById)) {
                //判断菜单的父类是否自己
                if (!sysDept.getParentId().equals(sysDept.getDeptId())) {
                    if(!checkNewParentId(sysDept)){
                        //因为菜单名为必填字段，所以判断是否为空
                        if (sysDept.getDeptName() == null || "".equals(sysDept.getDeptName())) {
                            result.setMeta(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                            return result;
                        } else {
                            //判断菜单名称是否唯一
                            if (checkDeptNameUnique(1, sysDept)) {
                            } else {
                                result.setMeta(ResultTool.fail(ResultCode.REPEAT_DEPTNAME));
                                return result;
                            }
                        }
                    }else{
                        result.setMeta(ResultTool.fail(ResultCode.PARENT_CANNOT_BE_A_SUBSET));
                        return result;
                    }
                    String ancestors = ancestors(sysDept);
                    sysDept.setAncestors(ancestors);
                    sysDept.setUpdateTime(LocalDateTime.now().toString());
                    //判断状态是否修改
                    if(!sysDept.getStatus().equals(DeptById.getStatus())){
                        //如果已经修改过，则判断父类是否停用
                        SysDept parentDept =this.baseMapper.getDeptByDeptId(sysDept.getParentId()+"");
                        if("1".equals(parentDept.getStatus())){
                            result.setMeta(ResultTool.fail(ResultCode.PARENT_CLASS_DEACTIVATE));
                            return result;
                        }
                    }
                    int i = this.baseMapper.updateDept(sysDept);
                    if (i == 1) {
                        ancestors = ancestors+","+sysDept.getDeptId();
                        String status =sysDept.getStatus();

                        int m = this.baseMapper.updateDeptSon(status,ancestors);
                        if (m >= 1) {
                            result.setData("修改成功，影响的行数："+ m);
                            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                        }
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.PARENT_CLASS_CANNOT_BE_ITSELF));
                }}else {
                result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            }
        } catch(NullPointerException e){
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_IS_BLANK));
        } catch(ClassCastException e){
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_TYPE_ERROR));
        } catch(Exception e){
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        } finally{
            return result;
        }
    }

    /**
     * 部门名验重方法
     *
     * @param type
     * @param sysDept
     * @return
     */
    @Override
    public Boolean checkDeptNameUnique(int type, SysDept sysDept) {
        SysDept dept = this.baseMapper.checkDeptNameUnique(sysDept);
        //添加时--必须为空
        if (type == 0) {
            if (dept == null || dept.getDeptId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (dept == null || dept.getDeptId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysDept.getDeptId().equals(dept.getDeptId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 检查修改后的菜单是否和子集一致
     *
     * @param dept 修改后的部门数据
     * @return true--父类是子集
     */
    @Override
    public Boolean checkNewParentId(SysDept dept) {
        //获取其子集的菜单ID
        List<Long> childrenById = this.baseMapper.getChildrenById(dept.getDeptId()+"");
        //获取到要修改的父类ID
        long newParentId=dept.getParentId();
        //循环遍历，检查修改后的父类是否为子集
        for (int i = 0; i < childrenById.size(); i++) {
            //获取到子集部门id，用于比较是否相等
            long childId = childrenById.get(i);
            if(newParentId==childId) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证没有修改
     * @param updateDept
     * @param originalDept
     * @return
     */
    @Override
    public Boolean checkEquals (SysDept updateDept, SysDept originalDept){
        if (updateDept.getParentId().equals(originalDept.getParentId())) {
            if (updateDept.getDeptName().equals(originalDept.getDeptName())) {
                if (updateDept.getOrderNum().equals(originalDept.getOrderNum())) {
                    if (updateDept.getLeader().equals(originalDept.getLeader())) {
                        if (updateDept.getPhone().equals(originalDept.getPhone())) {
                            if (updateDept.getEmail().equals(originalDept.getEmail())) {
                                if (updateDept.getStatus().equals(originalDept.getStatus())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public SysDept getDeptById(Long deptId) {
        return this.baseMapper.getDeptById(deptId);
    }
    /**
     * 删除部门
     *
     * @param idList
     * @return
     */
    @Override
    public Result deleteDept(List<Integer> idList) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断有没有子集
        Integer hasChildDept = this.baseMapper.hasChildDept(idList.get(0));
        //小于1说明没有子集，就可以删
        if (hasChildDept < 1) {
            //判断有没有用户
            Integer hasUserDept = this.baseMapper.hasUserDept(idList);
            //小于1说明没有用户，就可以删
            if (hasUserDept < 1) {
                int i = this.baseMapper.deleteByIdList(idList);
                result.setData("删除成功，影响的行数：" + i);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.DEPT_HAVE_USER));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.DEPT_HAVE_CHILDREN));
        }
        return result;
    }

    /**
     * 添加ancestors 属性
     *
     * @param sysdept 部门信息
     * @return
     */
    private String ancestors(SysDept sysdept) {
        Long parentId = sysdept.getParentId();
        if (parentId != 0) {
            SysDept parentDept = this.baseMapper.getDeptById(parentId);
            return parentDept.getAncestors() + "," + parentDept.getDeptId();
        }
        return "0";
    }
}

