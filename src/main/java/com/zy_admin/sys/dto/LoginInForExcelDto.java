package com.zy_admin.sys.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录日志导出Excel模板
 * @author fangqian
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "登录日志导出Excel模板")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInForExcelDto extends Model<LoginInForExcelDto> {
    /**
     * 日志编码
     */
    @ApiModelProperty("日志编码")
    @ExcelProperty("日志编码")
    private Long infoId;
    /**
     * 登录账户
     */
    @ApiModelProperty("登录账户")
    @ExcelProperty("登录账户")
    private String userName;
    /**
     * 登录IP
     */
    @ApiModelProperty("登录IP")
    @ExcelProperty("登录IP")
    private String ipaddr;
    /**
     * 登录地点
     */
    @ApiModelProperty("登录地点")
    @ExcelProperty("登录地点")
    private String loginLocation;
    /**
     * 浏览器类型
     */
    @ApiModelProperty("浏览器类型")
    @ExcelProperty("浏览器类型")
    private String browser;
    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    @ExcelProperty("操作系统")
    private String os;
    /**
     * 登录状态（0成功 1失败）
     */
    @ApiModelProperty("登录状态（0成功 1失败）")
    @ExcelProperty("登录状态（0成功 1失败）")
    private String status;
    /**
     * 提示消息
     */
    @ApiModelProperty("提示消息")
    @ExcelProperty("提示消息")
    private String msg;
    /**
     * 访问时间
     */
    @ApiModelProperty("访问时间")
    @ExcelProperty("访问时间")
    private String loginTime;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.infoId;
    }
}
