package com.zy_admin.util;

import com.zy_admin.community.dto.ZyBuildingDto;
import com.zy_admin.community.entity.ZyUnit;

import java.util.List;

/**
 * @author admin
 */
public class BuildAndUnit {

    private List<ZyBuildingDto> buildingDtoList;

    private List<ZyUnit> unitList;

    public BuildAndUnit(List<ZyBuildingDto> buildingDtoList, List<ZyUnit> unitList) {
        this.buildingDtoList = buildingDtoList;
        this.unitList = unitList;
    }

    public List<ZyBuildingDto> test(List<ZyBuildingDto> buildingDtoList, List<ZyUnit> unitList){
        for (int i = 0; i < buildingDtoList.size() - 1; i++) {
            for (int j = 0; j < unitList.size() - 1; j++) {
                if (buildingDtoList.get(i).getBuildingId().equals(unitList.get(j).getBuildingId())){
                    buildingDtoList.get(i).getChildren().add(unitList.get(j));
                }
            }
        }
        return buildingDtoList;
    }
}
