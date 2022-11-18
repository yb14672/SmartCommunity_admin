package entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 文件管理(ZyFiles)实体类
 *
 * @author makejava
 * @since 2022-11-17 09:22:38
 */
public class ZyFiles implements Serializable {
    private static final long serialVersionUID = -18545829994313962L;
    /**
     * 文件ID
     */
    private Long filesId;
    /**
     * 文件地址
     */
    private String filesUrl;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建人者
     */
    private String createBy;
    /**
     * 删除状态0默认1删除
     */
    private Integer delFlag;
    /**
     * 来源0APP端，1PC端
     */
    private Integer source;
    /**
     * 备注
     */
    private String remark;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 创建人ID
     */
    private Long userId;


    public Long getFilesId() {
        return filesId;
    }

    public void setFilesId(Long filesId) {
        this.filesId = filesId;
    }

    public String getFilesUrl() {
        return filesUrl;
    }

    public void setFilesUrl(String filesUrl) {
        this.filesUrl = filesUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}

