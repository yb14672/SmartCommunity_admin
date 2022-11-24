package com.zy_admin.common.core.Result;

import com.zy_admin.common.core.Result.JsonResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回到前端的结果集
 *
 * @author admin
 * @date 2022/11/22
 */
@ApiModel(description = "返回到前端的结果集")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private Object data;
    /**
     * 信号
     */
    @ApiModelProperty("信号")
    private JsonResult meta;
}
