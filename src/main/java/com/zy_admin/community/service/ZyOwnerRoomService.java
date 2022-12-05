package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.entity.ZyOwnerRoom;

/**
 * 房屋绑定表 (ZyOwnerRoom)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerRoomService extends IService<ZyOwnerRoom> {
    /**
     * 根据业主ID获取其房屋绑定列表
     * @param ownerId 业主ID
     * @return 关联关系列表
     */
    Result getOwnerRoomByOwnerId(String ownerId);

    /**
     * 检查房间绑定是否重复提交
     *
     * @param ownerRoom 房间绑定信息
     * @return boolean
     */
    boolean checkOwnerRoom(ZyOwnerRoom ownerRoom);

    /**
     * 检查业主是否实名认证
     *
     * @param ownerId 所有者id
     * @return boolean
     */
    boolean checkOwnerIdCardExist(String ownerId);

    /**
     * 根据业主Id获取房屋审核记录
     * @param ownerId 业主Id
     * @return 房屋绑定信息
     */
    Result selectOwnerRoomByOwnerId(String ownerId);

    /**
     * 主人插入
     * 提交审核记录
     *
     * @param ownerRoom 审核信息
     * @return 提交结果
     * @throws Exception 异常
     */
    Result ownerInsert(ZyOwnerRoom ownerRoom) throws Exception;

    /**
     * 获取小区，楼栋，单元，房屋四级联动的树形结构
     * @return 树形结构
     */
    Result getTreeData();

    /**
     * 查询所有业主审核和分页
     *
     * @param zyOwnerRoom 业主房间关联
     * @param pageable    分页对象
     * @return {@link Result}
     */
    Result selectAllOwnerRoomLimit(ZyOwnerRoom zyOwnerRoom, Pageable pageable);

    /**
     * 更新主人房间状态
     * 修改业主审核的状态
     *
     * @param zyOwnerRoom        业主房间关联
     * @param recordAuditOpinion 记录审计意见
     * @return {@link Result}
     * @throws Exception 异常
     */
    Result updateOwnerRoomStatus(ZyOwnerRoom zyOwnerRoom, String recordAuditOpinion) throws Exception;

    /**
     * 获取绑定率
     *
     * @return 获取绑定率
     */
    Result getTheNumberOfHouseBindings();
}

