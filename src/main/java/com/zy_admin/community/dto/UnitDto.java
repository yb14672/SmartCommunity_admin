package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 14208
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto {
    /**
     *
     */
    private List<UnitListDto> unitListDtos;
    /**
     *
     */
    private Pageable pageable;
}
