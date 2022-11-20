package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyCommunity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto extends ZyCommunity {

    /**
     * 省名称
     */
    private String communityProvenceName;
    /**
     * 城市名称
     */
    private String communityCityName;
    /**
     * 区名称
     */
    private String communityTownName;
}
