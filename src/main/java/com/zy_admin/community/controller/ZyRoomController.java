package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.ZyRoomDto;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.community.service.ZyRoomService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 房间 (ZyRoom)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@Api(value = "zyRoom", tags = {"房间 (ZyRoom)表控制层"})
@RestController
@RequestMapping("zyRoom")
public class ZyRoomController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyRoomService zyRoomService;
    @Resource
    private RequestUtil requestUtil;
    /**
     * 删除房屋
     * @param idList 存放房屋的id数组
     * @return 返回正确或错误信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "idList", value = "存放房屋的id数组", required = true)
    })
    @ApiOperation(value = "删除房屋", notes = "删除房屋", httpMethod = "DELETE")
    @DeleteMapping("/deleteZyRoom")
    @MyLog(title = "房屋信息", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result deleteZyRoom(@RequestParam("idList") ArrayList<String> idList){
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //修改房屋表
            result =this.zyRoomService.deleteZyRoom(idList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改房屋
     * @param zyRoom 修改的房屋信息
     * @param request 前端请求
     * @return 返回成功或错误信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyRoom", name = "zyRoom", value = "修改的房屋信息", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "修改房屋", notes = "修改房屋", httpMethod = "PUT")
    @PutMapping("/updateZyRoom")
    @MyLog(title = "房屋信息", optParam = "#{zyRoom}", businessType = BusinessType.UPDATE)
    public Result updateZyRoom(@RequestBody ZyRoom zyRoom, HttpServletRequest request) {
        SysUser user = requestUtil.getUser(request);
        zyRoom.setUpdateBy(user.getUserName());
        zyRoom.setUpdateTime(LocalDateTime.now().toString());
        return this.zyRoomService.updateZyRoom(zyRoom,request);
    }
    /**
     * 新增房屋
     * @param zyRoom 新增的房屋信息
     * @param request 前端请求
     * @return 返回成功或错误信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ZyRoom", name = "zyRoom", value = "新增的房屋信息", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "新增房屋", notes = "新增房屋", httpMethod = "POST")
    @PostMapping("/insertZyRoom")
    @MyLog(title = "房屋信息", optParam = "#{zyRoom}", businessType = BusinessType.INSERT)
    public Result insertZyRoom(@RequestBody ZyRoom zyRoom, HttpServletRequest request){
        SysUser user = requestUtil.getUser(request);
        zyRoom.setCreateBy(user.getUserName());
        zyRoom.setCreateTime(LocalDateTime.now().toString());
        return this.zyRoomService.insertZyRoom(zyRoom,request);
    }

    /**
     * 房屋数据导出
     * @param roomIds 存放房屋的id数组
     * @param response 前端响应
     * @return 成功或失败的信息
     * @throws IOException 抛出数据流异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "roomIds", value = "存放房屋的id数组", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端响应", required = true)
    })
    @ApiOperation(value = "房屋数据导出", notes = "房屋数据导出", httpMethod = "GET")
    @GetMapping("/getExcel")
    @MyLog(title = "房屋信息", optParam = "#{roomIds}", businessType = BusinessType.EXPORT)
    public Result getExcel(@RequestParam("ids") ArrayList<String> roomIds, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //用于存储要导出的数据列表
        List<ZyRoomDto> zyRoomDtoList;
        if (roomIds == null || roomIds.size() == 0) {
            ZyRoom zyRoom = new ZyRoom();
            Page<Object> page=  new Page<>(0,0);
            zyRoomDtoList = (List<ZyRoomDto>) zyRoomService.getAllCommunity(page, zyRoom).getData();
        } else {
            zyRoomDtoList = zyRoomService.getRoomByIds(roomIds);
        }
        String fileName = URLEncoder.encode("房屋信息表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), ZyRoomDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("房屋信息");
        excel.doWrite(zyRoomDtoList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 分页查询小区信息
     * @param page  分页对象
     * @param zyRoom 查询的房屋信息
     * @return 查询分页的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "ZyRoom", name = "zyRoom", value = "查询的房屋信息", required = true)
    })
    @ApiOperation(value = "分页查询小区信息", notes = "分页查询小区信息", httpMethod = "GET")
    @GetMapping
    public Result getAllCommunity(Page page, ZyRoom zyRoom) {
        return zyRoomService.getAllCommunity(page,zyRoom);
    }
}

