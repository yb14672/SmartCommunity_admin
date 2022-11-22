package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.community.dto.ZyRoomDto;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.util.Result;

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
     *
     * 分页查询
     * @param page
     * @param zyRoom
     * @return
     */
    Result getAllCommunity(Page page, ZyRoom zyRoom);

    /**
     * 根据id查房屋信息
     * @param roomIds
     * @return
     */
    List<ZyRoomDto> getRoomByIds(ArrayList<String> roomIds);

    /**
     * 新增房屋
     * @param zyRoom
     * @param request
     * @return
     */
    Result insertZyRoom(ZyRoom zyRoom, HttpServletRequest request) throws Exception;
    /**
     * 修改房屋
     * @param zyRoom
     * @param request
     * @return
     */
    Result updateZyRoom(ZyRoom zyRoom, HttpServletRequest request);
    /**
     * 删除房屋
     * @param idList
     * @return
     */
    Result deleteZyRoom(ArrayList<String> idList);
}

