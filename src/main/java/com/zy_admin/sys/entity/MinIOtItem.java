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
    /**
     * 对象名
     */
    private String objectName;
    /**
     * 大小
     */
    private Long size;
}
