package com.zy_admin.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    //  返回状态码
    private int code;

    // 返回的信息提示
    private String message;

}
