package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyComplaintSuggestDao;
import com.zy_admin.community.dao.ZyFilesDao;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.dto.ZyComplaintSuggestDto;
import com.zy_admin.community.entity.ZyComplaintSuggest;
import com.zy_admin.community.entity.ZyFiles;
import com.zy_admin.community.service.ZyComplaintSuggestService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    @Resource
    private ZyOwnerDao zyOwnerDao;
    @Resource
    private SnowflakeManager snowflakeManager;

    /**
     * 根据id查投诉建议
     *
     * @param suggestId id
     * @return
     */
    @Override
    public Result selectSuggestById(String suggestId) {
        //默认给失败
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        ZyComplaintSuggestDto zyComplaintSuggestDto = this.baseMapper.selectSuggestById(suggestId);
        result.setData(zyComplaintSuggestDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

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
        List<ZyComplaintSuggestDto> zyComplaintSuggestList = this.baseMapper.selectSuggestLimit(zyComplaintSuggest,pageable);
        if (zyComplaintSuggestList.size()!=0){
            //把图片的值给dto里,循环存进去
//            for (ZyComplaintSuggestDto zyComplaintSuggestDto : zyComplaintSuggestList) {
//                //循环查图片，把所有图片渲染上去
//                List<String> list = zyFilesDao.queryAllFileUrl(zyComplaintSuggestDto.getComplaintSuggestId(), "ComplaintSuggest");
//                //把获取到的图片地址的list放入dto中
//                zyComplaintSuggestDto.setFilesUrl(list);
//            }

            for (int i = 0; i < zyComplaintSuggestList.size(); i++) {
                String id = zyComplaintSuggestList.get(i).getComplaintSuggestId();
                List<ZyFiles> filesList = zyFilesDao.queryAllFileUrl(id,"ComplaintSuggest");
                zyComplaintSuggestList.get(i).setFilesUrl(filesList);
            }
        }
        Page<ZyComplaintSuggestDto> page = new Page<>(zyComplaintSuggestList,pageable);
        //存入数据
        result.setData(page);
        System.out.println(page);
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
    public Result insertSuggest(ZyComplaintSuggest zyComplaintSuggest) throws Exception {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        zyComplaintSuggest.setComplaintSuggestId(snowflakeManager.nextId() + "");
        //判断是否重复x
        try {
                //新增
                Integer i = this.baseMapper.insertSuggest(zyComplaintSuggest);
                if (i == 1) {
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    result.setData("新增成功");
                }
                return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        }
    }

    /**
     * 修改投诉建议
     *
     * @param zyComplaintSuggest 投诉建议对象
     * @return
     */
    @Override
    public Result updateSuggest(ZyComplaintSuggest zyComplaintSuggest) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断数据的值有没有改变 zyComplaintSuggest1是原来的对象
        ZyComplaintSuggest zyComplaintSuggest1 = this.baseMapper.queryById(zyComplaintSuggest.getComplaintSuggestId());
        String[] fields = new String[]{"ComplaintSuggestContent", "complaintSuggestType", "complaintSuggestId"};
        if (!ObjUtil.checkEquals(zyComplaintSuggest,zyComplaintSuggest1,fields)){
            //默认给失败
            try {
                    //判断重复x
                    zyComplaintSuggest.setUpdateTime(LocalDateTime.now().toString());
                    int i = this.baseMapper.updateSuggest(zyComplaintSuggest);
                    if (i == 1) {
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    }
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            }
            return result;
        }else {
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            return result;
        }

    }

    /**
     * 批量删除
     * @param idList id的集合
     * @return
     */
    @Override
    public Result deleteSuggestByIds(List<String> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
            //判断是单个
            int i = this.baseMapper.deleteSuggestByIds(idList);
            if (idList.size() == 1) {
                result.setData("删除成功，影响的行数：" + i);
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                //多个就是批量删除
            } else {
                if (i >= 1) {
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.DELETE_FAIL));
                }
            }
        return result;
    }
}

