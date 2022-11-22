package com.zy_admin.sys.dto;
import com.zy_admin.common.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
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
    @Resource
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
    @Resource
    private Pageable pageable;
}