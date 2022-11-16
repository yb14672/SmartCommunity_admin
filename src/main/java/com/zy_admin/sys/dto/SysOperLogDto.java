package com.zy_admin.sys.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysOperLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOperLogDto  {
    private List<SysOperLog> sysOperLogs;
    private Pageable pageable;
    private String startTime;
    private String endTime;
    private String orderByColumn;
    private String isAsc;

}
