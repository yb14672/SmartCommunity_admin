package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "访客dto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorDto {
    @ApiModelProperty(hidden = true)
    List<VisitorListDto> visitorListDtos;
    @ApiModelProperty(hidden = true)
    Pageable pageable;
}
