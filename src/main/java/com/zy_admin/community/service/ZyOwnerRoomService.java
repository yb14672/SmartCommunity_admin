package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.util.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 房屋绑定表 (ZyOwnerRoom)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerRoomService extends IService<ZyOwnerRoom> {
    /**
     * 查询所有业主审核的和分页
     * @param zyOwnerRoom
     * @param pageable
     * @return
     */
    Result selectAllOwnerRoomLimit(ZyOwnerRoom zyOwnerRoom, Pageable pageable);

    /**
     * 修改业主审核的状态
     * @param zyOwnerRoom
     * @return
     */
    Result updateOwnerRoomStatus(ZyOwnerRoom zyOwnerRoom, ZyOwnerRoomRecord zyOwnerRoomRecord, HttpServletRequest request) throws Exception;

}

