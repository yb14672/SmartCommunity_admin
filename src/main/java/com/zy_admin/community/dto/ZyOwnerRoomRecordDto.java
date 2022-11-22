package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerRoomRecordDto extends ZyOwnerRoomRecord{
    /**
     * 主人真实姓名
     */
    private String ownerRealName;
    /**
     * 所有者类型
     */
    private String ownerType;
    /**
     * 房间状态
     */
    private String roomStatus;
    /**
     * 记录审计意见
     */
    private String recordAuditOpinion;
    /**
     * 创建id
     */
    private Long createById;
    /**
     * 记录审计类型
     */
    private String recordAuditType;
    /**
     * 备注
     */
    private String remark;
}
