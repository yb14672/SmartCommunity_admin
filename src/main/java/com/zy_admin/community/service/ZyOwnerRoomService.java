package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.community.entity.ZyOwnerRoom;
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
     * 提交审核记录
     * @param ownerRoom 审核信息
     * @param request 请求
     * @return 提交结果
     */
    Result ownerInsert(ZyOwnerRoom ownerRoom, HttpServletRequest request) throws Exception;

    /**
     * 获取小区，楼栋，单元，房屋四级联动的树形结构
     * @return 树形结构
     */
    Result getTreeData();
}

