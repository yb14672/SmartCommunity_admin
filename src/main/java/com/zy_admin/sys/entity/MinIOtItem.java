package com.zy_admin.sys.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * minio 属性值
 * @author yb14672
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinIOtItem {
    private String objectName;
    private Long size;
}
