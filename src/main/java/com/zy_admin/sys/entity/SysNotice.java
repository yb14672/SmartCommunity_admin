package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通知公告表(SysNotice)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysNotice extends Model<SysNotice> {
    /**
     * 公告ID
     */
    private Integer noticeId;
    /**
     * 公告标题
     */
    private String noticeTitle;
    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;
    /**
     * 公告内容
     */
    private Object noticeContent;
    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.noticeId;
    }
}

