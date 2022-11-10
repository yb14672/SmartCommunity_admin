package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysDictType;
import com.zy_admin.sys.service.SysDictTypeService;
import com.zy_admin.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典类型表(SysDictType)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@RestController
@RequestMapping("sysDictType")
public class SysDictTypeController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictTypeService sysDictTypeService;

    /**
     * 修改
     * @return
     */
    @PutMapping("/updateDict")
    public Result updateDict(@RequestBody SysDictType sysDictType){
        return sysDictTypeService.updateDict(sysDictType);
    }
    /**
     * 新增字典
     * @param sysDictType
     * @return
     */
    @PostMapping("/addSysDict")
    public Result insertDictType(@RequestBody SysDictType sysDictType){
        System.out.println(sysDictType);
        //sysDictType.setCreateTime(LocalDateTime.now().toString());
        return this.sysDictTypeService.insertOrUpdateBatch(sysDictType);
    }


    /**
     * 分页查询
     * @param sysDictType
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/selectDictByLimit")
    public Result selectDictByLimit(SysDictType sysDictType, Pageable pageable, String startTime, String endTime){
        return sysDictTypeService.selectDictByLimit(sysDictType, pageable,startTime,endTime);
    }

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll() {
        return this.sysDictTypeService.selectDictAll();
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable String id) {
        return this.sysDictTypeService.getDictTypeById(id);
    }

    /**
     * 新增数据
     *
     * @param sysDictType 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysDictType sysDictType) {
        return success(this.sysDictTypeService.save(sysDictType));
    }

    /**
     * 修改数据
     *
     * @param sysDictType 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysDictType sysDictType) {
        return success(this.sysDictTypeService.updateById(sysDictType));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysDictTypeService.removeByIds(idList));
    }
}

