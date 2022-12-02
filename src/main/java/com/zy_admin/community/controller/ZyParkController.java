package com.zy_admin.community.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyParkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "zyPark", tags = {"(ZyPark)表控制层"})
@RestController
@RequestMapping("zyPark")
public class ZyParkController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyParkService zyParkService;

    /**
     * 查询车位状态是启用0的
     * @return {@link Result}
     */
    @ApiOperation(value = "查询车位状态是启用的", notes = "查询车位状态是启用的", httpMethod = "GET")
    @GetMapping("/selectParkStatusOpen")
    public Result selectParkStatusOpen(){
        return this.zyParkService.selectParkStatusOpen();
    }

    /**
     * 分页查询
     *
     * @param zyPark      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyPark", name = "zyPark", value = "筛选条件", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "PageRequest", name = "pageRequest", value = "分页对象", required = true)
    })
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyPark", name = "zyPark", value = "实体", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyPark", name = "zyPark", value = "实体", required = true)
    })
    @ApiOperation(value = "编辑数据", notes = "编辑数据", httpMethod = "PUT")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "long", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.zyParkService.deleteById(id));
    }

}

