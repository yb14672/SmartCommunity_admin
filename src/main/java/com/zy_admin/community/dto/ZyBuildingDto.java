package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyBuildingDto extends ZyBuilding {
    /**
     * 子集
     */
    private ArrayList<ZyUnit> children;
}
