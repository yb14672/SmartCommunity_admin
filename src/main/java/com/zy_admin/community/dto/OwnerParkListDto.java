package com.zy_admin.community.dto;


import com.zy_admin.community.entity.ZyOwner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerParkListDto extends ZyOwner {
    private String communityName;
    private String parkCode;
    private String createTime;
    private String parkType;
    private String ownerParkId;
    private String parkId;
    private String communityId;
    private String recordId;
    private String parkBundingStatus;
    private String recordAuditOpinion;
    private String recordAuditType;

}
