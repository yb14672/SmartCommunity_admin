package com.zy_admin.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页+数据
 *
 * @author yb14672
 * Time:2022/11/24 - 12:28
 */
@ApiModel(description = "分页+数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private List<T> records;
    /**
     * 分页对象
     */
    @ApiModelProperty("分页对象")
    private Pageable pageable;
}
