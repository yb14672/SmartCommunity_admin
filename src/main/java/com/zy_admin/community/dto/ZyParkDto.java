package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zy_admin.community.entity.ZyPark;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 车位dto
 *
 * @author yb14672
 * Time:2022/12/2 - 16:39
 */
@ApiModel(description = "车位dto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZyParkDto extends ZyPark {
    /**
     * 小区id
     */
    @ApiModelProperty("小区id")
    private String communityId;
    /**
     * 小区名字
     */
    @ExcelProperty("小区名字")
    @ApiModelProperty("小区名字")
    private String communityName;
}
