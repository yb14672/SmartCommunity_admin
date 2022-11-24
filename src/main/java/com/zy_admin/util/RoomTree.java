
package com.zy_admin.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 房间树
 *
 * @author Administrator
 * @description: 房间树实体类
 * @author: xnylh
 * @createDate: 2022/11/22 0022 9:08
 * @date 2022/11/23
 */

@ApiModel(description = "房间树: 房间树实体类")
@Data
public class RoomTree {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;
    /**
     * 名字
     */
    @ApiModelProperty("名字")
    private String name;
    /**
     * 父id
     */
    @ApiModelProperty("父id")
    private String parentId;
    /**
     * 子节点
     */
    @ApiModelProperty("子节点")
    private List<RoomTree> childNode = new ArrayList<>();
}
