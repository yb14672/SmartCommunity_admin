package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.service.SysOperLogService;
import com.zy_admin.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 操作日志记录(SysOperLog)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@RestController
@RequestMapping("sysOperLog")
public class SysOperLogController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysOperLogService sysOperLogService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysOperLogService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysOperLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysOperLog sysOperLog) {
        return success(this.sysOperLogService.save(sysOperLog));
    }

    /**
     * 修改数据
     *
     * @param sysOperLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysOperLog sysOperLog) {
        return success(this.sysOperLogService.updateById(sysOperLog));
    }

    /**
     * 删除数据
     *
     * @param Logids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/deleteLog")
    public Result deleteById(@RequestParam("idList") List<Integer> Logids) {
        return sysOperLogService.deleteById(Logids);
    }
    /**
     * 分页查询所有数据
     *
     * @param pageable       分页对象
     * @param sysOperLog 查询实体
     * @return 所有数据
     */

    @GetMapping("/getOperLogList")
    public Result getOperLogList(SysOperLog sysOperLog, Pageable pageable, String startTime, String endTime){
        return this.sysOperLogService.getOperLogList(sysOperLog, pageable, startTime, endTime);
    }
}

