package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyCommunity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyCommunityDto {
    private List<ZyCommunity> zyCommunityList;
    private Pageable pageable;
}
