package com.zy_admin.community.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyOwnerParkRecord;
import com.zy_admin.community.service.ZyOwnerParkRecordService;
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
 * 房屋绑定记录表 (ZyOwnerParkRecord)表控制层
 *
 * @author makejava
 * @since 2022-12-01 15:13:39
 */
@Api(value = "zyOwnerParkRecord", tags = {"房屋绑定记录表 (ZyOwnerParkRecord)表控制层"})
@RestController
@RequestMapping("zyOwnerParkRecord")
public class ZyOwnerParkRecordController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerParkRecordService zyOwnerParkRecordService;

    /**
     * 审核记录
     * @param ownerParkId 审核记录的id
     * @return  审核记录对象
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "ownerParkId", value = "审核记录的id", required = true)
    })
    @ApiOperation(value = "审核车位记录", notes = "审核车位记录", httpMethod = "GET")
    @GetMapping("/selectOwnerParkById")
    public Result selectOwnerParkById(String ownerParkId) {
        return zyOwnerParkRecordService.selectOwnerParkById(ownerParkId);
    }

    /**
     * 分页查询
     *
     * @param zyOwnerParkRecord 筛选条件
     * @param pageRequest       分页对象
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyOwnerParkRecord", name = "zyOwnerParkRecord", value = "筛选条件", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "PageRequest", name = "pageRequest", value = "分页对象", required = true)
    })
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("{id}")
    public ResponseEntity<ZyOwnerParkRecord> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.zyOwnerParkRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param zyOwnerParkRecord 实体
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyOwnerParkRecord", name = "zyOwnerParkRecord", value = "实体", required = true)
    })
    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyOwnerParkRecord", name = "zyOwnerParkRecord", value = "实体", required = true)
    })
    @ApiOperation(value = "编辑数据", notes = "编辑数据", httpMethod = "PUT")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "long", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.zyOwnerParkRecordService.deleteById(id));
    }

}

