package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerParkDao;
import com.zy_admin.community.dao.ZyOwnerParkRecordDao;
import com.zy_admin.community.dao.ZyParkDao;
import com.zy_admin.community.dto.ZyOwnerParkDto;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.entity.ZyOwnerParkRecord;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyOwnerParkService;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import com.zy_admin.util.StringUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerPark)表服务实现类
 *
 * @author makejava
 * @since 2022-12-01 15:50:35
 */
@Service("zyOwnerParkService")
public class ZyOwnerParkServiceImpl extends ServiceImpl<ZyOwnerParkDao, ZyOwnerPark> implements ZyOwnerParkService {
    /**
     * 审核表
     */
    @Resource
    private ZyOwnerParkDao zyOwnerParkDao;

    /**
     * 车位表
     */
    @Resource
    private ZyParkDao zyParkDao;

    /**
     * 审核记录
     */
    @Resource
    private ZyOwnerParkRecordDao zyOwnerParkRecordDao;

    /**
     * 雪花算法
     */
    @Resource
    private SnowflakeManager snowflakeManager;

    /**
     * 批量删除
     *
     * @param idList id的集合
     * @return
     */
    @Override
    public Result deleteOwnerParkByIds(List<String> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是单个
        int i = this.baseMapper.deleteOwnerParkByIds(idList);
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
     * 修改数据
     *
     * @param zyOwnerPark 实例对象
     * @return 实例对象
     */
    @Override
    public Result updateOwnerPark(ZyOwnerPark zyOwnerPark) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断数据的值有没有改变 zyOwnerPark1是原来的对象
        ZyOwnerPark zyOwnerPark1 = this.baseMapper.queryById(zyOwnerPark.getOwnerParkId());
        System.out.println(zyOwnerPark1);
        System.out.println(zyOwnerPark);
        String[] fields = new String[]{"parkOwnerStatus","remark","ownerId","parkId","ownerParkId"};
        if(!ObjUtil.checkEquals(zyOwnerPark1,zyOwnerPark,fields)){
                try {
                    zyOwnerPark.setUpdateTime(LocalDateTime.now().toString());
                    int i = this.baseMapper.updateOwnerPark(zyOwnerPark);
                    if (i == 1) {
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
                }
        }else {
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            result.setData("参数没有变化");
        }
        return result;
    }

    /**
     * 新增车位审核
     *
     * @param zyOwnerPark 车位审核对象
     * @return 车位审核的条数
     */
    @Override
    public Result insertOwnerPark(ZyOwnerPark zyOwnerPark) throws Exception {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //雪花算法拿到OwnerParkId
        zyOwnerPark.setOwnerParkId(snowflakeManager.nextId() + "");
        try {
            //新增
            Integer i = this.baseMapper.insert(zyOwnerPark);
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
     * 提交车位审核以后审核状态改变
     *
     * @param zyOwnerPark 车位审核对象
     * @param recordAuditOpinion 车位审核意见
     * @return
     */
    @Override
    public Result updateOwnerParkStatus(ZyOwnerPark zyOwnerPark,String recordAuditOpinion) throws Exception {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //新增车位审核记录
        ZyOwnerParkRecord zyOwnerParkRecord=new ZyOwnerParkRecord();
        //审核记录的id
        zyOwnerParkRecord.setRecordId(snowflakeManager.nextId()+"");
        //车位id x
        zyOwnerParkRecord.setOwnerParkId(zyOwnerPark.getOwnerParkId()+"");
        //小区id 先根据parkId找到zyPark，再拿小区id
        ZyPark zyPark = zyParkDao.queryById(zyOwnerPark.getParkId());
        zyOwnerParkRecord.setCommunityId(zyPark.getCommunityId()+"");
        //业主id
        zyOwnerParkRecord.setOwnerId(zyOwnerPark.getOwnerId());
        //业主id
        zyOwnerParkRecord.setOwnerParkId(zyOwnerPark.getOwnerParkId()+"");
        //绑定状态
        zyOwnerParkRecord.setParkBundingStatus(zyOwnerPark.getParkOwnerStatus());
        //审核意见
        zyOwnerParkRecord.setRecordAuditOpinion(recordAuditOpinion);
        //审核人类型
        zyOwnerParkRecord.setRecordAuditType("Web");
       //创建时间
        zyOwnerParkRecord.setUpdateTime(LocalDateTime.now().toString());
        zyOwnerParkRecordDao.insert(zyOwnerParkRecord);
        //修改时间
        zyOwnerPark.setUpdateTime(LocalDateTime.now().toString());
        System.out.println(zyOwnerPark);
        this.baseMapper.updateOwnerParkStatus(zyOwnerPark);
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
    public ZyOwnerPark queryById(String ownerParkId) {
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
