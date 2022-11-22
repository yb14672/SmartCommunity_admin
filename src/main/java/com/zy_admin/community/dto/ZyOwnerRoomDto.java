package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyOwnerRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerRoomDto extends ZyOwnerRoom {
    //小区名字
    private String communityName;
    //楼栋名字
    private String buildingName;
    //单元名字
    private String unitName;
    //房间名字
    private String roomName;
    //业主真实名字
    private String ownerRealName;
    //业主类型
    private String ownerType;
    //绑定状态
    private String roomStatus;

}
