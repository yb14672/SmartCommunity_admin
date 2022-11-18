package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyCommunity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author fangqian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyCommunityDto {
    /**
     * 显示数据
     */
    private List<CommunityDto> zyCommunityList;
    /**
     * 分页条件
     */
    private Pageable pageable;

}
