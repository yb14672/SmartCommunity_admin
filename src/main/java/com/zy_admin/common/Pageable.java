package com.zy_admin.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yb14672
 */
@Data
@ApiModel("分页对象")
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {
    /**
     * 页码号
     */
    @ApiModelProperty(value = "页码号",example = "1")
    private long pageNum = 1;
    /**
     * 每页数据量
     */
    private long pageSize = 2;
    /**
     * 索引
     */
    private long index;
    /**
     * 总数据量
     */
    private long total;
    /**
     * 总页数
     */
    private long pages;
}
