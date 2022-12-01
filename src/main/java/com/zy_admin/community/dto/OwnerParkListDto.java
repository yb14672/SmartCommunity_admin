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

}
