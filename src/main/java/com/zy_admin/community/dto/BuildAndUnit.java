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
        if(buildingList.size()==1){
            buildUnitDtoList.add(new BuildUnitDto(buildingList.get(0).getBuildingName(), buildingList.get(0).getBuildingId()));
            if(unitList.size()==1){
                if (buildUnitDtoList.get(0).getValue().equals(unitList.get(0).getBuildingId())){
                    buildUnitDtoList.get(0).getChildren().add(new BuildUnitDto(unitList.get(0).getUnitName(),unitList.get(0).getUnitId()));
                }
            }else{
                for (int i = 0; i < unitList.size() - 1; i++) {
                    ZyUnit zyUnit = unitList.get(i);
                    if (buildUnitDtoList.get(0).getValue().equals(zyUnit.getBuildingId())) {
                        buildUnitDtoList.get(0).getChildren().add(new BuildUnitDto(zyUnit.getUnitName(), zyUnit.getUnitId()));
                    }
                }
            }
        }else{
            for (int i = 0; i <= buildingList.size() - 1; i++) {
                ZyBuilding zyBuilding = buildingList.get(i);
                buildUnitDtoList.add(new BuildUnitDto(zyBuilding.getBuildingName(), zyBuilding.getBuildingId()));
                for (int j = 0; j <= unitList.size() - 1; j++) {
                    ZyUnit zyUnit = unitList.get(j);
                    if (zyBuilding.getBuildingId().equals(zyUnit.getBuildingId())) {
                        buildUnitDtoList.get(i).getChildren().add(new BuildUnitDto(zyUnit.getUnitName(), zyUnit.getUnitId()));
                    }
                }
            }
        }
        return buildUnitDtoList;
    }
}
