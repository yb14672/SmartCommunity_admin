package com.zy_admin.sys.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.sys.service.SysAreaService;
import com.zy_admin.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 区域表(SysArea)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:33
 */
@RestController
@Api(tags = "地区接口")
@RequestMapping("sysArea")
public class SysAreaController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysAreaService sysAreaService;

    /**
     * 获取地区树
     * @return 查询结果
     */
    @ApiOperation("获取获取树状地区菜单")
    @GetMapping("/queryAreaTree")
    public Result queryAreaTree(){
        return sysAreaService.queryAreaTree();
    }
}

