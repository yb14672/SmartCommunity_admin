package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyComplaintSuggestDao;
import com.zy_admin.community.dao.ZyFilesDao;
import com.zy_admin.community.dto.ZyComplaintSuggestDto;
import com.zy_admin.community.dto.ZyComplaintSuggestDtoAll;
import com.zy_admin.community.entity.ZyComplaintSuggest;
import com.zy_admin.community.service.ZyComplaintSuggestService;
import com.zy_admin.util.ResultTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 投诉建议 (ZyComplaintSuggest)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@Service("zyComplaintSuggestService")
public class ZyComplaintSuggestServiceImpl extends ServiceImpl<ZyComplaintSuggestDao, ZyComplaintSuggest> implements ZyComplaintSuggestService {

    @Resource
    private ZyFilesDao zyFilesDao;

    /**
     * 投诉建议和分页
     * @param zyComplaintSuggest 投诉建议
     * @param pageable           分页
     * @return
     */
    @Override
    public Result selectSuggestLimit(ZyComplaintSuggest zyComplaintSuggest, Pageable pageable) {
        //默认给失败
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //总数量
        Long total = this.baseMapper.count(zyComplaintSuggest);
        long pages;
        if (total > 0){
            //计算总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() +1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum()-1)*pageable.getPageSize());
        }else {
            pageable.setPageNum(0);
        }
        //总数量
        pageable.setTotal(total);
        //根据id获取到的图片的地址
        List<ZyComplaintSuggestDto> fileUrlList = zyFilesDao.queryAllFileUrl(zyComplaintSuggest.getComplaintSuggestId());
        System.out.println(fileUrlList);
        List<ZyComplaintSuggestDto> zyComplaintSuggestList = this.baseMapper.selectSuggestLimit(zyComplaintSuggest,pageable);
        System.out.println(zyComplaintSuggestList);
        //把图片的值给dto里,循环存进去
        for (int i=0;i<fileUrlList.size();i++){
            zyComplaintSuggestList.get(8).setFilesUrl(fileUrlList.get(i).getFilesUrl());
        }
        ZyComplaintSuggestDtoAll zyComplaintSuggestDtoAll = new ZyComplaintSuggestDtoAll(zyComplaintSuggestList, pageable);
        System.out.println(zyComplaintSuggestDtoAll);
        //存入数据
        result.setData(zyComplaintSuggestDtoAll);
        //返回信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 导出选中的id
     *
     * @param suggestIds 选中的id集合
     * @return
     */
    @Override
    public List<ZyComplaintSuggestDto> querySuggestByIds(ArrayList<String> suggestIds) {
        //如果有选中列表，就执行导出多个
        if (suggestIds != null) {
            suggestIds = suggestIds.size() == 0 ? null : suggestIds;
        }
        return baseMapper.querySuggestByIds(suggestIds);
    }

    /**
     * 导出全部
     *
     * @return
     */
    @Override
    public List<ZyComplaintSuggestDto> querySuggestAll() {
        return this.baseMapper.querySuggestAll();
    }

    /**
     * 新增投诉建议对象
     *
     * @param zyComplaintSuggest 投诉建议对象
     * @return
     */
    @Override
    public Result insertSuggest(ZyComplaintSuggest zyComplaintSuggest) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        Integer i = this.baseMapper.insertSuggest(zyComplaintSuggest);

        //判断重复
        return result;
    }

    /**
     * 修改投诉建议
     *
     * @param complaintSuggestId 投诉建议id
     * @return
     */
    @Override
    public Result updateSuggest(Integer complaintSuggestId) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断重复
        return result;
    }

    /**
     * 删除投诉建议通过id
     *
     * @param complaintSuggestId 投诉建议id
     * @return
     */
    @Override
    public Result deleteSuggestById(Integer complaintSuggestId) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        return result;
    }

    /**
     * 批量删除
     *
     * @param idList id的集合
     * @return
     */
    @Override
    public Result deleteSuggestByIds(List<Integer> idList) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        return result;
    }
}

