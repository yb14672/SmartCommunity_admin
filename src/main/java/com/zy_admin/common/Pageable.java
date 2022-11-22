package com.zy_admin.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yb14672
 */
@ApiModel(description = "分页对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {
    /**
     * 页码号
     */
    @ApiModelProperty("页码号")
    private long pageNum = 1;
    /**
     * 每页数据量
     */
    @ApiModelProperty("每页数据量")
    private long pageSize = 2;
    /**
     * 索引
     */
    @ApiModelProperty("索引")
    private long index;
    /**
     * 总数据量
     */
    @ApiModelProperty("总数据量")
    private long total;
    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private long pages;
}
