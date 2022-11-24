package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyOwnerRoom;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业主房间dto
 *
 * @author yb14672
 * @date 2022/11/22
 */
@ApiModel(description = "业主房间dto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerRoomDto extends ZyOwnerRoom {
    /**
     * 社区名字
     */
    @ApiModelProperty("社区名字")
    private String communityName;
    /**
     * 建筑名字
     */
    @ApiModelProperty("建筑名字")
    private String buildingName;
    /**
     * 单位名字
     */
    @ApiModelProperty("单位名字")
    private String unitName;
    /**
     * 房间名字
     */
    @ApiModelProperty("房间名字")
    private String roomName;
    /**
     * 业主真实名字
     */
    @ApiModelProperty("业主真实名字")
    private String ownerRealName;
    /**
     * 业主类型
     */
    @ApiModelProperty("业主类型")
    private String ownerType;
    /**
     * 绑定状态
     */
    @ApiModelProperty("绑定状态")
    private String roomStatus;

}
