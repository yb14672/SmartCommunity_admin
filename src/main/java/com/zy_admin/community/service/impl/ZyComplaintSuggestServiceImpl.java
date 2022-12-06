package com.zy_admin.community.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyComplaintSuggestDao;
import com.zy_admin.community.dao.ZyFilesDao;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.dao.ZyOwnerRoomDao;
import com.zy_admin.community.dto.OwnerRoomDto;
import com.zy_admin.community.dto.SuggestInMonth;
import com.zy_admin.community.dto.ZyComplaintSuggestDto;
import com.zy_admin.community.entity.ZyComplaintSuggest;
import com.zy_admin.community.entity.ZyFiles;
import com.zy_admin.community.service.ZyComplaintSuggestService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
    @Resource
    private ZyOwnerRoomDao zyOwnerRoomDao;


    /**
     * 根据id查投诉建议
     *
     * @param suggestId id
     * @return
     */
    @Override
    public Result selectSuggestById(String suggestId) {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        ZyComplaintSuggestDto zyComplaintSuggestDto = this.baseMapper.selectSuggestById(suggestId);
        result.setData(zyComplaintSuggestDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 投诉建议和分页
     *
     * @param zyComplaintSuggest 投诉建议
     * @param pageable           分页
     * @return
     */
    @Override
    public Result selectSuggestLimit(ZyComplaintSuggest zyComplaintSuggest, Pageable pageable) {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //总数量
        Long total = this.baseMapper.count(zyComplaintSuggest);
        long pages;
        if (total > 0) {
            //计算总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        //总数量
        pageable.setTotal(total);
        //根据id获取到的图片的地址
        List<ZyComplaintSuggestDto> zyComplaintSuggestList = this.baseMapper.selectSuggestLimit(zyComplaintSuggest, pageable);
        if (zyComplaintSuggestList.size() != 0) {
            for (int i = 0; i < zyComplaintSuggestList.size(); i++) {
                String id = zyComplaintSuggestList.get(i).getComplaintSuggestId();
                List<String> filesList = zyFilesDao.queryAllFileUrl(id, "ComplaintSuggest");
                zyComplaintSuggestList.get(i).setFilesUrl(filesList);
            }
        }
        Page<ZyComplaintSuggestDto> page = new Page<>(zyComplaintSuggestList, pageable);
        //存入数据
        result.setData(page);
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
     * 插入建议
     *
     * @param zyComplaintSuggest zy投诉建议
     * @return {@link Result}
     * @throws Exception 异常
     */
    @Override
    public Result insertSuggest(ZyComplaintSuggestDto zyComplaintSuggest) throws Exception {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        zyComplaintSuggest.setComplaintSuggestId(snowflakeManager.nextId() + "");
        //判断下面有没有房屋绑定
        List<OwnerRoomDto> ownerRoomByOwnerId = zyOwnerRoomDao.getOwnerRoomByOwnerId(zyComplaintSuggest.getUserId());
        if (ownerRoomByOwnerId == null) {
            result.setMeta(ResultTool.fail(ResultCode.OWNER_NOT_BOUND));
            result.setData("新增失败");
            return result;
        }
        try {
            //新增
            Integer i = this.baseMapper.insertSuggest(zyComplaintSuggest);
            if (i == 1) {
                List<String> urlList = zyComplaintSuggest.getFilesUrl();
                //如果有添加文件或者图片则添加
                if (urlList != null && !urlList.isEmpty()) {
                    List<ZyFiles> files = new ArrayList<>();
                    for (String url : urlList) {
                        if (ObjectUtil.isNotEmpty(url)) {
                            ZyFiles zyFile = new ZyFiles();
                            zyFile.setFilesUrl(url);
                            zyFile.setFilesId(snowflakeManager.nextId() + "");
                            zyFile.setCreateTime(LocalDateTime.now().toString());
                            zyFile.setCreateBy(zyComplaintSuggest.getCreateBy());
                            zyFile.setDelFlag(0);
                            zyFile.setSource(0);
                            zyFile.setRemark("ComplaintSuggest");
                            zyFile.setParentId(zyComplaintSuggest.getComplaintSuggestId());
                            zyFile.setUserId(zyComplaintSuggest.getUserId());
                            files.add(zyFile);
                        }
                    }
                    if (files != null && !files.isEmpty()) {
                        int j = this.zyFilesDao.insertBatch(files);
                        if (j < 1) {
                            return result;
                        }
                    }
                }
                result.setData("添加成功");
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    /**
     * 回复投诉建议
     *
     * @param zyComplaintSuggest 投诉建议对象
     * @return
     */
    @Override
    public Result updateSuggestByOwner(ZyComplaintSuggest zyComplaintSuggest) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断数据的值有没有改变 zyComplaintSuggest1是原来的对象
        ZyComplaintSuggest zyComplaintSuggest1 = this.baseMapper.queryById(zyComplaintSuggest.getComplaintSuggestId());
        String[] fields = new String[]{"reply"};
        if (!ObjUtil.checkEquals(zyComplaintSuggest1, zyComplaintSuggest, fields)) {
            try {
                zyComplaintSuggest.setUpdateTime(LocalDateTime.now().toString());
                int i = this.baseMapper.updateSuggest(zyComplaintSuggest);
                if (i == 1) {
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            result.setData("参数没有变化");
        }
        return result;
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
        String[] fields = new String[]{"complaintSuggestType", "complaintSuggestContent", "remark"};
        if (!ObjUtil.checkEquals(zyComplaintSuggest1, zyComplaintSuggest, fields)) {
            //判断下面有没有房屋绑定
            List<OwnerRoomDto> ownerRoomByOwnerId = zyOwnerRoomDao.getOwnerRoomByOwnerId(zyComplaintSuggest.getComplaintSuggestId());
            if (ownerRoomByOwnerId == null) {
                result.setData("未绑定房屋,不允许修改");
                result.setMeta(ResultTool.fail(ResultCode.OWNER_NOT_BOUND));
            } else {
                try {
                    zyComplaintSuggest.setUpdateTime(LocalDateTime.now().toString());
                    int i = this.baseMapper.updateSuggest(zyComplaintSuggest);
                    if (i == 1) {
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
                }
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            result.setData("参数没有变化");
        }
        return result;
    }

    /**
     * 批量删除
     *
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

    /**
     * 获取一个月内的投诉建议
     *
     * @param limitNum 总共显示多少条
     * @return 一个月内的投诉建议
     */
    @Override
    public Result getSuggestInMonth(String limitNum) {
        Result result = new Result("最近一个月没有新的投诉建议", ResultTool.fail(ResultCode.COMMON_FAIL));
        //获取七天前的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 30);
        String lastWeek = sdf.format(calendar.getTime());
        List<SuggestInMonth> suggestInMonthList = this.baseMapper.getSuggestInMonth(lastWeek, Integer.valueOf(limitNum));
        if (!suggestInMonthList.isEmpty()) {
            result.setData(suggestInMonthList);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }
}

