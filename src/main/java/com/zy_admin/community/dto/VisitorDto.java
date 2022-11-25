package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 访客dto
 *
 * @author 张友炜
 * @date 2022/11/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorDto {
    List<VisitorListDto> visitorListDtos;
    Pageable pageable;
}
