package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.VisitorGetExcelDto;
import com.zy_admin.community.entity.ZyVisitor;

import java.util.List;

/**
 * 访客邀请 (ZyVisitor)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:04
 */
public interface ZyVisitorService extends IService<ZyVisitor> {

    List<VisitorGetExcelDto>getLists(String communityId);
    List<VisitorGetExcelDto> queryVisitorrById(List<String> ids);
    /**
     * 得到访客名单
     *
     * @param zyVisitor zy访客
     * @param pageable  可分页
     * @return {@link Result}
     */
    Result getVisitorList(ZyVisitor zyVisitor, Pageable pageable);


    /**
     * 更新状态
     *
     * @param zyVisitor 修改条件
     * @return {@link Result}
     */
    Result updateStatus(ZyVisitor zyVisitor);


    /**
     * 访客申请
     *
     * @param zyVisitor zy访客
     * @return {@link Result}
     */
    Result insertVisitor( ZyVisitor zyVisitor);
}

