package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.dto.OwnerDto;
import com.zy_admin.community.dto.OwnerListDto;
import com.zy_admin.community.dto.OwnerRoomExcel;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.community.service.ZyOwnerService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 业主 (ZyOwner)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerService")
public class ZyOwnerServiceImpl extends ServiceImpl<ZyOwnerDao, ZyOwner> implements ZyOwnerService {

    @Resource
    SnowflakeManager snowflakeManager;

    @Resource
    private RequestUtil requestUtil;


    /**
     * 通过id获取业主
     *
     * @param ownerId 业主id
     * @return {@link Result}
     */
    @Override
    public Result getOwnerById(String ownerId) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        ZyOwner owner = this.baseMapper.selectById(ownerId);
        if (ObjUtil.isNotEmpty(owner)||"".equals(owner.getOwnerId())) {
            result.setData(owner);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 根据条件查询业主列表并分页
     *
     * @param zyOwner   户主信息
     * @param pageable  页码
     * @return 获取分页结果集
     */
    @Override
    public Result getOwnerList(ZyOwner zyOwner, Pageable pageable) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        Long total = this.baseMapper.count(zyOwner);
        long pages = 0;
        if (total > 0) {
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<OwnerListDto> ownerList = this.baseMapper.getOwnerList(zyOwner, pageable);
        OwnerDto ownerDto = new OwnerDto(pageable,ownerList);
        result.setData(ownerDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 解绑房间
     *
     * @param request    获取修改者信息
     * @param owenRoomId 绑定房间信息
     * @return 修改结果集
     */
    @Override
    public Result deleteOwenRome(HttpServletRequest request,String owenRoomId) {
        Result result = new Result();
        try {
            ZyOwnerRoomRecord zyOwnerRoom = this.baseMapper.getZyOwnerRoom(owenRoomId);
            //将解绑记录放入历史表中
            SysUser user = this.requestUtil.getUser(request);
            zyOwnerRoom.setRecordAuditOpinion("由"+user.getUserName()+"解绑");
            zyOwnerRoom.setUpdateBy(user.getUserName());
            zyOwnerRoom.setUpdateTime(LocalDateTime.now().toString());
            zyOwnerRoom.setRecordId(snowflakeManager.nextId()+"");
            this.baseMapper.updateIntoRoomRecord(zyOwnerRoom);
            //解绑
            this.baseMapper.deletOwnerRoomId(owenRoomId);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.FAIL_UNBIND_ROOM));
        }
        return result;
    }

    /**
     * 得到列表
     *
     * @return {@link List}<{@link OwnerRoomExcel}>
     */
    @Override
    public List<OwnerRoomExcel> getLists() {
        return this.baseMapper.getOwenLists();
    }

    /**
     * 查询所有房间信息者通过id
     *
     * @param ownerIds 房间Id集合
     * @return {@link List}<{@link OwnerRoomExcel}>
     */
    @Override
    public List<OwnerRoomExcel> queryOwnerById(List<String> ownerIds) {

        if (ownerIds != null) {
            ownerIds = ownerIds.size() == 0 ? null : ownerIds;
        }
        return this.baseMapper.queryOwnerById(ownerIds);
    }
}

