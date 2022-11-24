package com.zy_admin.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 描述
 * @author: xnylh
 * @createDate: 2022/11/22 0022 9:08
 */
@Data
public class RoomTree {
    private String id;
    private String name;
    private String parentId;
    private List<RoomTree> childNode = new ArrayList<>();
}
