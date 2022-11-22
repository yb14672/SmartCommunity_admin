package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyBuildingDtoAll {
    /**
     * 获取楼栋集合
     */
    private List<ZyBuildingDto> zyBuildingList;
    /**
     * 获取分页对象
     */
    private Pageable pageable;
}
