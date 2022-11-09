package com.zy_admin.sys.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysDictType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yb14672
 * Time:2022 - 2022/11/9 - 22:54
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDictDto {
    private List<SysDictType> sysDictTypeList;
    private String startTime;
    private String endTime;
    private Pageable pageable;
}