package com.zy_admin.community.dto;

import com.zy_admin.util.RoomTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fangqian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRoomDto {
    /**
     * 小区Id
     */
    private String communityId;
    /**
     * 小区名称
     */
    private String communityName;
    /**
     * 楼栋id
     */
    private String buildingId;
    /**
     * 楼栋名称
     */
    private String buildingName;
    /**
     * 单元Id
     */
    private String unitId;
    /**
     * 单元名称
     */
    private String unitName;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 房间号
     */
    private String roomName;
    /**
     * 审核状态
     */
    private String roomStatus;
    /**
     * 绑定时间
     */
    private String createTime;
}
