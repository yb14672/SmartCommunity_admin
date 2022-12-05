package com.zy_admin.common.core.Result;

import com.zy_admin.community.dto.AreaInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用树
 *
 * @author lvwei
 * @date 2022/12/05
 */
@ApiModel(description = "通用树")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tree {
    /**
     * 名字
     */
    @ApiModelProperty("名字")
    private String name;
    /**
     * 值
     */
    @ApiModelProperty("值")
    private String value;
    /**
     * 子类
     */
    @ApiModelProperty("子类")
    private List<AreaInfo> children;
}
