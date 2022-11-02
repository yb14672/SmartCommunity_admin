package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyBuildingDao;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.service.ZyBuildingService;
import org.springframework.stereotype.Service;

/**
 * 楼栋 (ZyBuilding)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@Service("zyBuildingService")
public class ZyBuildingServiceImpl extends ServiceImpl<ZyBuildingDao, ZyBuilding> implements ZyBuildingService {

}

