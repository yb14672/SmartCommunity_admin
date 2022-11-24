package com.zy_admin.sys.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysDictType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yb14672
 * Time:2022 - 2022/11/9 - 22:54
 **/
@ApiModel(description = "字典类型列表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDictDto {
    /**
     * 获取字典类型集合
     */
    @ApiModelProperty("获取字典类型集合")
    private List<SysDictType> sysDictTypeList;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endTime;
    /**
     * 分页对象
     */
    @ApiModelProperty("分页对象")
    private Pageable pageable;
}
