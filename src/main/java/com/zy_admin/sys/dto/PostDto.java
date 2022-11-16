package com.zy_admin.sys.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 部门Dto
 * @author Jason.ZHANG
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    /**
     * 岗位集合
     */
    List<SysPost> sysPostList;
    /**
     * 页码数
     */
    private Pageable pageable;


}
