package com.zy_admin.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.OwnerParkListDto;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.service.ZyOwnerParkService;
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
    public Result getOwnerParkList(OwnerParkListDto ownerParkListDto, Page page )
    {

        Result ownerParkList = zyOwnerParkService.getOwnerParkList(ownerParkListDto, page);
        System.out.println(ownerParkList.toString());
        return ownerParkList;
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

