package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
public class BuildAndUnit {

    private List<ZyBuilding> buildingDtoList;

    private List<ZyUnit> unitList;

    public BuildAndUnit(List<ZyBuilding> buildingDtoList, List<ZyUnit> unitList) {
        this.buildingDtoList = buildingDtoList;
        this.unitList = unitList;
    }

    public List<BuildUnitDto> build() {
        List<BuildUnitDto> buildUnitDtoList = new ArrayList<>();
        for (int i = 0; i < buildingDtoList.size() - 1; i++) {
            ZyBuilding zyBuilding = buildingDtoList.get(i);
            buildUnitDtoList.add(new BuildUnitDto(zyBuilding.getBuildingName(), zyBuilding.getBuildingId()));
            for (int j = 0; j < unitList.size() - 1; j++) {
                ZyUnit zyUnit = unitList.get(j);
                if (zyBuilding.getBuildingId().equals(zyUnit.getBuildingId())) {
                    buildUnitDtoList.get(i).getChildren().add(new BuildUnitDto(zyUnit.getUnitName(), zyUnit.getUnitId()));
                }
            }
        }
        return buildUnitDtoList;
    }
}
