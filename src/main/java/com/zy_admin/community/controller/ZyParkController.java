package com.zy_admin.community.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyParkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ZyPark)表控制层
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
@RestController
@RequestMapping("zyPark")
public class ZyParkController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyParkService zyParkService;

    /**
     * 分页查询
     *
     * @param zyPark      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<ZyPark>> queryByPage(ZyPark zyPark, PageRequest pageRequest) {
        return ResponseEntity.ok(this.zyParkService.queryByPage(zyPark, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ZyPark> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.zyParkService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param zyPark 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<ZyPark> add(ZyPark zyPark) {
        return ResponseEntity.ok(this.zyParkService.insert(zyPark));
    }

    /**
     * 编辑数据
     *
     * @param zyPark 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<ZyPark> edit(ZyPark zyPark) {
        return ResponseEntity.ok(this.zyParkService.update(zyPark));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.zyParkService.deleteById(id));
    }

}

