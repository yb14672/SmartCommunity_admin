package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.community.dto.ZyComplaintSuggestDto;
import com.zy_admin.community.entity.ZyComplaintSuggest;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 投诉建议 (ZyComplaintSuggest)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
public interface ZyComplaintSuggestService extends IService<ZyComplaintSuggest> {

    /**
     * 根据id查投诉建议
     * @param suggestId id
     * @return
     */
    Result selectSuggestById(String suggestId);

    /**
     * 投诉建议和分页
     * @param zyComplaintSuggest 投诉建议
     * @param pageable 分页
     * @return
     */
    Result selectSuggestLimit(ZyComplaintSuggest zyComplaintSuggest, Pageable pageable);

    /**
     * 导出选中的id
     * @param suggestIds 选中的id集合
     * @return
     */
    List<ZyComplaintSuggestDto> querySuggestByIds(ArrayList<String> suggestIds);

    /**
     * 导出全部
     * @return
     */
    List<ZyComplaintSuggestDto> querySuggestAll();


    /**
     * 插入建议
     *
     * @param zyComplaintSuggest zy投诉建议
     * @return {@link Result}
     * @throws Exception 异常
     */
    Result insertSuggest(ZyComplaintSuggestDto zyComplaintSuggest) throws Exception;

    /**
     * 修改投诉建议
     * @param zyComplaintSuggest 投诉建议对象
     * @return
     */
    Result updateSuggest(ZyComplaintSuggest zyComplaintSuggest);

    /**
     * 回复投诉建议
     * @param zyComplaintSuggest 投诉建议对象
     * @return
     */
    Result updateSuggestByOwner(ZyComplaintSuggest zyComplaintSuggest);

    /**
     * 批量删除
     * @param idList id的集合
     * @return
     */
    Result deleteSuggestByIds(@Param("idList") List<String> idList);
}

