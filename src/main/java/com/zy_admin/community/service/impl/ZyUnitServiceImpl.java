package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyBuildingDao;
import com.zy_admin.community.dao.ZyRoomDao;
import com.zy_admin.community.dao.ZyUnitDao;
import com.zy_admin.community.dto.UnitDto;
import com.zy_admin.community.dto.UnitListDto;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.community.entity.ZyUnit;
import com.zy_admin.community.service.ZyUnitService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 单元 (ZyUnit)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Service("zyUnitService")
public class ZyUnitServiceImpl extends ServiceImpl<ZyUnitDao, ZyUnit> implements ZyUnitService {

    @Resource
    private ZyBuildingDao zyBuildingDao;

    @Resource
    private ZyRoomDao zyRoomDao;

    /**
     * 单元楼分页查询
     *
     * @param zyUnit
     * @param pageable
     * @return
     */
    @Override
    public Result getUnitList(ZyUnit zyUnit, Pageable pageable) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
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
        UnitDto unitDto = new UnitDto(unitListDtos, pageable);
        result.setData(unitDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 新增单元楼
     *
     * @param zyUnit
     * @return
     */
    @Override
    public Result insertUnit(ZyUnit zyUnit) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        ZyUnit selectBuildingId = this.baseMapper.selectBuildingId(zyUnit.getBuildingId());
        zyUnit.setCommunityId(selectBuildingId.getCommunityId());
        List<ZyUnit> selectUnitName = this.baseMapper.selectUnitName(zyUnit);
        if (selectUnitName.size() != 0) {
            result.setMeta(ResultTool.fail(ResultCode.UNIT_NAME_REPEAT));
            return result;
        }
        Long now = System.currentTimeMillis();
        zyUnit.setUnitCode("UNIT_" + now.toString().substring(0, 13));
        try {
            this.baseMapper.insertUnit(zyUnit);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.UNIT_ADD_FAIL));
        }
        return result;
    }

    /**
     * 修改角色
     *
     * @param zyUnit
     * @return
     */
    @Override
    public Result updateUnit(ZyUnit zyUnit) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        ZyUnit originUnit = this.baseMapper.queryById(zyUnit.getUnitId() + "");
        String[] fields = new String[]{"buildingId", "unitName", "unitLevel", "unitAcreage", "unitHaveElevator", "remark"};
        if (!ObjUtil.checkEquals(zyUnit, originUnit, fields)) {
            List<ZyUnit> checkUnitName = this.baseMapper.selectUnitName(zyUnit);
            if (checkUnitName.size() > 0) {
                if (checkUnitName.size() > 1) {
                    result.setMeta(ResultTool.fail(ResultCode.UNIT_NAME_REPEAT));
                    return result;
                } else {
                    if (!zyUnit.getUnitId().equals(checkUnitName.get(0).getUnitId())) {
                        result.setMeta(ResultTool.fail(ResultCode.UNIT_NAME_REPEAT));
                        return result;
                    }
                }
            }
            zyUnit.setCommunityId(originUnit.getCommunityId());
            try {
                this.baseMapper.updateUnit(zyUnit);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            }

        } else {
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
        }
        return result;
    }

    /**
     * 删除单元
     *
     * @param unitIds
     * @return
     */
    @Override
    public Result deleteUnit(List<String> unitIds) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyRoom> list = this.zyRoomDao.getRoomByUnitId(unitIds);
        if (list.size() > 0) {
            result.setMeta(ResultTool.fail(ResultCode.UNIT_HAVE_PEOPLE));
        } else {
            try {
                this.baseMapper.deleteUnit(unitIds);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
            }
        }
        return result;
    }

    /**
     * 根据社区id获取所有单元
     *
     * @return
     */
    @Override
    public List<ZyUnit> getAll(String communityId) {
        return this.baseMapper.getAll(communityId);
    }

    /**
     * 通过id获取对应单元
     *
     * @param ids
     * @return
     */
    @Override
    public List<ZyUnit> getUnitById(List<String> ids) {
        if (ids != null) {
            ids = ids.size() == 0 ? null : ids;
        }
        return this.baseMapper.getUnitById(ids);
    }

    /**
     * 根据社区id获取对应的楼栋集合
     *
     * @param id
     * @return
     */
    @Override
    public Result getBuildingList(String id) {
        List<String> ids = new ArrayList<>();
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        ids.add(id);
        try {
            List<ZyBuilding> buildingListsByIds = zyBuildingDao.getBuildingListsByIds(ids);
            result.setData(buildingListsByIds);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }

        return result;
    }


}

