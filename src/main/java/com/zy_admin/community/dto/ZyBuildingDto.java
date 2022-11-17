package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyBuilding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyBuildingDto {
    private List<ZyBuilding> zyBuildingList;
    private Pageable pageable;
}
