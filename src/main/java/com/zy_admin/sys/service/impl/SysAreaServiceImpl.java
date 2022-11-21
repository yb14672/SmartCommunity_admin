package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysAreaDao;
import com.zy_admin.sys.entity.SysArea;
import com.zy_admin.sys.service.SysAreaService;
import com.zy_admin.util.AreaTree;
import com.zy_admin.util.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

/**
 * 区域表(SysArea)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:33
 */
@Service("sysAreaService")
public class SysAreaServiceImpl extends ServiceImpl<SysAreaDao, SysArea> implements SysAreaService {


    @Override
    public Result queryAreaTree() {
        Result result = new Result(null, ResultTool.fail(ResultCode.ADDRESS_GET_FAIL));
        try {
            AreaTree areaTree = new AreaTree(this.baseMapper.queryAreaTree());
            result.setData(areaTree.buildTree());
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            return result;
        }
    }
}

