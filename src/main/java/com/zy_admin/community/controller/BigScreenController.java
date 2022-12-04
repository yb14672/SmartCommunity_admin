package com.zy_admin.community.controller;

import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yb14672
 * Time:2022 - 2022/12/4 - 16:48
 **/
@RestController
@RequestMapping("bigScreen")
public class BigScreenController {
    @Resource
    private ZyCommunityInteractionService interactionService;

    /**
     * 获取一周内的互动信息
     *
     * @return 一周以内的互动信息
     */
    @GetMapping("/ssyj")
    public Result getInteractionInMonth(String limitNum) {
        return interactionService.getInteractionInMonth(limitNum);
    }
}
