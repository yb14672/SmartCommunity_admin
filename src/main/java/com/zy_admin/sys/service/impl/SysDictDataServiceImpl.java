package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.dao.SysDictDataDao;
import com.zy_admin.sys.dao.SysDictTypeDao;
import com.zy_admin.sys.dto.DataDictExcelDto;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDictDataService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    /**
     * 服务对象
     */
    @Resource
    private SysDictTypeDao sysDictTypeDao;
    /**
     * 根据字典类型获取所有字典数据
     * @param dictType 字典类型
     * @return 根据字典类型获取所有字典数据结果集
     */
    @Override
    public Result getDict(String dictType) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            List<SysDictData> dictDataList = this.baseMapper.getDict(dictType);
            result.setData(dictDataList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
    }
    /**
     * 分页查询所有字典数据
     * @param page 分页对象
     * @param sysDictData 查询字典数据对象
     * @return 分页查询的结果集
     */
    @Override
    public Result selectDictDataLimit(SysDictData sysDictData, Page page) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //新建查询条件对象
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        //StringUtils.isNotEmpty(xxx)--当dictType不为空时，执行这个行sql
        //SysDictData::getDictType--查询哪个字段
        //sysDictData.getDictType()--查询条件
        queryWrapper.eq(StringUtil.isNotEmpty(sysDictData.getDictType()), SysDictData::getDictType, sysDictData.getDictType());
        queryWrapper.like(StringUtil.isNotEmpty(sysDictData.getDictLabel()), SysDictData::getDictLabel, sysDictData.getDictLabel());
        queryWrapper.eq(StringUtil.isNotEmpty(sysDictData.getStatus()), SysDictData::getStatus, sysDictData.getStatus());
        queryWrapper.orderByAsc(SysDictData::getDictSort);
        Page page1 = this.baseMapper.selectPage(page, queryWrapper);
        if (page1.getSize() > 0) {
            result.setData(page1);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
    /**
     * 通过字典数据id查询单条数据
     * @param id 字典数据主键
     * @return 单条数据结果集
     */
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
    /**
     * 新增字典数据
     * @param sysDictData 字典数据对象
     * @return 新增的字典数据结果集
     */
    @Override
    public Result insert(SysDictData sysDictData, SysUser user) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断用户是否登录
            if (user != null) {
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
                    result.setData("新增成功，影响的行数：" + i);
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
    /**
     * 修改字典数据
     * @param sysDictData 字典数据对象
     * @return 修改的字典数据结果集
     */
    @Override
    public Result updateDictData(SysDictData sysDictData, SysUser user) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断用户是否登录
            if (user != null) {
                //因为字典标题名为必填字段，所以判断是否为空
                if (sysDictData.getDictLabel() == null || "".equals(sysDictData.getDictLabel())) {
                    result.setMeta(ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE));
                } else {
                    //判断是否没有修改就提交
                    SysDictData dictDataById = this.baseMapper.getDictDataById(sysDictData.getDictCode() + "");
                    //需要判断的字段名
                    String[] fields = new String[]{"dictLabel", "dictValue", "cssClass", "dictSort", "listClass", "status", "remark"};
                    if (!ObjUtil.checkEquals(sysDictData, dictDataById, fields)) {
                        //判断字典标题名是否唯一
                        if (checkUnique(1, sysDictData, this.baseMapper.checkDictLabelUnique(sysDictData))) {
                            //当字典键值不为空时判断其路由是否重复
                            if (!"".equals(sysDictData.getDictValue()) || !"".equals(sysDictData.getDictValue())) {
                                //不唯一即false，因此不唯一时提示并返回
                                if (checkUnique(1, sysDictData, this.baseMapper.checkDictValueUnique(sysDictData))) {
                                    //当其修改状态时
                                    if (!sysDictData.getStatus().equals(dictDataById.getStatus())) {
                                        SysDictType sysDictType = sysDictTypeDao.selectSysDictByType(dictDataById.getDictType());
                                        //若它父类是停用则不准启用
                                        if ("1".equals(sysDictType.getStatus())) {
                                            result.setMeta(ResultTool.fail(ResultCode.PARENT_CLASS_DEACTIVATE));
                                            return result;
                                        }
                                    }
                                } else {
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
                    } else {
                        result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
                        return result;
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
    /**
     * 检查数据键值是否唯一
     * @param type 判断是新增0还是修改1
     * @param dictData 字典数据对象
     * @return 成功或失败的结果集
     */
    @Override
    public Boolean checkUnique(int type, SysDictData dictData, SysDictData sysDictData) {
        //添加时--必须为空
        if (type == 0) {
            return sysDictData == null || sysDictData.getDictCode() == null;
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysDictData == null || sysDictData.getDictCode() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else {
                return sysDictData.getDictCode().equals(dictData.getDictCode());
            }
        }
    }
    /**
     * 删除字典数据
     * @param idList 字典数据主键集合
     * @return 删除的字典结果集
     */
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
    /**
     * 查询所有字典数据
     * @param dictType 字典数据对象
     * @return 导出字典数据的集合
     */
    @Override
    public List<DataDictExcelDto> getDictList(String dictType) {
        return this.baseMapper.getDictList(dictType);
    }
    /**
     * 根据ID查询字典列表
     * @param idList 字典数据主键
     * @return 字典数据的集合
     */
    @Override
    public List<DataDictExcelDto> getDictListById(List<Integer> idList) {
        return this.baseMapper.getDictListById(idList);
    }
}
