package com.zy_admin.sys.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.sys.entity.SysDictData;
import com.zy_admin.sys.service.SysDictDataService;
import com.zy_admin.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 字典数据表(SysDictData)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@RestController
@RequestMapping("/sysDictData")
public class SysDictDataController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param sysDictData 查询实体
     * @return 所有数据
     */
    @GetMapping()
    public Result selectAll(SysDictData sysDictData, Page page) {
        return this.sysDictDataService.selectDictDataLimit(sysDictData,page);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysDictDataService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysDictData 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysDictData sysDictData) {
        return success(this.sysDictDataService.save(sysDictData));
    }

    /**
     * 修改数据
     *
     * @param sysDictData 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysDictData sysDictData) {
        return success(this.sysDictDataService.updateById(sysDictData));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysDictDataService.removeByIds(idList));
    }

    @GetMapping("/getDict")
    public Result getDict(String dictType){
        return this.sysDictDataService.getDict(dictType);
    }
}

