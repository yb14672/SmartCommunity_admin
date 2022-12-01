package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.service.ZyOwnerRoomRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 房屋绑定记录表 (ZyOwnerRoomRecord)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Api(value = "zyOwnerRoomRecord", tags = {"房屋绑定记录表 (ZyOwnerRoomRecord)表控制层"})
@RestController
@RequestMapping("zyOwnerRoomRecord")
public class ZyOwnerRoomRecordController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerRoomRecordService zyOwnerRoomRecordService;

    /**
     * 根据ID查询审核记录列表
     * @param zyOwnerRoomRecordId 主键ID
     * @return 审核记录列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "zyOwnerRoomRecordId", value = "主键ID", required = true)
    })
    @ApiOperation(value = "审核记录", notes = "审核记录", httpMethod = "GET")
    @GetMapping("/selectZyOwnerRoomRecord")
    public Result selectZyOwnerRoomRecord(String zyOwnerRoomRecordId) {
        return zyOwnerRoomRecordService.selectZyOwnerRoomRecord(zyOwnerRoomRecordId);
    }
}

