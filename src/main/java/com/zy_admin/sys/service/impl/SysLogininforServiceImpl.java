package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysLogininforDao;
import com.zy_admin.sys.entity.SysLogininfor;
import com.zy_admin.sys.service.SysLogininforService;
import org.springframework.stereotype.Service;

/**
 * 系统访问记录(SysLogininfor)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@Service("sysLogininforService")
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforDao, SysLogininfor> implements SysLogininforService {

}

