package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAndRoleMenu {
    SysRole sysRole;
    List<Integer> menuIdList;


}
