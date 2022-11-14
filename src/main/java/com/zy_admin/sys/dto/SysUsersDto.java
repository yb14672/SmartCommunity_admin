package com.zy_admin.sys.dto;

import com.zy_admin.common.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yb14672
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUsersDto {
    /**
     * 用户集合
     */
    @Autowired
    private List<SysUserDeptDto> sysUserDeptDto;
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
    @Autowired
    private Pageable pageable;
}