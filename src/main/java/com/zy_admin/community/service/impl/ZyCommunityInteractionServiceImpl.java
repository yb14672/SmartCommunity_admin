package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyCommunityInteractionDao;
import com.zy_admin.community.entity.ZyCommunityInteraction;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import org.springframework.stereotype.Service;

/**
 * 社区互动表(ZyCommunityInteraction)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Service("zyCommunityInteractionService")
public class ZyCommunityInteractionServiceImpl extends ServiceImpl<ZyCommunityInteractionDao, ZyCommunityInteraction> implements ZyCommunityInteractionService {

}

