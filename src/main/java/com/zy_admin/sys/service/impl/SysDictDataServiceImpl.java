package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysDictDataDao;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.sys.service.SysDictDataService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.StringUtils;
import org.springframework.stereotype.Service;

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
        Result result =new Result();
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        //新建查询条件对象
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        //StringUtils.isNotEmpty(xxx)--当dictType不为空时，执行这个行sql
        //SysDictData::getDictType--查询哪个字段
        //sysDictData.getDictType()--查询条件
        queryWrapper.eq(StringUtils.isNotEmpty(sysDictData.getDictType()),SysDictData::getDictType,sysDictData.getDictType());
        queryWrapper.like(StringUtils.isNotEmpty(sysDictData.getDictLabel()),SysDictData::getDictLabel,sysDictData.getDictLabel());
        queryWrapper.eq(StringUtils.isNotEmpty(sysDictData.getStatus()),SysDictData::getStatus,sysDictData.getStatus());
        queryWrapper.orderByAsc(SysDictData::getDictSort);
        Page page1 = this.baseMapper.selectPage(page, queryWrapper);
        if(page1.getSize() > 0){
            result.setData(page1);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
}

