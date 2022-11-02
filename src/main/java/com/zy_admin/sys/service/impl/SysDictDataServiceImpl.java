package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysDictDataDao;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.sys.service.SysDictDataService;
import org.springframework.stereotype.Service;

/**
 * 字典数据表(SysDictData)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataDao, SysDictData> implements SysDictDataService {

}

