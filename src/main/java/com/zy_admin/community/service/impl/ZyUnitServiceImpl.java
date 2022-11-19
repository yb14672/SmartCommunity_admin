package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyUnitDao;
import com.zy_admin.community.dto.UnitDto;
import com.zy_admin.community.dto.UnitListDto;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyUnitService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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

    /**
     * 新增单元楼
     * @param zyUnit
     * @return
     */
    @Override
    public Result insertUnit(ZyUnit zyUnit) {

        Result result = new Result();
        List<ZyUnit> unit = this.baseMapper.getUnit(zyUnit.getBuildingId());
        zyUnit.setCommunityId(unit.get(0).getCommunityId());
        ZyUnit selectUnitName = this.baseMapper.selectUnitName(unit.get(0).getCommunityId(), zyUnit.getBuildingId(), zyUnit.getUnitName());
        if (selectUnitName!=null)
        {
            result.setMeta(ResultTool.fail(ResultCode.UNIT_NAME_REPEAT)
            );
        }else {

            Long now = System.currentTimeMillis();
            zyUnit.setUnitCode("UNIT_"+now.toString().substring(0,13));
            try {
                this.baseMapper.insertUnit(zyUnit);
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.UNIT_ADD_FAIL));
            }
        }

        return result;

    }

    /**
     * 修改角色
     * @param zyUnit
     * @return
     */
    @Override
    public Result updateUnit(ZyUnit zyUnit) {

        Result result = new Result();
        List<ZyUnit> unit = this.baseMapper.getUnit(zyUnit.getBuildingId());
        ZyUnit selectUnitName = this.baseMapper.selectUnitName(unit.get(0).getCommunityId(), zyUnit.getBuildingId(), zyUnit.getUnitName());
        if (selectUnitName!=null){
            result.setMeta(ResultTool.fail(ResultCode.UNIT_NAME_REPEAT));
        }else {
            zyUnit.setCommunityId(unit.get(0).getCommunityId());
            try {
                this.baseMapper.updateUnit(zyUnit);
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            }

        }

        return result;
    }

    /**
     * 删除单元
     * @param unitIds
     * @return
     */
    @Override
    public Result deleteUnit(List<String> unitIds) {
        Result result = new Result();
        List<String> list = this.baseMapper.selectAllByUnitByIds(unitIds);
        if (list.size()>0)
        {
            result.setMeta(ResultTool.fail(ResultCode.UNIT_HAVE_PEOPLE));
        }else {
            try {
                this.baseMapper.deleteUnit(unitIds);
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
            }
        }
        return result;
    }

    @Override
    public List<ZyUnit> getAll() {
        return this.baseMapper.getAll();
    }

    @Override
    public List<ZyUnit> getUnitById(List<Integer> ids) {
        if (ids != null) {
            ids = ids.size() == 0 ? null : ids;
        }
        return this.baseMapper.getUnitById(ids);
    }



}

