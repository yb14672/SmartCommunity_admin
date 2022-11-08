package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto extends SysRole {

    private String endTime;
}
