package com.zy_admin.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {
    //页码号
    private long pageNum = 1;
    //每页数据量
    private long pageSize = 2;
    //索引
    private long index;
    //总数据量
    private long total;
    //总页数
    private long pages;
}
