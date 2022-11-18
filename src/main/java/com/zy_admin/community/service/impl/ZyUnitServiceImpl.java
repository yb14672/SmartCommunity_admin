package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dao.ZyUnitDao;
import com.zy_admin.community.dto.UnitDto;
import com.zy_admin.community.dto.UnitListDto;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyUnitService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 单元 (ZyUnit)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Service("zyUnitService")
public class ZyUnitServiceImpl extends ServiceImpl<ZyUnitDao, ZyUnit> implements ZyUnitService {

    /**
     * 单元楼分页查询
     * @param zyUnit
     * @param pageable
     * @return
     */
    @Override
    public Result getUnitList(ZyUnit zyUnit,Pageable pageable) {
        Result result = new Result();
        Long total = this.baseMapper.count(zyUnit);
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
        List<UnitListDto> unitListDtos = this.baseMapper.queryAllByLimit(zyUnit, pageable);
        UnitDto unitDto = new UnitDto(unitListDtos,pageable);
        result.setData(unitDto);
        result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        return result;
    }
}

