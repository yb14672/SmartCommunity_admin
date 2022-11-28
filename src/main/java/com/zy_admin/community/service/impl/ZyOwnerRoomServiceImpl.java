package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.dao.ZyOwnerRoomDao;
import com.zy_admin.community.dao.ZyOwnerRoomRecordDao;
import com.zy_admin.community.dao.ZyRoomDao;
import com.zy_admin.community.dto.ZyOwnerRoomDto;
import com.zy_admin.community.dto.ZyOwnerRoomDtoAll;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.community.service.ZyOwnerRoomService;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.RoomTree;
import com.zy_admin.util.SnowflakeManager;
import com.zy_admin.util.TreeData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * zy所有者impl客房服务
 * 房屋绑定表 (ZyOwnerRoom)表服务实现类
 *
 * @author makejava
 * @date 2022/11/22
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerRoomService")
public class ZyOwnerRoomServiceImpl extends ServiceImpl<ZyOwnerRoomDao, ZyOwnerRoom> implements ZyOwnerRoomService {

    /**
     * 系统用户刀
     */
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 业主房间记录道
     */
    @Resource
    private ZyOwnerRoomRecordDao zyOwnerRoomRecordDao;

    /**
     * 房屋
     */
    @Resource
    private ZyRoomDao zyRoomDao;

    /**
     * 雪花经理
     */
    @Resource
    private SnowflakeManager snowflakeManager;

    @Resource
    private ZyOwnerDao ownerDao;

    /**
     * 根据业主ID获取其房屋绑定列表
     *
     * @param ownerId 业主ID
     * @return 关联关系列表
     */
    @Override
    public Result getOwnerRoomByOwnerId(String ownerId) {
        Result result = new Result("加载失败，请稍后重试", ResultTool.fail(ResultCode.COMMON_FAIL));
        List<ZyOwnerRoom> ownerRoomByOwnerId = this.baseMapper.getOwnerRoomByOwnerId(ownerId);
        if(!ownerRoomByOwnerId.isEmpty()||ownerRoomByOwnerId.size()!=0){
            result.setData(ownerRoomByOwnerId);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result ownerInsert(ZyOwnerRoom ownerRoom) throws Exception {
        Result result = new Result("提交失败",ResultTool.fail(ResultCode.OWNER_ROOM_INSERT_FAIL));
        ZyOwnerRoom zyOwnerRoom = this.baseMapper.checkOwnerRoom(ownerRoom);
        if (zyOwnerRoom != null ){
            result.setData("提交失败");
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_OWNER_ROOM));
            return result;
        }
        ownerRoom.setOwnerRoomId(snowflakeManager.nextId()+"");
        ownerRoom.setCreateTime(LocalDateTime.now().toString());
        Integer i = this.baseMapper.insertOwnerRoom(ownerRoom);
        if (i == 1){
            result.setData("提交成功");
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result getTreeData() {
        Result result = new Result("获取失败！", ResultTool.fail(ResultCode.ROOMTREE_GET_FAIL));
        try {
            List<RoomTree> treeRoom = this.baseMapper.getTreeRoom();
            TreeData treeData = new TreeData(treeRoom);
            List<RoomTree> roomTrees = treeData.buildTree();
            result.setData(roomTrees);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }


    /**
     * 选择所有业主房间限制
     *
     * @param zyOwnerRoom 业主房间
     * @param pageable    分页对象
     * @return {@link Result}
     */
    @Override
    public Result selectAllOwnerRoomLimit(ZyOwnerRoom zyOwnerRoom, Pageable pageable) {
        //默认给失败的情况的状态
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //查询出来的总数量
        Long total = this.baseMapper.count(zyOwnerRoom);
        //默认的页面总数设为0
        long pages;
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
     *
     * 修改业主审核的状态
     *
     * @param zyOwnerRoom       业主房间
     * @param recordAuditOpinion 业主房间记录
     * @return {@link Result}
     * @throws Exception 异常
     */
    @Override
    public Result updateOwnerRoomStatus(ZyOwnerRoom zyOwnerRoom, String recordAuditOpinion) throws Exception {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        ZyOwnerRoomRecord zyOwnerRoomRecord=new ZyOwnerRoomRecord();
        zyOwnerRoomRecord.setRecordAuditOpinion(recordAuditOpinion);
        zyOwnerRoomRecord.setOwnerId(zyOwnerRoom.getOwnerId()+"");
        //id
        zyOwnerRoomRecord.setRecordId(snowflakeManager.nextId()+"");
        zyOwnerRoomRecord.setOwnerType("yz");
        zyOwnerRoomRecord.setOwnerRoomId(zyOwnerRoom.getOwnerRoomId());
        zyOwnerRoomRecord.setRoomStatus(zyOwnerRoom.getRoomStatus());
        zyOwnerRoomRecord.setUpdateTime(LocalDateTime.now().toString());
        zyOwnerRoomRecordDao.insert(zyOwnerRoomRecord);
        //修改时间
        zyOwnerRoom.setUpdateTime(LocalDateTime.now().toString());
        this.baseMapper.updateOwnerRoomStatus(zyOwnerRoom);
        //判断审核是不是通过
        if ("Binding".equals(zyOwnerRoom.getRoomStatus())){
            zyRoomDao.updateRoomStatus(zyOwnerRoom.getRoomId()+"");
        }
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

}

