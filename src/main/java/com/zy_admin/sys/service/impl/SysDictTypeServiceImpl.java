package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysDictTypeDao;
import com.zy_admin.sys.dto.SysDictDto;
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.sys.service.SysDictTypeService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典类型表(SysDictType)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@Service("sysDictTypeService")
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeDao, SysDictType> implements SysDictTypeService {

    @Override
    public Result getDictTypeById(String id) {
        Result result = new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        SysDictType sysDictType = this.baseMapper.queryById(id);
        if (sysDictType != null || sysDictType.getDictId() != null) {
            result.setData(sysDictType);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
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
        Result result = new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
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
        //封装一个dto，把对象和分页放进去
        SysDictDto sysDicDto = new SysDictDto(sysDictTypeList, startTime, endTime, pageable);
        //存到data数据里面
        result.setData(sysDicDto);
        //返回信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 新增
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertOrUpdateBatch(SysDictType sysDictType) {
        Result result = new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断字典名称唯一
        if (selectSysDictByName(0, sysDictType)) {
        //判断字典类型唯一
            if (selectSysDictByType(0, sysDictType)) {
                try {
                    //新增字典
                    int sysDictType1 = this.baseMapper.insert(sysDictType);
                    result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
                } catch (Exception e) {
                    return new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
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
     * 修改字典
     *
     * @param sysDictType
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateDict(SysDictType sysDictType) {
        Result result = new Result();
        //type为1是修改
        if (selectSysDictByName(1, sysDictType)) {
            if (selectSysDictByType(1, sysDictType)) {
                try {
                    int update = this.baseMapper.update(sysDictType);
                    return new Result(null, ResultTool.fail(ResultCode.SUCCESS));
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_NAME));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_DICT_TYPE));
        }
        return result;
    }

    @Override
    public Result selectDictAll() {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysDictType> sysDictTypeList = this.baseMapper.selectDictAll();
        if(!sysDictTypeList.isEmpty()||sysDictTypeList.size() > 0) {
            result.setData(sysDictTypeList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 判断name是否重复
     *
     * @param type
     * @param sysDictType
     * @return
     */
    public boolean selectSysDictByName(int type, SysDictType sysDictType) {
        SysDictType sysDictType1 = this.baseMapper.selectSysDictByName(sysDictType.getDictName());
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
            //判断他的名字是否唯一
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
     *
     * @param type
     * @param sysDictType
     * @return
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

