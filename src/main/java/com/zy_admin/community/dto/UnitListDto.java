package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 14208
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitListDto extends ZyUnit {
    /**
     * 小区名
     */
    private String communityName;
    /**
     * 楼栋名
     */
    private String buildingName;

}
