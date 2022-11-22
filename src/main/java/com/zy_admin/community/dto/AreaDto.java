package com.zy_admin.community.dto;

import com.zy_admin.sys.entity.SysArea;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author fangqian
 */
@ApiModel(value = "地区树",description = "地区树状菜单")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDto extends SysArea{
    /**
     * 子级菜单
     */
    @ApiModelProperty("子级菜单")
    private List<AreaDto> children;

}
