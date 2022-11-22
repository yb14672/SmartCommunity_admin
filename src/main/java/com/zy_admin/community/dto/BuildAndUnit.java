package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
public class BuildAndUnit {

    private List<ZyBuilding> buildingList;

    private List<ZyUnit> unitList;

    public BuildAndUnit(List<ZyBuilding> buildingList, List<ZyUnit> unitList) {
        this.buildingList = buildingList;
        this.unitList = unitList;
    }

    public List<BuildUnitDto> build() {
        List<BuildUnitDto> buildUnitDtoList = new ArrayList<>();
        for (int i = 0; i < buildingList.size(); i++) {
            ZyBuilding zyBuilding = buildingList.get(i);
            buildUnitDtoList.add(new BuildUnitDto(zyBuilding.getBuildingName(), zyBuilding.getBuildingId()));
            for (int j = 0; j < unitList.size(); j++) {
                ZyUnit zyUnit = unitList.get(j);
                if (zyBuilding.getBuildingId().equals(zyUnit.getBuildingId())) {
                    buildUnitDtoList.get(i).getChildren().add(new BuildUnitDto(zyUnit.getUnitName(), zyUnit.getUnitId()));
                }
            }
        }
        return buildUnitDtoList;
    }
}
