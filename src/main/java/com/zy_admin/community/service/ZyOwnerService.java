package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.OwnerRoomExcel;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.common.core.Result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 业主 (ZyOwner)表服务接口
 *
 * @author makejava
 * @date 2022/11/22
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerService extends IService<ZyOwner> {

    /**
     * 通过id获取业主
     *
     * @param ownerId 业主id
     * @return {@link Result}
     */
    Result getOwnerById(String ownerId);
    /**
     * 得到业主名单
     * 获取户主信息并分页
     *
     * @param zyOwner  户主信息
     * @param pageable 分页对象
     * @return {@link Result}
     */
    Result getOwnerList(ZyOwner zyOwner, Pageable pageable);

    /**
     * 解绑房间
     *
     * @param request    请求
     * @param owenRoomId 房主房间id
     * @return {@link Result}
     */
    Result deleteOwenRome(HttpServletRequest request, String owenRoomId);

    /**
     * 得到列表
     *
     * @return {@link List}<{@link OwnerRoomExcel}>
     */
    List<OwnerRoomExcel> getLists();

    /**
     * 查询所有者通过id
     *
     * @param userIds 用户id
     * @return {@link List}<{@link OwnerRoomExcel}>
     */
    List<OwnerRoomExcel>queryOwnerById(List<String> userIds);



}

