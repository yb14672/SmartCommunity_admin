package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.community.service.ZyOwnerRoomRecordService;
import com.zy_admin.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 房屋绑定记录表 (ZyOwnerRoomRecord)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@RestController
@RequestMapping("zyOwnerRoomRecord")
public class ZyOwnerRoomRecordController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerRoomRecordService zyOwnerRoomRecordService;

    /**
     * 审核记录
     * @param zyOwnerRoomRecordId
     * @return
     */
    @GetMapping("/selectZyOwnerRoomRecord")
    public Result selectZyOwnerRoomRecord(String zyOwnerRoomRecordId){
        Result result = zyOwnerRoomRecordService.selectZyOwnerRoomRecord(zyOwnerRoomRecordId);
        return result;
    }

    /**
     * 分页查询所有数据
     *
     * @param page              分页对象
     * @param zyOwnerRoomRecord 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyOwnerRoomRecord> page, ZyOwnerRoomRecord zyOwnerRoomRecord) {
        return success(this.zyOwnerRoomRecordService.page(page, new QueryWrapper<>(zyOwnerRoomRecord)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyOwnerRoomRecordService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyOwnerRoomRecord 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyOwnerRoomRecord zyOwnerRoomRecord) {
        return success(this.zyOwnerRoomRecordService.save(zyOwnerRoomRecord));
    }

    /**
     * 修改数据
     *
     * @param zyOwnerRoomRecord 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyOwnerRoomRecord zyOwnerRoomRecord) {
        return success(this.zyOwnerRoomRecordService.updateById(zyOwnerRoomRecord));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyOwnerRoomRecordService.removeByIds(idList));
    }
}

