package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 投诉建议 (ZyComplaintSuggest)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyComplaintSuggest extends Model<ZyComplaintSuggest> {
    //id
    private Long complaintSuggestId;
    //小区id
    private Long communityId;
    //类型(投诉、建议)
    private String complaintSuggestType;
    //内容
    private String complaintSuggestContent;
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
    //投诉人ID
    private Long userId;

}

