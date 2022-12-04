package com.zy_admin.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yb14672
 * Time:2022 - 2022/12/4 - 17:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNum {
    /**
     * 停用人数
     */
    private Integer lockNum=7;
    /**
     * 离线人数
     */
    private Integer offlineNum=53;
    /**
     * 在线人数
     */
    private Integer onlineNum=15;
    /**
     * 总人数
     */
    private Integer totalNum=75;
}
