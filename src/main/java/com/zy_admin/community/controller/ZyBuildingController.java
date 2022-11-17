package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.log.BusinessType;
import com.zy_admin.common.core.log.MyLog;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.service.ZyBuildingService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 楼栋 (ZyBuilding)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@RestController
@RequestMapping("zyBuilding")
public class ZyBuildingController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyBuildingService zyBuildingService;

    /**
     * 删除
     * @param idList
     * @return
     */
    @DeleteMapping
    @MyLog(title = "删除楼层类型", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result delete(@RequestParam String[] idList){
        List<Integer> idList1 = new ArrayList<Integer>();
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            for (String str : idList) {
//                把删除选上的id添加到idlist1的集合里
                idList1.add(Integer.valueOf(str));
            }
            //修改字典表
            result = this.zyBuildingService.deleteByIdList(idList1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改楼层
     * @return
     */
    @PutMapping("/updateZyBuilding")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "修改楼层", optParam = "#{zyBuilding}", businessType = BusinessType.UPDATE)
    public Result updateZyBuilding(@RequestBody ZyBuilding zyBuilding){
        System.out.println(zyBuilding);
        System.out.println("controller");
        return zyBuildingService.updateZyBuilding(zyBuilding);
    }

    /**
     * 新增楼层
     * @param zyBuilding
     * @return
     */
    @PostMapping("/addZyBuilding")
    @MyLog(title = "新增楼层", optParam = "#{zyBuilding}", businessType = BusinessType.INSERT)
    public Result insertDictType(@RequestBody ZyBuilding zyBuilding){
        return this.zyBuildingService.insertZyBuilding(zyBuilding);
    }

    /**
     * 分页查询
     * @param zyBuilding
     * @param pageable
     * @return
     */
    @GetMapping("/selectBuildLimit")
    public Result selectBuildLimit(ZyBuilding zyBuilding, Pageable pageable){
        Result result = zyBuildingService.selectBuildLimit(zyBuilding, pageable);
        return result;
    }

    /**
     * 通过主键查询单条数据,修改里面加上id
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable String id) {
        return zyBuildingService.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param zyBuilding 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyBuilding zyBuilding) {
        return success(this.zyBuildingService.save(zyBuilding));
    }

    /**
     * 修改数据
     *
     * @param zyBuilding 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyBuilding zyBuilding) {
        return success(this.zyBuildingService.updateById(zyBuilding));
    }

}

