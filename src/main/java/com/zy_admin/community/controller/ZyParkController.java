package com.zy_admin.community.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.ZyParkDto;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyParkService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.SnowflakeManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

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
    @Resource
    private RequestUtil requestUtil;
    @Resource
    private SnowflakeManager snowflakeManager;


    /**
     * 批量插入
     *
     * @param zyPark  停车位
     * @param number  数量
     * @param request 请求
     * @return {@link Result}
     * @throws Exception 异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyPark", name = "zyPark", value = "停车位", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "number", value = "数量", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "批量插入", notes = "批量插入", httpMethod = "POST")
    @PostMapping("/batchInsert")
    public Result batchInsert(@RequestBody ZyPark zyPark,@RequestParam("number") Integer number, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyPark.setCreateBy(user.getUserName());
        zyPark.setCreateTime(LocalDateTime.now().toString());
        return zyParkService.batchInsert(zyPark, number);
    }

    /**
     * 新增停车位
     *
     * @param zyPark  停车位
     * @param request 请求
     * @return {@link Result}
     * @throws Exception 异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyPark", name = "zyPark", value = "停车位", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "新增停车位", notes = "新增停车位", httpMethod = "POST")
    @PostMapping("/insertPark")
    public Result insertPark(@RequestBody ZyPark zyPark, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyPark.setCreateBy(user.getUserName());
        zyPark.setCreateTime(LocalDateTime.now().toString());
        zyPark.setParkId(snowflakeManager.nextId() + "");
        long now = System.currentTimeMillis();
        zyPark.setParkCode("PK_" + Long.toString(now).substring(0, 13));
        return zyParkService.insertPark(zyPark);
    }

    /**
     * 修改车位
     *
     * @param request 请求
     * @param zyPark  停车位
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "ZyPark", name = "zyPark", value = "停车位", required = true)
    })
    @ApiOperation(value = "修改车位", notes = "修改车位", httpMethod = "PUT")
    @PutMapping("/updatePark")
    public Result updatePark(@RequestBody ZyPark zyPark,HttpServletRequest request) {
        SysUser user = requestUtil.getUser(request);
        zyPark.setUpdateBy(user.getUserName());
        zyPark.setUpdateTime(LocalDateTime.now().toString());
        return this.zyParkService.updatePark(zyPark);
    }

    /**
     * 删除停车位
     *
     * @param parkIds 停车位
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<String>", name = "parkIds", value = "停车位", required = true)
    })
    @ApiOperation(value = "删除停车位", notes = "删除停车位", httpMethod = "DELETE")
    @DeleteMapping
    public Result deletePark(@RequestParam("parkIds") List<String> parkIds) {
        return this.zyParkService.deletePark(parkIds);
    }

    /**
     * 分页查询
     *
     * @param zyPark 筛选条件
     * @param page   分页对象
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyParkDto", name = "zyPark", value = "筛选条件", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Page", name = "page", value = "分页对象", required = true)
    })
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
    @GetMapping
    public Result queryByPage(ZyParkDto zyPark, Page page) {
        return this.zyParkService.queryByPage(zyPark, page);
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
    public Result queryById(@PathVariable("id") String id) {
        return this.zyParkService.queryById(id);
    }
}

