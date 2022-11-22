package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.util.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 业主 (ZyOwner)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerService extends IService<ZyOwner> {
    /**
     * 获取户主信息并分页
     * @param zyOwner 户主信息
     * @param pageable 页码
     * @return
     */
     Result getOwnerList(ZyOwner zyOwner, Pageable pageable);

    Result deleteOwenRome(HttpServletRequest request, String owenRoomId);

}

