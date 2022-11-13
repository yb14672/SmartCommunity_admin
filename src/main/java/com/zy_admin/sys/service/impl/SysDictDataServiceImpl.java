package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysDictDataDao;
import com.zy_admin.sys.dto.DataDictExcelDto;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDictDataService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典数据表(SysDictData)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataDao, SysDictData> implements SysDictDataService {

    @Override
    public Result getDict(String deptType) {
        Result result = new Result();
        try {
            List<SysDictData> dictDataList = this.baseMapper.getDict(deptType);
            result.setData(dictDataList);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
    }

    @Override
    public Result selectDictDataLimit(SysDictData sysDictData, Page page) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //新建查询条件对象
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        //StringUtils.isNotEmpty(xxx)--当dictType不为空时，执行这个行sql
        //SysDictData::getDictType--查询哪个字段
        //sysDictData.getDictType()--查询条件
        queryWrapper.eq(StringUtils.isNotEmpty(sysDictData.getDictType()), SysDictData::getDictType, sysDictData.getDictType());
        queryWrapper.like(StringUtils.isNotEmpty(sysDictData.getDictLabel()), SysDictData::getDictLabel, sysDictData.getDictLabel());
        queryWrapper.eq(StringUtils.isNotEmpty(sysDictData.getStatus()), SysDictData::getStatus, sysDictData.getStatus());
        queryWrapper.orderByAsc(SysDictData::getDictSort);
        Page page1 = this.baseMapper.selectPage(page, queryWrapper);
        if (page1.getSize() > 0) {
            result.setData(page1);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result getDictDataById(String id) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        SysDictData sysDictData = this.baseMapper.getDictDataById(id);
        if (sysDictData.getDictCode() != null || sysDictData != null) {
            result.setData(sysDictData);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result insert(SysDictData sysDictData, SysUser user) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断用户是否登录
            if (user != null || user.getUserId() != null) {
                //因为字典标题名为必填字段，所以判断是否为空
                if (sysDictData.getDictLabel() == null || "".equals(sysDictData.getDictLabel())) {
                    result.setMeta(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                    return result;
                } else {
                    //判断字典标题名是否唯一
                    if (checkUnique(0, sysDictData, this.baseMapper.checkDictLabelUnique(sysDictData))) {
                        //当路由不为空时判断其路由是否重复
                        if (!"".equals(sysDictData.getDictValue()) || !"".equals(sysDictData.getDictValue())) {
                            //不唯一即false，因此不唯一时提示并返回
                            if (!checkUnique(0, sysDictData, this.baseMapper.checkDictValueUnique(sysDictData))) {
                                result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_DATA_VALUE));
                                return result;
                            }
                        } else {
                            result.setMeta(ResultTool.fail(ResultCode.PARAM_IS_BLANK));
                            return result;
                        }
                    } else {
                        result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_DATA_LABEL));
                        return result;
                    }
                }
                sysDictData.setCreateBy(user.getUserName());
                sysDictData.setCreateTime(LocalDateTime.now().toString());
                sysDictData.setListClass("primary");
                int i = this.baseMapper.insert(sysDictData);
                if (i == 1) {
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.USER_NOT_LOGIN));
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
        }
        return result;
    }

    @Override
    public Result updateDictData(SysDictData sysDictData, SysUser user) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断用户是否登录
            if (user != null || user.getUserId() != null) {
                //因为字典标题名为必填字段，所以判断是否为空
                if (sysDictData.getDictLabel() == null || "".equals(sysDictData.getDictLabel())) {
                    result.setMeta(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                } else {
                    //判断是否没有修改就提交
                    SysDictData dictDataById = this.baseMapper.getDictDataById(sysDictData.getDictCode() + "");
                    if(!checkEquals(sysDictData,dictDataById)){
                        //判断字典标题名是否唯一
                        if (checkUnique(1, sysDictData, this.baseMapper.checkDictValueUnique(sysDictData))) {
                            //当路由不为空时判断其路由是否重复
                            if (!"".equals(sysDictData.getDictValue()) || !"".equals(sysDictData.getDictValue())) {
                                //不唯一即false，因此不唯一时提示并返回
                                if (!checkUnique(1, sysDictData, this.baseMapper.checkDictValueUnique(sysDictData))) {
                                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_MENUPATH));
                                }
                            } else {
                                result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_DATA_VALUE));
                            }
                        } else {
                            result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_DATA_LABEL));
                        }
                    }else{
                        result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
                    }
                }
                sysDictData.setUpdateBy(user.getUserName());
                sysDictData.setUpdateTime(LocalDateTime.now().toString());
                int i = this.baseMapper.updateDictDataById(sysDictData);
                if (i == 1) {
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.USER_NOT_LOGIN));
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
        }
        return result;
    }

    @Override
    public Boolean checkUnique(int type, SysDictData dictData, SysDictData sysDictData) {
        //添加时--必须为空
        if (type == 0) {
            if (sysDictData == null || sysDictData.getDictCode() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysDictData == null || sysDictData.getDictCode() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysDictData.getDictCode().equals(dictData.getDictCode())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Result removeDictDataByIds(List<String> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        if (idList.size() == 0) {
            result.setMeta(ResultTool.fail(ResultCode.PARAM_IS_BLANK));
        } else {
            int i = this.baseMapper.deleteDictDataByIds(idList);
            if (i >= 1) {
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
        }
        return result;
    }

    @Override
    public List<DataDictExcelDto> getDictList(String dictType) {
        return this.baseMapper.getDictList(dictType);
    }

    @Override
    public List<DataDictExcelDto> getDictListById(ArrayList<Integer> idList) {
        return this.baseMapper.getDictListById(idList);
    }

    @Override
    public Boolean checkEquals(SysDictData updateData, SysDictData originalData) {
        if(updateData.getDictLabel().equals(originalData.getDictLabel())){
            if(updateData.getDictValue().equals(originalData.getDictValue())){
                if(updateData.getCssClass().equals(originalData.getCssClass())){
                    if(updateData.getDictSort().equals(originalData.getDictSort())){
                        if(updateData.getListClass().equals(originalData.getListClass())){
                            if(updateData.getStatus().equals(originalData.getStatus())){
                                if(updateData.getRemark().equals(originalData.getRemark())){
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
}

