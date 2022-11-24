package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyRepairDao;
import com.zy_admin.community.dto.RepairAllDto;
import com.zy_admin.community.dto.RepairDto;
import com.zy_admin.community.entity.ZyRepair;
import com.zy_admin.community.service.ZyRepairService;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 报修信息(ZyRepair)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Service("zyRepairService")
public class ZyRepairServiceImpl extends ServiceImpl<ZyRepairDao, ZyRepair> implements ZyRepairService {

    /**
     * 分页查询所有报修数据
     *
     * @param pageable  分页对象
     * @param repairDto 查询报修对象
     * @return 所有报修信息数据结果集
     */
    @Override
    public Result getAllRepairs(Pageable pageable, RepairDto repairDto) {
        //默认给失败的情况
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //满足条件的总数
        Long total = this.baseMapper.count(repairDto);
        //默认设置页面为0
        long pages;
        if (total > 0) {
            //计算出总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(Math.min(pageable.getPageNum(), pages));
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<RepairDto> repairList = this.baseMapper.getAllRepairs(pageable, repairDto);
        //封装一个dto，把对象和分页放进去
        RepairAllDto repairAllDto = new RepairAllDto(repairList, pageable);
        //存到data数据里面
        result.setData(repairAllDto);
        //返回信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 得到所有报修列表
     *
     * @param repairIds 报修id
     * @return 查询的结果集
     */
    @Override
    public List<RepairDto> getAllRepairList(ArrayList<String> repairIds) {
        return this.baseMapper.getAllRepairList(repairIds);
    }

    /**
     * 通过id获取报修
     *
     * @param repairIds 报修id
     * @return 查询的结果集
     */
    @Override
    public List<RepairDto> getRepairById(ArrayList<String> repairIds) {
        return this.baseMapper.getRepairById(repairIds);
    }

    /**
     * 插入报修信息
     *
     * @param zyRepair 报修对象
     * @param request  前端请求
     * @return 插入的结果集
     */
    @Override
    public Result insertRepair(ZyRepair zyRepair, HttpServletRequest request) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        long now = System.currentTimeMillis();
        zyRepair.setRepairNum("BX_" + Long.toString(now).substring(0, 13));
        //新增报修
        int num = this.baseMapper.insertRepair(zyRepair);
        if (num == 1) {
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            result.setData("新增成功");
        }
        return result;
    }

    /**
     * 更新修复
     *
     * @param zyRepair 报修对象
     * @return 更新的结果集
     */
    @Override
    public Result updateRepair(ZyRepair zyRepair) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //更新报修
        int num = this.baseMapper.updateRepair(zyRepair);
        if (num == 1) {
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            result.setData("更新成功");
        }
        return result;
    }

    /**
     * 删除报修信息
     *
     * @param idList id列表
     * @return 删除的结果集
     */
    @Override
    public Result deleteRepair(List<String> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
            int i = this.baseMapper.deleteRepair(idList);
            if (i >= 1) {
            result.setData("删除成功");
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }else {
                result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            }
        return result;
    }
}
