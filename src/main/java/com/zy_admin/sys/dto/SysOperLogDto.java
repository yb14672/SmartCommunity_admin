package com.zy_admin.sys.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysOperLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 日志操作DTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOperLogDto {
    /**
     * 获取操作日志对象
     */
    private List<SysOperLog> sysOperLogs;
    /**
     * 分页对象
     */
    private Pageable pageable;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 获取按列排序对象
     */
    private String orderByColumn;
    /**
     * 获取顺序排列对象
     */
    private String isAsc;

}
