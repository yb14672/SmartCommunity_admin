package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerRoomRecordDto extends ZyOwnerRoomRecord{
    //真实名字
    private String ownerRealName;
    private String ownerType;
    private String roomStatus;
    private String recordAuditOpinion;
    private Long createById;
    private String recordAuditType;
    private String remark;
}
