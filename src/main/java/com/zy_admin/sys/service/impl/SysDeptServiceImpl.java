package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyCommunityDao;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.sys.dao.SysDeptDao;
import com.zy_admin.sys.dto.DeptTree;
import com.zy_admin.sys.dto.DeptTreeDto;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDeptService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * 服务对象
     */
    @Resource
    private ZyCommunityDao zyCommunityDao;
    /**
     * 部门名验重方法
     * @param type 判断是新增0或者修改1
     * @param sysDept 部门对象
     * @return 成功或失败的结果集
     */
    @Override
    public Boolean checkDeptNameUnique(int type, SysDept sysDept) {
        SysDept dept = this.baseMapper.checkDeptNameUnique(sysDept);
        //添加时--必须为空
        if (type == 0) {
            return dept == null || dept.getDeptId() == null;
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (dept == null || dept.getDeptId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else {
                return sysDept.getDeptId().equals(dept.getDeptId());
            }
        }
    }
    /**
     * 修改部门
     * @param sysDept 存放部门对象
     * @return 修改部门的结果集
     */
    @Override
    public Result updateDept(SysDept sysDept) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断是否没有修改就提交
            SysDept deptById = this.baseMapper.getDeptByDeptId(sysDept.getDeptId() + "");
            //需要判断的字段名
            String[] fields = new String[]{"parentId", "deptName", "orderNum", "leader", "phone", "status", "email"};
            if (!ObjUtil.checkEquals(sysDept, deptById, fields)) {
                //判断菜单的父类是否自己
                if (!sysDept.getParentId().equals(sysDept.getDeptId())) {
                    if (!checkNewParentId(sysDept)) {
                        //因为菜单名为必填字段，所以判断是否为空
                        if (sysDept.getDeptName() == null || "".equals(sysDept.getDeptName())) {
                            result.setMeta(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                            return result;
                        }// 判断菜单名称是否唯一
                         if (!checkDeptNameUnique(1, sysDept)) {
                                result.setMeta(ResultTool.fail(ResultCode.REPEAT_DEPTNAME));
                                return result;
                            }
                    } else {
                        result.setMeta(ResultTool.fail(ResultCode.PARENT_CANNOT_BE_A_SUBSET));
                        return result;
                    }
                    String ancestors = ancestors(sysDept);
                    sysDept.setAncestors(ancestors);
                    sysDept.setUpdateTime(LocalDateTime.now().toString());
                    //判断状态是否修改
                    if (!sysDept.getStatus().equals(deptById.getStatus())) {
                        //如果已经修改过，则判断父类是否停用
                        SysDept parentDept = this.baseMapper.getDeptByDeptId(sysDept.getParentId() + "");
                        if ("1".equals(parentDept.getStatus())) {
                            result.setMeta(ResultTool.fail(ResultCode.PARENT_CLASS_DEACTIVATE));
                            return result;
                        }
                    }
                    int i = this.baseMapper.updateDept(sysDept);
                    if (i == 1) {
                        ancestors = ancestors + "," + sysDept.getDeptId();
                        String status = sysDept.getStatus();

                        int m = this.baseMapper.updateDeptSon(status, ancestors);
                        if (m >= 1) {
                            result.setData("修改成功，影响的行数：" + m);
                            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                        }
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.PARENT_CLASS_CANNOT_BE_ITSELF));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_IS_BLANK));
            return result;
        } catch (ClassCastException e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_TYPE_ERROR));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }return result;
    }

    /**
     * 根据小区ID查询对应物业的维修人员列表
     *
     * @param communityId 小区ID
     * @return 维修人员列表
     */
    @Override
    public Result getRepairByCommunityId(String communityId) {
        Result result = new Result("获取失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysUser> repairByCommunityId = this.baseMapper.getRepairByCommunityId(communityId);
        if(repairByCommunityId.size() > 0){
            result.setData(repairByCommunityId);
            result.setMeta(ResultTool.success());
        }
        return result;
    }

    /**
     * 检查修改后的菜单是否和子集一致
     * @param dept 修改后的部门数据
     * @return true--父类是子集
     */
    @Override
    public Boolean checkNewParentId(SysDept dept) {
        //获取其子集的菜单ID
        List<Long> childrenById = this.baseMapper.getChildrenById(dept.getDeptId() + "");
        //获取到要修改的父类ID
        long newParentId = dept.getParentId();
        //循环遍历，检查修改后的父类是否为子集
        for (long childId : childrenById) {
            //获取到子集部门id，用于比较是否相等
            if (newParentId == childId) {
                return true;
            }
        }
        return false;
    }
    /**
     * 通过条件搜索部门
     * @param sysDept 部门对象
     * @return 查询到的部门结果集
     */
    @Override
    public Result getDeptList(SysDept sysDept) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
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
     * @param sysDept 新增的部门对象
     * @return 新增部门结果集
     */
    @Override
    public Result insertDept(SysDept sysDept) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
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
                result.setData("新增成功，影响的行数：" + i);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_IS_BLANK));
            return result;
        } catch (ClassCastException e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_TYPE_ERROR));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
        return result;
    }
    /**
     * 删除部门
     * @param idList 删除的部门集合
     * @return 被删除的部门结果集
     */
    @Override
    public Result deleteDept(List<Integer> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断有没有子集
        Integer hasChildDept = this.baseMapper.hasChildDept(idList.get(0));
        //小于1说明没有子集，就可以删
        if (hasChildDept < 1) {
            //判断有没有用户
            Integer hasUserDept = this.baseMapper.hasUserDept(idList);
            //小于1说明没有用户，就可以删
            if (hasUserDept < 1) {
                //判断是否有负责的物业
                List<ZyCommunity> zyCommunities = this.zyCommunityDao.selectCommunityByDeptId(idList);
                //小于1说明没有物业，就可以删
                if (zyCommunities.size() == 0) {
                    int i = this.baseMapper.deleteByIdList(idList);
                    if (i >= 1) {
                        result.setData("删除成功，影响的行数：" + i);
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    }
                } else {
                    result.setMeta(ResultTool.success(ResultCode.COMPANY_OWNS_PROPERTY));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.DEPT_HAVE_USER));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.DEPT_HAVE_CHILDREN));
        }
        return result;
    }
    /**
     * 根据ID获取部门
     * @param deptId 部门主键
     * @return 部门对象
     */
    @Override
    public SysDept getDeptById(Long deptId) {
        return this.baseMapper.getDeptById(deptId);
    }
    /**
     * 添加ancestors 属性
     * @param sysdept 部门对象
     * @return ancestors字符串
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

