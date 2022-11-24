package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.ZyComplaintSuggestDto;
import com.zy_admin.community.entity.ZyComplaintSuggest;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 投诉建议 (ZyComplaintSuggest)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyComplaintSuggestDao extends BaseMapper<ZyComplaintSuggest> {

    /**
     * 查询投诉建议和分页
     * @param zyComplaintSuggest 投诉建议
     * @param pageable 分页
     * @return
     */
    List<ZyComplaintSuggestDto> selectSuggestLimit(@Param("zyComplaintSuggest") ZyComplaintSuggest zyComplaintSuggest, @Param("pageable")Pageable pageable);

    /**
     * 计算总数量
     * @param zyComplaintSuggest 投诉建议
     * @return
     */
    Long count(@Param("zyComplaintSuggest") ZyComplaintSuggest zyComplaintSuggest);

    /**
     * 选中的导出
     * @param suggestIds 选中的id
     * @return
     */
    List<ZyComplaintSuggestDto> querySuggestByIds(@Param("list")ArrayList<String> suggestIds);

    /**
     * 所有的导出
     * @return
     */
    List<ZyComplaintSuggestDto> querySuggestAll();

    /**
     * 新增投诉建议对象
     * @return
     */
    Integer insertSuggest(@Param("zyComplaintSuggest") ZyComplaintSuggest zyComplaintSuggest);

    /**
     * 修改投诉建议
     * @param complaintSuggestId 投诉建议id
     * @return
     */
    Integer updateSuggest(Integer complaintSuggestId);

    /**
     * 删除投诉建议通过id
     * @param complaintSuggestId 投诉建议id
     * @return
     */
    Integer deleteSuggestById(Integer complaintSuggestId);

    /**
     * 批量删除
     * @param idList id集合
     * @return
     */
    Integer deleteSuggestByIds(@Param("idList") List<Integer> idList);


}

