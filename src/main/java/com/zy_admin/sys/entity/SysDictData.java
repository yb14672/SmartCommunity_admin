package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 字典数据表(SysDictData)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:34
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysDictData extends Model<SysDictData> {
    /**
     * 字典编码
     */
    private Long dictCode;
    /**
     * 字典排序
     */
    private Integer dictSort;
    /**
     * 字典标签
     */
    private String dictLabel;
    /**
     * 字典键值
     */
    private String dictValue;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;
    /**
     * 表格回显样式
     */
    private String listClass;
    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 获取主键值
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.dictCode;
    }
}

