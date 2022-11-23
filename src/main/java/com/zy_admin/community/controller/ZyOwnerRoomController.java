package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
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
     * 服务对象
     */
    @Resource
    private ZyOwnerRoomService zyOwnerRoomService;

    @Resource
    private RequestUtil requestUtil;

    /**
     * 新增数据
     *
     * @param ownerRoom 实体对象
     * @return 新增结果
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody ZyOwnerRoom ownerRoom, HttpServletRequest request) throws Exception {
        return this.zyOwnerRoomService.ownerInsert(ownerRoom,request);
    }

    /**
     *
     * @return
     */
    @GetMapping("/getTree")
    public Result getTreeData(){
        return this.zyOwnerRoomService.getTreeData();
    }

    /**
     * 修改业主审核的状态
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyOwnerRoom", name = "zyOwnerRoom", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "recordAuditOpinion", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "status", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "修改业主审核的状态", notes = "修改业主审核的状态", httpMethod = "PUT")
    @PutMapping("/updateOwnerRoomStatus")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "业主审核", optParam = "#{zyOwnerRoom}", businessType = BusinessType.UPDATE)
    public Result updateOwnerRoomStatusReject(@RequestBody ZyOwnerRoom zyOwnerRoom, String recordAuditOpinion,String status, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyOwnerRoom.setUpdateBy(user.getUserName());
        zyOwnerRoom.setRoomStatus(status);
        return zyOwnerRoomService.updateOwnerRoomStatus(zyOwnerRoom, recordAuditOpinion, request);
    }

    /**
     * 分页和查询业主审核
     * @param zyOwnerRoom
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ZyOwnerRoom", name = "zyOwnerRoom", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "", required = true)
    })
    @ApiOperation(value = "分页和查询业主审核", notes = "分页和查询业主审核", httpMethod = "GET")
    @GetMapping("selectAllOwnerRoomLimit")
    public Result selectAllOwnerRoomLimit(ZyOwnerRoom zyOwnerRoom, Pageable pageable){
        Result result = zyOwnerRoomService.selectAllOwnerRoomLimit(zyOwnerRoom,pageable);
        return result;
    }
}

