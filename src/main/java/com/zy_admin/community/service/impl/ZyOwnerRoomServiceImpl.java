package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerRoomDao;
import com.zy_admin.community.dao.ZyOwnerRoomRecordDao;
import com.zy_admin.community.dto.ZyOwnerRoomDto;
import com.zy_admin.community.dto.ZyOwnerRoomDtoAll;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.community.service.ZyOwnerRoomService;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerRoom)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerRoomService")
public class ZyOwnerRoomServiceImpl extends ServiceImpl<ZyOwnerRoomDao, ZyOwnerRoom> implements ZyOwnerRoomService {

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private ZyOwnerRoomRecordDao zyOwnerRoomRecordDao;

    @Autowired
    private SnowflakeManager snowflakeManager;

    /**
     * 查询所有业主审核的和分页
     *
     * @param zyOwnerRoom
     * @param pageable
     * @return
     */
    @Override
    public Result selectAllOwnerRoomLimit(ZyOwnerRoom zyOwnerRoom, Pageable pageable) {
        //默认给失败的情况的状态
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //查询出来的总数量
        Long total = this.baseMapper.count(zyOwnerRoom);
        //默认的页面总数设为0
        long pages = 0;
        if (total>0){
            pages = total % pageable.getPageSize() == 0 ? total/pageable.getPageSize() : total/pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum()<1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(pageable.getPageNum()>pages ? pages : pageable.getPageNum());
            //设置起始下标
            pageable.setIndex((pageable.getPageNum()-1)*pageable.getPageSize());
        }else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<ZyOwnerRoomDto> zyOwnerRoomList = this.baseMapper.selectAllOwnerRoomLimit(zyOwnerRoom, pageable);
        ZyOwnerRoomDtoAll zyOwnerRoomDtoAll = new ZyOwnerRoomDtoAll(zyOwnerRoomList, pageable);
        //集合存进去
        result.setData(zyOwnerRoomDtoAll);
        //给一个信号
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 修改业主审核的状态为绑定
     *
     * @param zyOwnerRoom
     * @return
     */
    @Override
    public Result updateOwnerRoomStatusBinding(@RequestBody ZyOwnerRoom zyOwnerRoom, ZyOwnerRoomRecord zyOwnerRoomRecord, HttpServletRequest request) throws Exception {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));

        zyOwnerRoomRecord.setRecordId(snowflakeManager.nextId());
        zyOwnerRoomRecord.setCreateTime(LocalDateTime.now().toString());
        String id = JwtUtil.getMemberIdByJwtToken(request);
        zyOwnerRoomRecord.setCreateBy(sysUserDao.getUserById(id).getUserName());
        zyOwnerRoomRecord.setOwnerType("yz");

        zyOwnerRoomRecordDao.insert(zyOwnerRoomRecord);
        //修改时间
        zyOwnerRoom.setUpdateTime(LocalDateTime.now().toString());
        this.baseMapper.updateOwnerRoomStatusBinding(zyOwnerRoom);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 修改业主审核的状态为审核失败
     *
     * @param zyOwnerRoom
     * @return
     */
    @Override
    public Result updateOwnerRoomStatusReject(ZyOwnerRoom zyOwnerRoom,ZyOwnerRoomRecord zyOwnerRoomRecord, HttpServletRequest request) throws Exception {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));

        zyOwnerRoomRecord.setRecordId(snowflakeManager.nextId());
        zyOwnerRoomRecord.setCreateTime(LocalDateTime.now().toString());
        String id = JwtUtil.getMemberIdByJwtToken(request);
        zyOwnerRoomRecord.setCreateBy(sysUserDao.getUserById(id).getUserName());
        zyOwnerRoomRecord.setOwnerType("zh");

        //修改时间
        zyOwnerRoom.setUpdateTime(LocalDateTime.now().toString());
        this.baseMapper.updateOwnerRoomStatusReject(zyOwnerRoom);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
}

