package com.zy_admin.community.controller;

import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.GetRoomSDto;
import com.zy_admin.community.dto.TreeDto;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import com.zy_admin.community.service.ZyCommunityService;
import com.zy_admin.community.service.ZyRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yb14672
 * Time:2022 - 2022/12/4 - 16:48
 **/
@Api(value = "bigScreen", tags = {"Time:2022 - 2022/12/4 - 16:48*/"})
@RestController
@RequestMapping("bigScreen")
public class BigScreenController {
    @Resource
    private ZyCommunityInteractionService interactionService;
    @Resource
    private ZyCommunityService zyCommunityService;
    @Resource
    private ZyRoomService zyRoomService;

    /**
     * 获取不同销售状态的房屋总数
     * @return 查询结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "GetRoomSDto", name = "getRoomSDto", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/getRoomStatuses")
    public Result getRoomStatuses() {
        return zyRoomService.getRoomStatuses();
    }
    /**
     * 获取所有省的总小区数
     * @return 查询结果集
     */
    @ApiOperation(value = "获取所有省的总小区数", notes = "获取所有省的总小区数", httpMethod = "GET")
    @GetMapping("/getProvinces")
    public Result getProvinces(){
        return  zyCommunityService.getProvinces();
    }
    /**
     * 获取所有城市的总小区数
     * @param provence
     * @return 查询结果集
     */

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "provence", value = "", required = true)
    })
    @ApiOperation(value = "获取所有城市的总小区数", notes = "获取所有城市的总小区数", httpMethod = "GET")
    @GetMapping("/getCities")
    public Result getCities(String provence){
        return  zyCommunityService.getCities(provence);
    }
    /**
     * 获取一周内的互动信息
     *
     * @return 一周以内的互动信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "limitNum", value = "", required = true)
    })
    @ApiOperation(value = "获取一周内的互动信息", notes = "获取一周内的互动信息", httpMethod = "GET")
    @GetMapping("/ssyj")
    public Result getInteractionInMonth(String limitNum) {
        return interactionService.getInteractionInMonth(limitNum);
    }
}
