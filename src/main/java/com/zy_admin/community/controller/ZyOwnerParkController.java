package com.zy_admin.community.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.ZyOwnerParkDto;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.service.ZyOwnerParkService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 房屋绑定表 (ZyOwnerPark)表控制层
 *
 * @author makejava
 * @since 2022-12-01 15:50:35
 */
@RestController
@RequestMapping("zyOwnerPark")
public class ZyOwnerParkController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerParkService zyOwnerParkService;

    /**
     * 分页和查询车位审核
     * @param zyOwnerParkDto 车位审核对象
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "zyOwnerParkDto", name = "zyOwnerParkDto", value = "车位审核对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页", required = true)
    })
    @ApiOperation(value = "分页和查询车位审核", notes = "分页和查询车位审核", httpMethod = "GET")
    @GetMapping("selectAllParkLimit")
    public Result selectAllParkLimit(ZyOwnerParkDto zyOwnerParkDto, Page page){
        System.out.println(zyOwnerParkDto);
        return zyOwnerParkService.selectAllParkLimit(zyOwnerParkDto,page);
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

