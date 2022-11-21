package com.zy_admin.sys.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yb14672
 * Time:2022/11/10 - 15:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataDictExcelDto extends Model<DataDictExcelDto> {
    /**
     * 字典编码
     */
    @ExcelProperty("字典编码")
    private Long dictCode;

    /**
     * 字典排序
     */
    @ExcelProperty("字典排序")
    private Integer dictSort;

    /**
     * 字典标签
     */
    @ExcelProperty("字典标签")
    private String dictLabel;

    /**
     * 字典键值
     */
    @ExcelProperty("字典键值")
    private String dictValue;

    /**
     * 字典类型
     */
    @ExcelProperty("字典类型")
    private String dictType;

    /**
     * 字典名称
     */
    @ExcelProperty("字典名称")
    private String dictName;

    /**
     * 样式属性（其他样式扩展）
     */
    @ExcelProperty("样式属性（其他样式扩展）")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @ExcelProperty("表格回显样式")
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    @ExcelProperty("是否默认（Y是 N否）")
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty("状态（0正常 1停用）")
    private String status;

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

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.dictCode;
    }
}
