package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.community.entity.ZyFiles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件管理(ZyFiles)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyFilesDao extends BaseMapper<ZyFiles> {
    /**
     * 根据id查询文件链接
     * @param id        图片id
     * @param remark    备注信息
     * @return          图片路径集合
     */
    List<ZyFiles> queryAllFileUrl(@Param("id") String id,@Param("remark") String remark);

    /**
     * 根据id查询文件信息
     * @param id        图片id
     * @param remark    备注信息
     * @return          图片路径集合
     */
    List<ZyFiles> queryAllFile(@Param("id") String id,@Param("remark") String remark);

    int insertBatch(List<ZyFiles> filesList);
}

