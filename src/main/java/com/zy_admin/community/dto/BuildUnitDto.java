package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yb14672
 * Time:2022/11/21 - 11:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildUnitDto extends ZyBuilding {
    /**
     * 对应的单元
     */
    private List<ZyUnit> children = new ArrayList<ZyUnit>();
}
