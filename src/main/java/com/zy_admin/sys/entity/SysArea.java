package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 区域表(SysArea)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysArea extends Model<SysArea> {
    /**
     * 唯一主键
     */
    private String id;
    /**
     * 城市编码
     */
    private String code;
    /**
     * 城市名称
     */
    private String name;
    /**
     * 城市父ID
     */
    private String parentid;
    /**
     * 城市等级(0:省,1:市,2:县)
     */
    private String level;
    /**
     * 功能类型(1:角色认证地点开通;0:未开通)
     */
    private String type;
    /**
     * 服务状态
     */
    private String servicestate;
    /**
     * 逻辑删除 0正常 1 删除
     */
    private String deleteFlag;
    /**
     * 地区范围
     */
    private String region;
    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

