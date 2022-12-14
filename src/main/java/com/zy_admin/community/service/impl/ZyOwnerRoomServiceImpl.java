package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.dao.ZyOwnerRoomDao;
import com.zy_admin.community.dao.ZyOwnerRoomRecordDao;
import com.zy_admin.community.dao.ZyRoomDao;
import com.zy_admin.community.dto.*;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.community.service.ZyOwnerRoomService;
import com.zy_admin.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerRoom)表服务实现类
 *
 * @author makejava
 * @date 2022/11/22
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerRoomService")
public class ZyOwnerRoomServiceImpl extends ServiceImpl<ZyOwnerRoomDao, ZyOwnerRoom> implements ZyOwnerRoomService {


    /**
     * 业主房间记录
     */
    @Resource
    private ZyOwnerRoomRecordDao zyOwnerRoomRecordDao;

    /**
     * 房屋持久层
     */
    @Resource
    private ZyRoomDao zyRoomDao;

    /**
     * 雪花算法
     */
    @Resource
    private SnowflakeManager snowflakeManager;

    /**
     * 业主数据持久层
     */
    @Resource
    private ZyOwnerDao zyOwnerDao;

    @Override
    public boolean checkOwnerRoom(ZyOwnerRoom ownerRoom) {
        //检查该房屋是否已经提交审核
        ZyOwnerRoom zyOwnerRoom = this.baseMapper.checkOwnerRoom(ownerRoom);
        if (zyOwnerRoom == null){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkOwnerIdCardExist(String ownerId) {
        return zyOwnerDao.selectById(ownerId).getOwnerIdCard() == null;
    }

    @Override
    public Result selectOwnerRoomByOwnerId(String ownerId) {
        Result result = new Result("获取失败",ResultTool.fail(ResultCode.OWNER_ROOM_GET_FAIL));
        try {
            List<OwnerRoomDto> ownerRoomDtos = this.baseMapper.selectOwnerRoomByOwnerId(ownerId);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            result.setData(ownerRoomDtos);
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    /**
     * 根据业主ID获取其房屋绑定列表
     *
     * @param ownerId 业主ID
     * @return 关联关系列表
     */
    @Override
    public Result getOwnerRoomByOwnerId(String ownerId) {
        Result result = new Result("加载失败，请稍后重试", ResultTool.fail(ResultCode.COMMON_FAIL));
        List<OwnerRoomDto> ownerRoomByOwnerId = this.baseMapper.getOwnerRoomByOwnerId(ownerId);
        if(!ownerRoomByOwnerId.isEmpty()||ownerRoomByOwnerId.size()!=0){
            result.setData(ownerRoomByOwnerId);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }

        return result;
    }

    /**
     * 提交房屋绑定
     *
     * @param ownerRoom 老板房间
     * @return {@link Result}
     * @throws Exception 异常
     */
    @Override
    public Result ownerInsert(ZyOwnerRoom ownerRoom) throws Exception {
        Result result = new Result("提交失败",ResultTool.fail(ResultCode.OWNER_ROOM_INSERT_FAIL));
        //检查是否实名认证
        if (checkOwnerIdCardExist(ownerRoom.getOwnerId())){
            result.setMeta(ResultTool.fail(ResultCode.OWNER_ID_CARD_NOT_CERTIFICATION));
            return result;
        }
        if (!checkOwnerRoom(ownerRoom)){
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_OWNER_ROOM));
            return result;
        }
        ownerRoom.setOwnerRoomId(snowflakeManager.nextId()+"");
        ownerRoom.setRoomStatus("Auditing");
        ownerRoom.setCreateTime(LocalDateTime.now().toString());
        Integer i = this.baseMapper.insertOwnerRoom(ownerRoom);
        if (i == 1){
            result.setData("提交成功");
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }


    /**
     * 得到未绑定房屋数据
     *
     * @return {@link Result}
     */
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
        zyOwnerRoomRecord.setCommunityId(zyOwnerRoom.getCommunityId());
        zyOwnerRoomRecord.setBuildingId(zyOwnerRoom.getBuildingId());
        zyOwnerRoomRecord.setUnitId(zyOwnerRoom.getUnitId());
        zyOwnerRoomRecord.setRoomId(zyOwnerRoom.getRoomId());
        zyOwnerRoomRecord.setOwnerRoomId(zyOwnerRoom.getOwnerRoomId());
        zyOwnerRoomRecord.setRoomStatus(zyOwnerRoom.getRoomStatus());
        zyOwnerRoomRecord.setUpdateBy(zyOwnerRoom.getUpdateBy());
        zyOwnerRoomRecord.setUpdateTime(LocalDateTime.now().toString());
        zyOwnerRoomRecord.setCreateBy(zyOwnerRoom.getCreateBy());
        zyOwnerRoomRecord.setCreateTime(zyOwnerRoom.getCreateTime());
        zyOwnerRoomRecordDao.insert(zyOwnerRoomRecord);
        //修改时间
        zyOwnerRoom.setUpdateTime(LocalDateTime.now().toString());
        zyOwnerRoom.setRemark(recordAuditOpinion);
        //先把所有申请该房屋的全部设为拒绝
        this.baseMapper.changeStatusReject(zyOwnerRoom.getRoomId());
        this.baseMapper.updateOwnerRoomStatus(zyOwnerRoom);
        //判断审核是不是通过
        if ("Binding".equals(zyOwnerRoom.getRoomStatus())) {
            zyRoomDao.updateRoomStatus(zyOwnerRoom.getRoomId() + "");
        }
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 获取绑定率
     *
     * @return 获取绑定率
     */
    @Override
    public Result getTheNumberOfHouseBindings() {
        Result result = new Result("获取失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        List<RoomInfo> roomInfoList = this.baseMapper.getTheNumberOfHouseBindings();
        if (!roomInfoList.isEmpty()) {
            RoomInfoDto roomInfoDto = new RoomInfoDto();
            for (int i = 0; i < roomInfoList.size(); i++) {
                RoomInfo roomInfo = roomInfoList.get(i);
                //获取当前小区的房屋绑定率
                Double rate = roomInfo.getBingingNum() != 0 ? roomInfo.getBingingNum() / roomInfo.getRoomNum() : 0;
                //将绑定率保留两位小数
                String str = String.format("%.2f", rate * 100);
                Double rateFormat = Double.parseDouble(str);
                roomInfoDto.getBindingRate().add(rateFormat);
                roomInfoDto.getRoomNum().add(roomInfo.getRoomNum());
                roomInfoDto.getBingingNum().add(roomInfo.getBingingNum());
                roomInfoDto.getCommunityName().add(roomInfo.getCommunityName());
            }
            result.setData(roomInfoDto);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

}

