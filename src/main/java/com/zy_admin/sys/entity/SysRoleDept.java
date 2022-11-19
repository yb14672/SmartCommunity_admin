package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色和部门关联表(SysRoleDept)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:41
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleDept extends Model<SysRoleDept> {
    //角色ID
    private Long roleId;
    //部门ID
    private Long deptId;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }
}

