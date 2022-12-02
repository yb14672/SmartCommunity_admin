package com.zy_admin.community.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.ZyOwnerParkDto;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.service.ZyOwnerParkService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 房屋绑定表 (ZyOwnerPark)表控制层
 *
 * @author makejava
 * @since 2022-12-01 15:50:35
 */
@Api(value = "zyOwnerPark", tags = {"车位审核 (zyOwnerPark)表控制层"})
@RestController
@RequestMapping("zyOwnerPark")
@CrossOrigin
public class ZyOwnerParkController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerParkService zyOwnerParkService;

    /**
     * 请求
     */
    @Resource
    private RequestUtil requestUtil;

    /**
     * 删除车位审核
     * @param idList 要删除的投诉建议id
     * @return 返回
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "idList", value = "要删除的车位审核的id", required = true)
    })
    @ApiOperation(value = "删除车位审核", notes = "删除车位审核", httpMethod = "DELETE")
    @DeleteMapping
    public Result deleteOwnerParkByIds(@RequestParam("idList") ArrayList<String> idList){
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            result = this.zyOwnerParkService.deleteOwnerParkByIds(idList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改车位审核
     * @param zyOwnerPark 要更新的车位审核对象
     * @param request 前端请求
     * @return 更新车位审核结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "zyOwnerPark", name = "zyOwnerPark", value = "要修改的车位审核对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "修改车位审核", notes = "修改车位审核", httpMethod = "PUT")
    @PutMapping("/updateOwnerPark")
    public Result updateOwnerPark(@RequestBody ZyOwnerPark zyOwnerPark, HttpServletRequest request){
        ZyOwner owner = this.requestUtil.getOwner(request);
        zyOwnerPark.setUpdateBy(owner.getOwnerRealName());
        zyOwnerPark.setOwnerId(owner.getOwnerId());
        zyOwnerPark.setUpdateTime(LocalDateTime.now().toString());
        return zyOwnerParkService.updateOwnerPark(zyOwnerPark);
    }

    /**
     * 新增车位审核
     * @param zyOwnerPark 要新增的车位审核
     * @param request 前端请求
     * @return  查询的车位审核结果集
     * @throws Exception 将存在的异常抛出
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwnerPark", name = "zyOwnerPark", value = "要新增的车位审核信息", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "新增车位审核", notes = "新增车位审核", httpMethod = "POST")
    @PostMapping("/insertOwnerPark")
    public Result insertOwnerPark(@RequestBody ZyOwnerPark zyOwnerPark, HttpServletRequest request) throws Exception {
        ZyOwner owner = this.requestUtil.getOwner(request);
        zyOwnerPark.setCreateBy(owner.getOwnerRealName());
        zyOwnerPark.setCreateTime(LocalDateTime.now().toString());
        zyOwnerPark.setOwnerId(owner.getOwnerId());
        return this.zyOwnerParkService.insertOwnerPark(zyOwnerPark);
    }

    /**
     * 更新车位审核状态
     *
     * @param zyOwnerPark        车位审核对象
     * @param recordAuditOpinion 记录审计意见
     * @param status             状态
     * @param request            请求
     * @return {@link Result}
     * @throws Exception 异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "zyOwnerPark", name = "zyOwnerPark", value = "车位审核对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "recordAuditOpinion", value = "记录审核意见", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "status", value = "状态", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "修改车位审核的状态", notes = "修改车位审核的状态", httpMethod = "PUT")
    @PutMapping("/updateOwnerParkStatus")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "车位审核", optParam = "#{zyOwnerPark}", businessType = BusinessType.UPDATE)
    public Result updateOwnerParkStatus(@RequestBody ZyOwnerPark zyOwnerPark, String recordAuditOpinion, String status, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyOwnerPark.setUpdateBy(user.getUserName());
        //从前端把那个更新的状态带过来
        zyOwnerPark.setParkOwnerStatus(status);
        return zyOwnerParkService.updateOwnerParkStatus(zyOwnerPark, recordAuditOpinion);
    }

    /**
     * 分页和查询车位审核
     * @param zyOwnerParkDto 车位审核对象
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "zyOwnerParkDto", name = "zyOwnerParkDto", value = "车位审核对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页", required = true)
    })
    @ApiOperation(value = "分页和查询车位审核", notes = "分页和查询车位审核", httpMethod = "GET")
    @GetMapping("selectAllParkLimit")
    public Result selectAllParkLimit(ZyOwnerParkDto zyOwnerParkDto, Page page){
        return zyOwnerParkService.selectAllParkLimit(zyOwnerParkDto,page);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ZyOwnerPark> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.zyOwnerParkService.queryById(id));
    }
}
