package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dao.ZyCommunityDao;
import com.zy_admin.community.dto.ZyCommunityDto;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.service.ZyCommunityService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小区 (ZyCommunity)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Service("zyCommunityService")
public class ZyCommunityServiceImpl extends ServiceImpl<ZyCommunityDao, ZyCommunity> implements ZyCommunityService {
//   @Resource
//    private ZyCommunityDao zyCommunityDao;

    @Override
    public Result selectCommunityLimit(ZyCommunity zyCommunity, Pageable pageable) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //满足条件的总数据
        long total = this.baseMapper.count(zyCommunity);
        long pages = 0;
        if (total > 0) {
            //计算出总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<ZyCommunity> zyCommunityList = this.baseMapper.selectCommunityLimit(zyCommunity, pageable);
//        封装一个dto，把对象和分页放进去
        ZyCommunityDto zyCommunityDto = new ZyCommunityDto(zyCommunityList, pageable);
//        存到data数据里面
        result.setData(zyCommunityDto);
//        返回信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
}

