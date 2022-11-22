package com.zy_admin.sys.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysLogininfor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录日志分页搜索返回
 * @author fangqian
 */
@ApiModel(description = "登录日志分页搜索返回列表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInForDto  {
    /**
     * 显示数据
     */
    @ApiModelProperty("显示数据")
    private List<SysLogininfor> logininforList;
    /**
     * 分页条件
     */
    @ApiModelProperty("分页条件")
    private Pageable pageable;
    /**
     * 登陆时间
     */
    @ApiModelProperty("登陆时间")
    private String startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endTime;
}
