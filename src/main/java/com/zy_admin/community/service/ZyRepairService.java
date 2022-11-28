package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.RepairDto;
import com.zy_admin.community.entity.ZyRepair;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 报修信息(ZyRepair)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
public interface ZyRepairService extends IService<ZyRepair> {
    /**
     * 分页查询所有报修数据
     * @param pageable     分页对象
     * @param repairDto 查询报修对象
     * @return 所有报修信息数据结果集
     */
    Result getAllRepairs(Pageable pageable, RepairDto repairDto);

    /**
     * 得到所有报修列表
     * @param repairIds 报修id
     * @return 获取的结果集
     */
    List<RepairDto> getAllRepairList(ArrayList<String> repairIds);
    /**
     * 通过id获取报修
     * @param repairIds 报修id
     * @return 获取的结果集
     */
    List<RepairDto> getRepairById(ArrayList<String> repairIds);

    /**
     * 插入报修信息
     *
     * @param zyRepair 报修对象
     * @param request  前端请求
     * @return 插入的结果集
     */
    Result insertRepair(ZyRepair zyRepair, HttpServletRequest request);

    /**
     * 更新修复
     *
     * @param zyRepair 报修对象
     * @return 更新的结果集
     */
    Result updateRepair(ZyRepair zyRepair, HttpServletRequest request);

    /**
     * 删除报修信息
     *
     * @param idList id列表
     * @return 删除的结果集
     */
    Result deleteRepair(List<String> idList);
}

