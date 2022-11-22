package com.zy_admin.sys.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("MinIO对象")
public class MinIOtItem {
    /**
     * 对象名
     */
    @ApiModelProperty("对象名")
    private String objectName;
    /**
     * 大小
     */
    @ApiModelProperty("文件大小")
    private Long size;
}
