package com.zy_admin.sys.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvwei
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDto {
    /**
     * 角色集合
     */
    @Resource
    private List<SysRole> sysRole;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 页码
     */
    @Resource
    private Pageable pageable;
}