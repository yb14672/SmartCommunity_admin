package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerParkDao;
import com.zy_admin.community.dto.ZyOwnerParkDto;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.entity.ZyOwnerParkRecord;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyOwnerParkService;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import com.zy_admin.util.StringUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 房屋绑定表 (ZyOwnerPark)表服务实现类
 *
 * @author makejava
 * @since 2022-12-01 15:50:35
 */
@Service("zyOwnerParkService")
public class ZyOwnerParkServiceImpl extends ServiceImpl<ZyOwnerParkDao, ZyOwnerPark> implements ZyOwnerParkService {
    @Resource
    private ZyOwnerParkDao zyOwnerParkDao;

    /**
     * 雪花算法
     */
    @Resource
    private SnowflakeManager snowflakeManager;

    /**
     * 提交车位审核以后审核状态改变
     *
     * @param zyOwnerPark 车位审核对象
     * @param recordAuditOpinion 车位审核意见
     * @return
     */
    @Override
    public Result updateOwnerParkStatus(ZyOwnerPark zyOwnerPark,String recordAuditOpinion) {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        ZyOwnerParkRecord zyOwnerParkRecord=new ZyOwnerParkRecord();
        zyOwnerParkRecord.setRecordAuditOpinion(recordAuditOpinion);
        zyOwnerParkRecord.setOwnerId(zyOwnerPark.getOwnerParkId());
        //id
//        zyOwnerParkRecord.setRecordId(snowflakeManager.nextId());
//        zyOwnerParkRecord.setOwnerType("yz");
//        zyOwnerParkRecord.setOwnerRoomId(zyOwnerRoom.getOwnerRoomId());
//        zyOwnerParkRecord.setRoomStatus(zyOwnerRoom.getRoomStatus());
//        zyOwnerParkRecord.setUpdateTime(LocalDateTime.now().toString());
//        zyOwnerRoomRecordDao.insert(zyOwnerRoomRecord);
//        //修改时间
//        zyOwnerRoom.setUpdateTime(LocalDateTime.now().toString());
//        this.baseMapper.updateOwnerRoomStatus(zyOwnerRoom);
//        //判断审核是不是通过
//        if ("Binding".equals(zyOwnerRoom.getRoomStatus())){
//            zyRoomDao.updateRoomStatus(zyOwnerRoom.getRoomId()+"");
//        }
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 查询所有的车位审核并分页
     *
     * @param zyOwnerParkDto 车位审核对象
     * @return
     */
    @Override
    public Result selectAllParkLimit(ZyOwnerParkDto zyOwnerParkDto, Page page) {
        //默认给失败
        Result result = new Result("没有符合条件的数据",ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            MPJLambdaWrapper<ZyOwnerPark> zyOwnerParkDtoMPJLambdaWrapper = new MPJLambdaWrapper<>();
            zyOwnerParkDtoMPJLambdaWrapper.selectAll(ZyOwnerPark.class)
                    .select(ZyCommunity::getCommunityName)
                    .select(ZyPark::getParkCode)
                    .select(ZyPark::getParkType)
                    .select(ZyPark::getParkIsPublic)
                    .select(ZyPark::getParkStatus)
                    .leftJoin(ZyPark.class,ZyPark::getParkId,ZyOwnerPark::getParkId)
                    .leftJoin(ZyCommunity.class,ZyCommunity::getCommunityId,ZyPark::getCommunityId)
                    .eq(StringUtil.isNotEmpty(zyOwnerParkDto.getParkOwnerStatus()),ZyOwnerPark::getParkOwnerStatus,zyOwnerParkDto.getParkOwnerStatus());
            IPage<ZyOwnerParkDto> zyOwnerParkDtoIPage = this.baseMapper.selectJoinPage(page, ZyOwnerParkDto.class, zyOwnerParkDtoMPJLambdaWrapper);
            if(zyOwnerParkDtoIPage.getTotal()!=0){
                result.setData(zyOwnerParkDtoIPage);
                //给一个信号
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("查询失败，请稍后再试");
        }
        return result;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param ownerParkId 主键
     * @return 实例对象
     */
    @Override
    public ZyOwnerPark queryById(Long ownerParkId) {
        return this.zyOwnerParkDao.queryById(ownerParkId);
    }

    /**
     * 分页查询
     *
     * @param zyOwnerPark 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<ZyOwnerPark> queryByPage(ZyOwnerPark zyOwnerPark, PageRequest pageRequest) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param zyOwnerPark 实例对象
     * @return 实例对象
     */
    @Override
    public ZyOwnerPark insert(ZyOwnerPark zyOwnerPark) {
        this.zyOwnerParkDao.insert(zyOwnerPark);
        return zyOwnerPark;
    }

    /**
     * 修改数据
     *
     * @param zyOwnerPark 实例对象
     * @return 实例对象
     */
    @Override
    public ZyOwnerPark update(ZyOwnerPark zyOwnerPark) {
        this.zyOwnerParkDao.update(zyOwnerPark);
        return this.queryById(zyOwnerPark.getOwnerParkId());
    }

    /**
     * 通过主键删除数据
     *
     * @param ownerParkId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long ownerParkId) {
        return this.zyOwnerParkDao.deleteById(ownerParkId) > 0;
    }
}
