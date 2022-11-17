package com.zy_admin.community.dto;

import com.zy_admin.sys.entity.SysArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author fangqian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDto extends SysArea{
    /**
     * 子级菜单
     */
    private List<AreaDto> children;

}
