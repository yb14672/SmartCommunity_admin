package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyUnitDao;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyUnitService;
import org.springframework.stereotype.Service;

/**
 * 单元 (ZyUnit)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Service("zyUnitService")
public class ZyUnitServiceImpl extends ServiceImpl<ZyUnitDao, ZyUnit> implements ZyUnitService {

}

