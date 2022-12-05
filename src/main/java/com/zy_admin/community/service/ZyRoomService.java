package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.community.dto.GetRoomSDto;
import com.zy_admin.community.dto.ZyRoomDto;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.common.core.Result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 房间 (ZyRoom)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
public interface ZyRoomService extends IService<ZyRoom> {
    /**
     * 分页查询小区信息
     * @param page  分页对象
     * @param zyRoom 查询的房屋信息
     * @return 查询分页的结果集
     */
    Result getAllCommunity(Page page, ZyRoom zyRoom);
    /**
     * 根据id查房屋信息
     * @param roomIds 存放房屋的id数组
     * @return 返回房屋id数组
     */
    List<ZyRoomDto> getRoomByIds(ArrayList<String> roomIds);
    /**
     * 新增房屋
     * @param zyRoom 新增的房屋信息
     * @param request 前端请求
     * @return 返回成功或错误信息
     */
    Result insertZyRoom(ZyRoom zyRoom, HttpServletRequest request);
    /**
     * 修改房屋
     * @param zyRoom 修改的房屋信息
     * @param request 前端请求
     * @return 返回成功或错误信息
     */
    Result updateZyRoom(ZyRoom zyRoom, HttpServletRequest request);
    /**
     * 删除房屋
     * @param idList 存放房屋的id数组
     * @return 返回正确或错误信息
     */
    Result deleteZyRoom(ArrayList<String> idList);

    /**
     * 查询房间状态总数
     * @return 查询结果集
     */
    Result getRoomStatuses();
}

