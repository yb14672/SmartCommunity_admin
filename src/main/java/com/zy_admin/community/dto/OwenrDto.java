package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * owenr dto
 *
 * @author 14208
 * @date 2022/11/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwenrDto {
    /**
     * 可分页
     */
    private Pageable pageable;
    /**
     * 所有者列表dto列表
     */
    private List<OwnerListDto> ownerListDtoList;
}
