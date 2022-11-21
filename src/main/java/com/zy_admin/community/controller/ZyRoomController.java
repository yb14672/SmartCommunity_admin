package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
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
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
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
     * @param idList
     * @return
     * @throws Exception
     */
    @DeleteMapping("/deleteZyRoom")
    @MyLog(title = "房屋信息", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result deleteZyRoom(@RequestParam("idList") ArrayList<String> idList){
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //修改房屋表
            result =this.zyRoomService.deleteZyRoom(idList);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改房屋
     * @param zyRoom
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping("/updateZyRoom")
    @MyLog(title = "房屋信息", optParam = "#{zyRoom}", businessType = BusinessType.UPDATE)
    public Result updateZyRoom(@RequestBody ZyRoom zyRoom, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyRoom.setUpdateBy(user.getUserName());
        zyRoom.setUpdateTime(LocalDateTime.now().toString());
        return this.zyRoomService.updateZyRoom(zyRoom,request);
    }
    /**
     * 新增房屋
     * @param zyRoom
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/insertZyRoom")
    @MyLog(title = "房屋信息", optParam = "#{zyRoom}", businessType = BusinessType.INSERT)
    public Result insertZyRoom(@RequestBody ZyRoom zyRoom, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyRoom.setCreateBy(user.getUserName());
        zyRoom.setCreateTime(LocalDateTime.now().toString());
        return this.zyRoomService.insertZyRoom(zyRoom,request);
    }
    /**
     * 房屋数据导出
     * @param roomIds
     * @param response
     */
    @GetMapping("/getExcel")
    @MyLog(title = "房屋信息", optParam = "#{roomIds}", businessType = BusinessType.EXPORT)
    public Result getExcel(@RequestParam("ids") ArrayList<String> roomIds, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //用于存储要导出的数据列表
        List<ZyRoomDto> zyRoomDtoList = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        //判断idlist是否为空，若为空则没选要导出的，即导出全部
        if (roomIds == null || roomIds.size() == 0) {
            ZyRoom zyRoom = new ZyRoom();
            Page page=  new Page<>(0,0);
            zyRoomDtoList = (List<ZyRoomDto>) zyRoomService.getAllCommunity(page, zyRoom).getData();
        } else {
            //执行查询角色列表的sql语句
            zyRoomDtoList = zyRoomService.getRoomByIds(roomIds);
        }
        String fileName = URLEncoder.encode("房屋信息表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
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
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param zyRoom 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result getAllCommunity(Page page, ZyRoom zyRoom) {
        Result result = zyRoomService.getAllCommunity(page,zyRoom);
        return result;
    }
}

