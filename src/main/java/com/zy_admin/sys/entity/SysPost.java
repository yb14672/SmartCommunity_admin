package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 岗位信息表(SysPost)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPost extends Model<SysPost> {
    //岗位ID
    private Long postId;
    //岗位编码
    private String postCode;
    //岗位名称
    private String postName;
    //显示顺序
    private Integer postSort;
    //状态（0正常 1停用）
    private String status;
    //创建者
    private String createBy;
    //创建时间
    private String createTime;
    //更新者
    private String updateBy;
    //更新时间
    private String updateTime;
    //备注
    private String remark;


}

