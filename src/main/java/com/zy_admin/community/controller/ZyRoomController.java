package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.ZyRoomDto;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.community.service.ZyRoomService;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
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
    /**
     * 删除房屋
     * @param idList
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteZyRoom")
    @MyLog(title = "删除房屋", optParam = "#{zyRoom}", businessType = BusinessType.DELETE)
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
    @PostMapping("/updateZyRoom")
    @MyLog(title = "修改房屋", optParam = "#{zyRoom}", businessType = BusinessType.UPDATE)
    public Result updateZyRoom(@RequestBody ZyRoom zyRoom, HttpServletRequest request) throws Exception {
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
    @MyLog(title = "添加房屋", optParam = "#{zyRoom}", businessType = BusinessType.INSERT)
    public Result insertZyRoom(@RequestBody ZyRoom zyRoom, HttpServletRequest request) throws Exception {
        return this.zyRoomService.insertZyRoom(zyRoom,request);
    }
    /**
     * 房屋数据导出
     * @param roomIds
     * @param response
     */
    @GetMapping("/getExcel")
    @MyLog(title = "房屋信息", optParam = "#{roomIds}", businessType = BusinessType.EXPORT)
    public void getExcel(@RequestParam("ids") ArrayList<String> roomIds, HttpServletResponse response) throws IOException {
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
        EasyExcel.write(response.getOutputStream(), ZyRoomDto.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(zyRoomDtoList);
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

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyRoomService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyRoom 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyRoom zyRoom) {
        return success(this.zyRoomService.save(zyRoom));
    }

    /**
     * 修改数据
     *
     * @param zyRoom 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyRoom zyRoom) {
        return success(this.zyRoomService.updateById(zyRoom));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyRoomService.removeByIds(idList));
    }
}

