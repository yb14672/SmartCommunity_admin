package com.zy_admin.community.controller;

import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import com.zy_admin.community.service.ZyCommunityService;
import com.zy_admin.community.service.ZyRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.zy_admin.community.service.ZyComplaintSuggestService;
import com.zy_admin.community.service.ZyOwnerRoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 大屏幕控制器
 *
 * @author yb14672
 * Time:2022 - 2022/12/4 - 16:48
 * @date 2022/12/05
 */
@Api(value = "/bigScreen", tags = {"大屏幕控制器 Time:2022 - 2022/12/4 - 16:48"})
@RestController
@RequestMapping("/bigScreen")
public class BigScreenController {
    /**
     * 交互服务
     */
    @Resource
    private ZyCommunityInteractionService interactionService;
    /**
     * 建议服务
     */
    @Resource
    private ZyComplaintSuggestService suggestService;
    /**
     * 业主房间服务
     */
    @Resource
    private ZyOwnerRoomService ownerRoomService;
    /**
     * 小区服务
     */
    @Resource
    private ZyCommunityService zyCommunityService;
    /**
     * 房间服务
     */
    @Resource
    private ZyRoomService zyRoomService;

    /**
     * 获取不同销售状态的房屋总数
     *
     * @return 查询结果集
     */
    @ApiOperation(value = "让房间状态 获取不同销售状态的房屋总数", notes = "让房间状态 获取不同销售状态的房屋总数", httpMethod = "GET")
    @GetMapping("/getRoomStatuses")
    public Result getRoomStatuses() {
        return zyRoomService.getRoomStatuses();
    }

    /**
     * 获取所有省的总小区数
     *
     * @return 查询结果集
     */
    @ApiOperation(value = "获取所有省的总小区数", notes = "获取所有省的总小区数", httpMethod = "GET")
    @GetMapping("/getProvinces")
    public Result getProvinces(){
        return  zyCommunityService.getProvinces();
    }

    /**
     * 获取所有城市的总小区数
     *
     * @param provence 普罗旺斯
     * @return 查询结果集
     */

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "provence", value = "普罗旺斯", required = true)
    })
    @ApiOperation(value = "获取所有城市的总小区数", notes = "获取所有城市的总小区数", httpMethod = "GET")
    @GetMapping("/getCities")
    public Result getCities(String provence){
        return  zyCommunityService.getCities(provence);
    }

    /**
     * 获取一个月内的互动信息
     *
     * @param limitNum 总共显示多少条
     * @return 一个月内的互动信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "limitNum", value = "总共显示多少条", required = true)
    })
    @ApiOperation(value = "获取一个月内的互动信息", notes = "获取一个月内的互动信息", httpMethod = "GET")
    @GetMapping("/getInteractionInMonth")
    public Result getInteractionInMonth(String limitNum) {
        return interactionService.getInteractionInMonth(limitNum);
    }

    /**
     * 获取一个月内的投诉建议
     *
     * @param limitNum 总共显示多少条
     * @return 一个月内的投诉建议
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "limitNum", value = "总共显示多少条", required = true)
    })
    @ApiOperation(value = "获取一个月内的投诉建议", notes = "获取一个月内的投诉建议", httpMethod = "GET")
    @GetMapping("/getSuggestInMonth")
    public Result getSuggestInMonth(String limitNum) {
        return suggestService.getSuggestInMonth(limitNum);
    }

    /**
     * 获取绑定率
     *
     * @return 获取绑定率
     */
    @ApiOperation(value = "获取绑定率", notes = "获取绑定率", httpMethod = "GET")
    @GetMapping("/getTheNumberOfHouseBindings")
    public Result getTheNumberOfHouseBindings() {
        return ownerRoomService.getTheNumberOfHouseBindings();
    }
}
