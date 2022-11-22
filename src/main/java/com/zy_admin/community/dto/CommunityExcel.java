package com.zy_admin.community.dto;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 小区导出dto
 * @author fangqian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityExcel {
    /**
     * 小区id
     */
    @ExcelProperty("小区id")
    private String communityId;
    /**
     * 小区名称
     */
    @ExcelProperty("小区名称")
    private String communityName;
    /**
     * 小区编码
     */
    @ExcelProperty("小区编码")
    private String communityCode;
    /**
     * 省区
     */
    @ExcelProperty("省区")
    private String communityProvenceName;
    /**
     * 市区
     */
    @ExcelProperty("市区")
    private String communityCityName;
    /**
     * 区县区
     */
    @ExcelProperty("区县区")
    private String communityTownName;
    /**
     * 详细地址
     */
    @ExcelProperty("详细地址")
    private String communityDetailedAddress;
    /**
     * 经度
     */
    @ExcelProperty("经度")
    private String communityLongitude;
    /**
     * 纬度
     */
    @ExcelProperty("纬度")
    private String communityLatitude;
    /**
     * 物业id
     */
    @ExcelProperty("物业id")
    private Long deptId;
    /**
     * 排序
     */
    @ExcelProperty("排序")
    private Integer communitySort;
    /**
     * 创建者
     */
    @ExcelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private String createTime;
    /**
     * 更新者
     */
    @ExcelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    private String updateTime;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;
}
