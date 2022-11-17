package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysConfigDao;
import com.zy_admin.sys.entity.SysConfig;
import com.zy_admin.sys.service.SysConfigService;
import org.springframework.stereotype.Service;

/**
 * 参数配置表(SysConfig)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {

}

