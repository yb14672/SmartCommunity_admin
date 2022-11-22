package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysDictDataDao;
import com.zy_admin.sys.dao.SysDictTypeDao;
import com.zy_admin.sys.dto.SysDictDto;
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.sys.service.SysDictTypeService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型表(SysDictType)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@Service("sysDictTypeService")
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeDao, SysDictType> implements SysDictTypeService {

    @Resource
    private SysDictDataDao sysDictDataDao;
    /**
     * 根据id列表查询字典类型
     * @param dictIds 字典类型的主键集合
     * @return 字典类型的集合
     */
    @Override
    public List<SysDictType> queryDictById(ArrayList<Integer> dictIds) {
        //如果有选中列表，就执行导出多个
        if (dictIds != null) {
            dictIds = dictIds.size() == 0 ? null : dictIds;
        }
        return baseMapper.queryDictById(dictIds);
    }
    /**
     * 获取所有字典类型信息
     *
     * @return
     */
    @Override
    public List<SysDictType> getDictLists() {
        return baseMapper.getDictLists();
    }




    /**
     * 分页加查询
     *
     * @param sysDictType
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Result selectDictByLimit(SysDictType sysDictType, Pageable pageable, String startTime, String endTime) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //满足条件的总数据
        long total = this.baseMapper.count(sysDictType, startTime, endTime);
        long pages = 0;
        if (total > 0) {
            //计算出总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<SysDictType> sysDictTypeList = this.baseMapper.selectDictByLimit(sysDictType, pageable, startTime, endTime);
//        封装一个dto，把对象和分页放进去
        SysDictDto sysDictDto = new SysDictDto(sysDictTypeList, startTime, endTime, pageable);
//        存到data数据里面
        result.setData(sysDictDto);
//        返回信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 分页查询所有的字典类型数据
     * @return 所有查询的字典类型结果集
     */
    @Override
    public Result selectDictAll() {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysDictType> sysDictTypeList = this.baseMapper.selectDictAll();
        if (!sysDictTypeList.isEmpty() || sysDictTypeList.size() > 0) {
            result.setData(sysDictTypeList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
    /**
     * 新增字典
     * @param sysDictType 字典类型对象
     * @return 新增的字典类型结果集
     */
    @Override
    public Result insertOrUpdateBatch(SysDictType sysDictType) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断字典名称唯一
        if (selectSysDictByName(0, sysDictType)) {
            //判断字典类型唯一
            if (selectSysDictByType(0, sysDictType)) {
                try {
                    //新增字典
                    int sysDictType1 = this.baseMapper.insert(sysDictType);
                    result.setData("新增成功，影响的行数：" + sysDictType1);
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                } catch (Exception e) {
                    return result;
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_TYPE));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_NAME));
        }
        return result;
    }
    /**
     * 修改字典类型
     * @param sysDictType 字典类型对象
     * @return 修改的字典类型结果集
     */
    @Override
    public Result updateDict(SysDictType sysDictType) {
        SysDictType sysDictType1 = this.baseMapper.queryById(sysDictType.getDictId() + "");
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //需要判断的字段名
            String[] fields = new String[]{"dictName", "dictType","status", "remark"};
            if (!ObjUtil.checkEquals(sysDictType, sysDictType1,fields)) {
                //type为1是修改
                if (selectSysDictByName(1, sysDictType)) {
                    if (selectSysDictByType(1, sysDictType)) {
                        int update = this.baseMapper.update(sysDictType);
                        if (update == 1) {
                            //判断字典类型是否有修改
                            if (!sysDictType1.getDictType().equals(sysDictType.getDictType())) {
                                this.baseMapper.updateDictDataByDictType(sysDictType1.getDictType(), sysDictType.getDictType());
                            }
                            //判断字典状态是否有修改
                            if (!sysDictType1.getStatus().equals(sysDictType.getStatus())) {
                                this.sysDictDataDao.changeStatusByDictType(sysDictType.getDictType(), sysDictType.getStatus());
                            }
                            result.setData("修改成功，影响的行数：" + sysDictType1);
                            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                        }
                    } else {
                        result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_TYPE));
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_NAME));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }
        return result;
    }
    /**
     * 通过主键查询单条数据
     * @param id 字典类型的主键
     * @return 单条数据结果集
     */
    @Override
    public Result getDictTypeById(String id) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        SysDictType sysDictType = this.baseMapper.queryById(id);
        if (sysDictType != null || sysDictType.getDictId() != null) {
            result.setData(sysDictType);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
    /**
     * 删除字典类型
     * @param idList 字典类型的主键数组
     * @return 删除的字典类型结果集
     */
    @Override
    public Result deleteByIdList(List<Integer> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        // 判断是单个
        if (idList.size() == 1) {
            // 查当前的有没有子集 hasChildDict返回的是子集的数量
            Integer hasChildDict = this.baseMapper.hasChildDict(idList);
            // 小于1说明没有子集，就可以删
            if (hasChildDict < 1) {
                int i = this.baseMapper.deleteByIdList(idList);
                result.setData("删除成功，影响的行数：" + i);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.DICT_HAVE_CHILDREN));
            }
            // 多个就是批量删除
        } else {
            // 是多个就判断下面有没有子集
            Integer childs = this.baseMapper.hasChildDict(idList);
            if (childs < 1) {
                int i = this.baseMapper.deleteByIdList(idList);
                if (i >= 1) {
                    result.setData("删除成功，影响的行数：" + i);
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.DICT_HAVE_CHILDREN));
            }
        }
        return result;
    }
    /**
     * 判断name是否重复
     * @param type 判断是添加(0)还是修改(1)
     * @param sysDictType 字典类型对象
     * @return
     */
    public boolean selectSysDictByName(int type, SysDictType sysDictType) {
        SysDictType sysDictType1 = this.baseMapper.selectSysDictByName(sysDictType.getDictName());
        // 类型为0是新增
        if (type == 0) {
            // 判断是否为空
            if (sysDictType1 == null || sysDictType1.getDictId() == null) {
                return true;
            }
        } else {
            // 修改
            if (sysDictType1 == null || sysDictType1.getDictId() == null) {
                return true;
                // 判断他的名字是否唯一
            } else if (!sysDictType1.getDictId().equals(sysDictType.getDictId())) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    /**
     * 判断type是否重复
     * @param type  判断是添加(0)还是修改(1)
     * @param sysDictType 字典类型对象
     * @return 成功或失败的结果集
     */
    public boolean selectSysDictByType(int type, SysDictType sysDictType) {
        SysDictType sysDictType1 = this.baseMapper.selectSysDictByType(sysDictType.getDictType());
        //类型为0是新增
        if (type == 0) {
            //判断是否为空
            if (sysDictType1 == null || sysDictType1.getDictId() == null) {
                return true;
            }
        } else {
            //修改
            if (sysDictType1 == null || sysDictType1.getDictId() == null) {
                return true;
                //判断他的类型是否唯一
            } else if (!sysDictType1.getDictId().equals(sysDictType.getDictId())) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}

