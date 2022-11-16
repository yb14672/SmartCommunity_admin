package com.zy_admin.sys.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysLogininfor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录日志分页搜索返回
 * @author fangqian
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginInForDto  {
    /**
     * 显示数据
     */
    private List<SysLogininfor> logininforList;
    /**
     * 分页条件
     */
    private Pageable pageable;
    /**
     * 登陆时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
