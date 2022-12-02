package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.service.ZyOwnerRoomService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.common.core.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 房屋绑定表 (ZyOwnerRoom)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Api(value = "zyOwnerRoom", tags = {"房屋绑定表 (ZyOwnerRoom)表控制层"})
@RestController
@RequestMapping("zyOwnerRoom")
public class ZyOwnerRoomController extends ApiController {
    /**
     * 业主房间关联服务
     */
    @Resource
    private ZyOwnerRoomService zyOwnerRoomService;

    /**
     * 请求跑龙套
     */
    @Resource
    private RequestUtil requestUtil;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/selectOwnerRoomByOwnerId")
    public Result selectOwnerRoomByOwnerId(HttpServletRequest request){
        String ownerId = requestUtil.getOwnerId(request);
        return this.zyOwnerRoomService.selectOwnerRoomByOwnerId(ownerId);
    }

    /**
     * 根据业主ID获取其房屋绑定列表
     * @param request 前端发送的请求
     * @return 关联关系列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端发送的请求", required = true)
    })
    @ApiOperation(value = "根据业主ID获取其房屋绑定列表", notes = "根据业主ID获取其房屋绑定列表", httpMethod = "GET")
    @GetMapping("/getOwnerRoomByOwnerId")
    public Result getOwnerRoomByOwnerId(HttpServletRequest request){
        return this.zyOwnerRoomService.getOwnerRoomByOwnerId(requestUtil.getOwnerId(request));
    }
    
    /**
     * 新增数据
     *
     * @param ownerRoom 实体对象
     * @param request   请求
     * @return 新增结果
     * @throws Exception 异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwnerRoom", name = "ownerRoom", value = "实体对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "插入 新增数据", notes = "插入 新增数据", httpMethod = "POST")
    @PostMapping("/insert")
    public Result insert(@RequestBody ZyOwnerRoom ownerRoom, HttpServletRequest request) throws Exception {
        ZyOwner owner = requestUtil.getOwner(request);
        ownerRoom.setOwnerId(owner.getOwnerId());
        ownerRoom.setOwnerType(owner.getOwnerType());
        ownerRoom.setCreateBy(owner.getOwnerRealName());
        return this.zyOwnerRoomService.ownerInsert(ownerRoom);
    }

    /**
     * 得到树数据
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "得到树数据", notes = "得到树数据", httpMethod = "GET")
    @GetMapping("/getTree")
    public Result getTreeData(){
        return this.zyOwnerRoomService.getTreeData();
    }

    /**
     * 更新拒绝主人房间状态
     * 修改业主审核的状态
     *
     * @param zyOwnerRoom        业主房间关联
     * @param recordAuditOpinion 记录审计意见
     * @param status             状态
     * @param request            请求
     * @return {@link Result}
     * @throws Exception 异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwnerRoom", name = "zyOwnerRoom", value = "业主房间关联", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "recordAuditOpinion", value = "记录审计意见", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "status", value = "状态", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "请求", required = true)
    })
    @ApiOperation(value = "修改业主审核的状态", notes = "修改业主审核的状态", httpMethod = "PUT")
    @PutMapping("/updateOwnerRoomStatus")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "业主审核", optParam = "#{zyOwnerRoom}", businessType = BusinessType.UPDATE)
    public Result updateOwnerRoomStatusReject(@RequestBody ZyOwnerRoom zyOwnerRoom, String recordAuditOpinion,String status, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyOwnerRoom.setUpdateBy(user.getUserName());
        zyOwnerRoom.setRoomStatus(status);
        return zyOwnerRoomService.updateOwnerRoomStatus(zyOwnerRoom, recordAuditOpinion);
    }

    /**
     * 选择所有主人房间限制
     * 分页和查询业主审核
     *
     * @param zyOwnerRoom 业主房间关联
     * @param pageable    分页对象
     * @return {@link Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyOwnerRoom", name = "zyOwnerRoom", value = "业主房间关联", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true)
    })
    @ApiOperation(value = "分页和查询业主审核", notes = "分页和查询业主审核", httpMethod = "GET")
    @GetMapping("selectAllOwnerRoomLimit")
    public Result selectAllOwnerRoomLimit(ZyOwnerRoom zyOwnerRoom, Pageable pageable){
        return zyOwnerRoomService.selectAllOwnerRoomLimit(zyOwnerRoom,pageable);
    }
}

