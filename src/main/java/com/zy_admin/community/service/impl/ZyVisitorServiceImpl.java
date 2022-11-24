package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyVisitorDao;
import com.zy_admin.community.dto.OwnerDto;
import com.zy_admin.community.dto.OwnerListDto;
import com.zy_admin.community.dto.VisitorDto;
import com.zy_admin.community.dto.VisitorListDto;
import com.zy_admin.community.entity.ZyVisitor;
import com.zy_admin.community.service.ZyVisitorService;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 访客邀请 (ZyVisitor)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:04
 */
@Service("zyVisitorService")
public class ZyVisitorServiceImpl extends ServiceImpl<ZyVisitorDao, ZyVisitor> implements ZyVisitorService {

    /**
     * 得到访客名单
     *
     * @param zyVisitor zy访客
     * @param pageable  可分页
     * @return {@link Result}
     */
    @Override
    public Result getVisitorList(ZyVisitor zyVisitor, Pageable pageable) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        Long total = this.baseMapper.count(zyVisitor);
        long pages = 0;
        if (total > 0) {
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
        List<VisitorListDto> visitorListDtos = null;
        try {
            List<VisitorListDto> visitorListDtoList = this.baseMapper.queryAllByLimit(zyVisitor, pageable);

            VisitorDto visitorList = new VisitorDto(visitorListDtoList, pageable);
            result.setData(visitorList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }

        return result;
    }

    @Override
    public Result updateStatus(ZyVisitor zyVisitor) {
        Result result = new Result();
        try {
            this.baseMapper.updateStatus(zyVisitor);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }
        return result;
    }

    @Override
    public Result insertVisitor(ZyVisitor zyVisitor) {
        Result result = new Result();
        try {
            this.baseMapper.insertVisitor(zyVisitor);
            result.setMeta(ResultTool.fail(ResultCode.VISITOR_APPLICATION_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.VISITOR_APPLICATION_FAIL));
        }
        return result;
    }
}

