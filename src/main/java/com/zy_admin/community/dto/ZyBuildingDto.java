package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZyBuildingDto extends ZyBuilding {
    /**
     * 小区名称
     */
    private String communityName;

    private ArrayList<ZyUnit> children;
}
