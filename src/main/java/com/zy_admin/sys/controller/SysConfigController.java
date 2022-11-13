package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.zy_admin.sys.entity.SysConfig;
import com.zy_admin.sys.service.SysConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 参数配置表(SysConfig)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@RestController
@RequestMapping("sysConfig")
public class SysConfigController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysConfigService sysConfigService;



    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysConfigService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysConfig 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysConfig sysConfig) {
        return success(this.sysConfigService.save(sysConfig));
    }

    /**
     * 修改数据
     *
     * @param sysConfig 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysConfig sysConfig) {
        return success(this.sysConfigService.updateById(sysConfig));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysConfigService.removeByIds(idList));
    }
}

