package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerRoomDtoAll {
    private List<ZyOwnerRoomDto> zyOwnerRoomDtoList;
    private Pageable pageable;
}
