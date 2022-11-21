package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyUnit;

import java.util.List;

/**
 * @author admin
 */
public class BuildAndUnit {

    private List<BuildUnitDto> buildingDtoList;

    private List<ZyUnit> unitList;

    public BuildAndUnit(List<BuildUnitDto> buildingDtoList, List<ZyUnit> unitList) {
        this.buildingDtoList = buildingDtoList;
        this.unitList = unitList;
    }

    public List<BuildUnitDto> build(List<BuildUnitDto> buildingDtoList, List<ZyUnit> unitList){
        for (int i = 0; i < buildingDtoList.size() - 1; i++) {
            for (int j = 0; j < unitList.size() - 1; j++) {
                ZyUnit zyUnit = unitList.get(j);
                if (buildingDtoList.get(i).getBuildingId().equals(zyUnit.getBuildingId())){
                    buildingDtoList.get(i).getChildren().add(zyUnit);
                }
            }
        }
        return buildingDtoList;
    }
}
