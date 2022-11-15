package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyOwnerService;
import org.springframework.stereotype.Service;

/**
 * 业主 (ZyOwner)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerService")
public class ZyOwnerServiceImpl extends ServiceImpl<ZyOwnerDao, ZyOwner> implements ZyOwnerService {

}

