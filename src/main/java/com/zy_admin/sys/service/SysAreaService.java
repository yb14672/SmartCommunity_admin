package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysArea;
import com.zy_admin.util.Result;

/**
 * 区域表(SysArea)表服务接口
 * @author makejava
 * @since 2022-11-01 19:49:33
 */
public interface SysAreaService extends IService<SysArea> {
    /**
     * 生成省市区树形结构
     * @return 省市区的树形信息的结果集
     */
    Result queryAreaTree();
}

