package com.zy_admin.sys.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.sys.service.SysAreaService;
import com.zy_admin.common.core.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * 区域表(SysArea)表控制层
 * @author makejava
 * @since 2022-11-01 19:49:33
 */
@Api(value = "sysArea", tags = {"区域表(SysArea)表控制层"})
@RestController
@RequestMapping("sysArea")
public class SysAreaController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysAreaService sysAreaService;

    /**
     * 生成省市区树形结构
     * @return 返回关于树的数据结果集
     */
    @ApiOperation(value = "生成省市区树形结构", notes = "生成省市区树形结构", httpMethod = "GET")
    @GetMapping("/queryAreaTree")
    public Result queryAreaTree(){
        return sysAreaService.queryAreaTree();
    }
}

