package com.zy_admin.community.dto;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyRepair;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 报修连表dto类
 * @author lvwei
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "报修连表dto类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairDto extends ZyRepair{
    /**
     * 业主id
     */
    @ApiModelProperty("业主id")
    @ExcelIgnore
    private String ownerId;
    /**
     * 业主姓名
     */
    @ApiModelProperty("业主姓名")
    @ExcelProperty("业主姓名")
    private String ownerRealName;
    /**
     * 业主电话
     */
    @ApiModelProperty("业主电话")
    @ExcelProperty("业主电话")
    private String ownerPhoneNumber;
}
