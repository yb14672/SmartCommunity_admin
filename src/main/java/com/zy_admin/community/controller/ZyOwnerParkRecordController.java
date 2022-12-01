package com.zy_admin.community.controller;

import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerParkRecord;
import com.zy_admin.community.service.ZyOwnerParkRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 房屋绑定记录表 (ZyOwnerParkRecord)表控制层
 *
 * @author makejava
 * @since 2022-12-01 16:42:59
 */
@RestController
@RequestMapping("zyOwnerParkRecord")
public class ZyOwnerParkRecordController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerParkRecordService zyOwnerParkRecordService;




    /**
     * 分页查询
     *
     * @param zyOwnerParkRecord 筛选条件
     * @param pageRequest       分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<ZyOwnerParkRecord>> queryByPage(ZyOwnerParkRecord zyOwnerParkRecord, PageRequest pageRequest) {
        return ResponseEntity.ok(this.zyOwnerParkRecordService.queryByPage(zyOwnerParkRecord, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ZyOwnerParkRecord> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.zyOwnerParkRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param zyOwnerParkRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<ZyOwnerParkRecord> add(ZyOwnerParkRecord zyOwnerParkRecord) {
        return ResponseEntity.ok(this.zyOwnerParkRecordService.insert(zyOwnerParkRecord));
    }

    /**
     * 编辑数据
     *
     * @param zyOwnerParkRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<ZyOwnerParkRecord> edit(ZyOwnerParkRecord zyOwnerParkRecord) {
        return ResponseEntity.ok(this.zyOwnerParkRecordService.update(zyOwnerParkRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.zyOwnerParkRecordService.deleteById(id));
    }

}

