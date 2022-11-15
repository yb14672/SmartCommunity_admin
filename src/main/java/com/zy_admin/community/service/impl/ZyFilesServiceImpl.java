package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyFilesDao;
import com.zy_admin.community.entity.ZyFiles;
import com.zy_admin.community.service.ZyFilesService;
import org.springframework.stereotype.Service;

/**
 * 文件管理(ZyFiles)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Service("zyFilesService")
public class ZyFilesServiceImpl extends ServiceImpl<ZyFilesDao, ZyFiles> implements ZyFilesService {

}

