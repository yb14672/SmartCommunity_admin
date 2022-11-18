package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyBuilding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZyBuildingDto extends ZyBuilding {
    /**
     * 小区名称
     */
    private String communityName;
}
