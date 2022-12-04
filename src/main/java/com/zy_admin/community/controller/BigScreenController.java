package com.zy_admin.community.controller;

import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import com.zy_admin.community.service.ZyComplaintSuggestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yb14672
 * Time:2022 - 2022/12/4 - 16:48
 **/
@RestController
@RequestMapping("/bigScreen")
public class BigScreenController {
    @Resource
    private ZyCommunityInteractionService interactionService;
    @Resource
    private ZyComplaintSuggestService suggestService;

    /**
     * 获取一个月内的互动信息
     *
     * @param limitNum 总共显示多少条
     * @return 一个月内的互动信息
     */
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
    @GetMapping("/getSuggestInMonth")
    public Result getSuggestInMonth(String limitNum) {
        return suggestService.getSuggestInMonth(limitNum);
    }
}
