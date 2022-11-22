package com.zy_admin.sys.dto;
import com.zy_admin.common.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import java.util.List;
/**
 * @author yb14672
 */
@ApiModel(description = "用户列表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUsersDto {
    /**
     * 用户集合
     */
    @ApiModelProperty("用户集合")
    @Resource
    private List<SysUserDeptDto> sysUserDeptDto;
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
     * 页码
     */
    @ApiModelProperty("页码")
    @Resource
    private Pageable pageable;
}