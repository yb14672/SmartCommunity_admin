package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.community.dao.ZyCommentDao;
import com.zy_admin.community.entity.ZyComment;
import com.zy_admin.community.service.ZyCommentService;
import org.springframework.stereotype.Service;

/**
 * 评论表(ZyComment)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@Service("zyCommentService")
public class ZyCommentServiceImpl extends ServiceImpl<ZyCommentDao, ZyComment> implements ZyCommentService {

}

