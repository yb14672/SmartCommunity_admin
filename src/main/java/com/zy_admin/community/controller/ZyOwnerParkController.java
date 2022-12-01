package com.zy_admin.community.controller;

import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.service.ZyOwnerParkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 房屋绑定表 (ZyOwnerPark)表控制层
 *
 * @author makejava
 * @since 2022-12-01 15:18:32
 */
@RestController
@RequestMapping("zyOwnerPark")
public class ZyOwnerParkController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerParkService zyOwnerParkService;


    @GetMapping("/getOwnerParkList")
    public Result getOwnerParkList(ZyOwner zyOwner, Pageable pageable)
    {

        return null;
    }

    /**
     * 分页查询
     *
     * @param zyOwnerPark 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<ZyOwnerPark>> queryByPage(ZyOwnerPark zyOwnerPark, PageRequest pageRequest) {
        return ResponseEntity.ok(this.zyOwnerParkService.queryByPage(zyOwnerPark, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ZyOwnerPark> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.zyOwnerParkService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param zyOwnerPark 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<ZyOwnerPark> add(ZyOwnerPark zyOwnerPark) {
        return ResponseEntity.ok(this.zyOwnerParkService.insert(zyOwnerPark));
    }

    /**
     * 编辑数据
     *
     * @param zyOwnerPark 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<ZyOwnerPark> edit(ZyOwnerPark zyOwnerPark) {
        return ResponseEntity.ok(this.zyOwnerParkService.update(zyOwnerPark));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.zyOwnerParkService.deleteById(id));
    }

}

