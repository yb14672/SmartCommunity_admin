package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerParkDao;
import com.zy_admin.community.dao.ZyOwnerParkRecordDao;
import com.zy_admin.community.dao.ZyParkDao;
import com.zy_admin.community.dto.OwnerParkListDto;
import com.zy_admin.community.dto.ZyOwnerParkDto;
import com.zy_admin.community.entity.*;
import com.zy_admin.community.service.ZyOwnerParkService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * impl zy所有者公园服务
 * 房屋绑定表 (ZyOwnerPark)表服务实现类
 *
 * @author makejava
 * @date 2022/12/02
 * @since 2022-12-01 15:18:41
 */
@Service("zyOwnerParkService")
public class ZyOwnerParkServiceImpl extends ServiceImpl<ZyOwnerParkDao, ZyOwnerPark> implements ZyOwnerParkService {
    /**
     * zy所有者公园道
     */
    @Resource
    private ZyOwnerParkDao zyOwnerParkDao;

    /**
     * zy公园道
     */
    @Resource
    private ZyParkDao zyParkDao;

    /**
     * zy所有者公园记录道
     */
    @Resource
    private ZyOwnerParkRecordDao zyOwnerParkRecordDao;
    /**
     * 请求跑龙套
     */
    @Resource
    private RequestUtil requestUtil;

    /**
     * 雪花经理
     */
    @Resource
    private SnowflakeManager snowflakeManager;

    /**
     * 解绑
     *
     * @param ownerParkId 公园所有者id
     * @param request     请求
     * @return {@link Result}
     * @throws Exception 异常
     */
    @Override
    public Result deleteOwnerPark(String ownerParkId, HttpServletRequest request) throws Exception {
        Result result = new Result();
        //获取车位车主信息
        OwnerParkListDto ownerPark = this.baseMapper.getOwnerPark(ownerParkId);
        SysUser user = this.requestUtil.getUser(request);
        String communityId = this.baseMapper.getCommunityId(ownerPark.getParkId());
        ownerPark.setCommunityId(communityId);
        ownerPark.setRecordId(snowflakeManager.nextId()+"");
        ownerPark.setUpdateBy(user.getUserName());
        ownerPark.setParkBundingStatus("3");
        ownerPark.setUpdateTime(LocalDateTime.now().toString());
        //插入数据到记录表
        try {
            this.baseMapper.insertRecord(ownerPark);
            this.baseMapper.deleteOwnerPark(ownerParkId);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        }

        return result;
    }

    /**
     * 得到老板公园列表
     *
     * @param ownerParkListDto 主人dto公园列表
     * @param page             页面
     * @return {@link Result}
     */
    @Override
    public Result getOwnerParkList(OwnerParkListDto ownerParkListDto, Page page) {
        Result result = new Result();
        MPJLambdaWrapper<ZyOwnerPark> zyOwnerParkMPJLambdaWrapper = new MPJLambdaWrapper<>();
        zyOwnerParkMPJLambdaWrapper.selectAll(ZyOwner.class)
                .select(ZyCommunity::getCommunityName)
                .select(ZyPark::getParkCode)
                .select(ZyOwnerPark::getCreateTime)
                .select(ZyOwnerPark::getOwnerParkId)
                .select(ZyPark::getParkType)
                .select(ZyPark::getParkId)
                .leftJoin(ZyPark.class,ZyPark::getParkId,ZyOwnerPark::getParkId)
                .leftJoin(ZyCommunity.class,ZyCommunity::getCommunityId,ZyPark::getCommunityId)
                .leftJoin(ZyOwner.class,ZyOwner::getOwnerId,ZyOwnerPark::getOwnerId)
                .like(StringUtil.isNotEmpty(ownerParkListDto.getOwnerNickname()),ZyOwner::getOwnerNickname,ownerParkListDto.getOwnerNickname())
                .like(StringUtil.isNotEmpty(ownerParkListDto.getOwnerRealName()),ZyOwner::getOwnerRealName,ownerParkListDto.getOwnerRealName())
                .like(StringUtil.isNotEmpty(ownerParkListDto.getOwnerIdCard()),ZyOwner::getOwnerIdCard,ownerParkListDto.getOwnerIdCard())
                .like(StringUtil.isNotEmpty(ownerParkListDto.getOwnerPhoneNumber()),ZyOwner::getOwnerPhoneNumber,ownerParkListDto.getOwnerPhoneNumber())
                .eq(ZyOwnerPark::getParkOwnerStatus,"Binding");
        IPage<OwnerParkListDto> ownerParkListDtoIPage = this.baseMapper.selectJoinPage(page, OwnerParkListDto.class, zyOwnerParkMPJLambdaWrapper);
        if (ownerParkListDtoIPage.getTotal()>=0)
        {
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            result.setData(ownerParkListDtoIPage);
        }
        return result;
    }

    /**
     * 删除所有者公园由ids
     * 批量删除
     *
     * @param idList id的集合
     * @return {@link Result}
     */
    @Override
    public Result deleteOwnerParkByIds(List<String> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是单个
        int i = this.baseMapper.deleteOwnerParkByIds(idList);
        if (i >= 1) {
            result.setData("删除成功，影响的行数：" + i);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 更新所有者公园
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
        String[] fields = new String[]{"parkOwnerStatus", "remark", "ownerId", "parkId", "ownerParkId"};
        if (!ObjUtil.checkEquals(zyOwnerPark1, zyOwnerPark, fields)) {
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
        } else {
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            result.setData("参数没有变化");
        }
        return result;
    }

    /**
     * 插入所有者公园
     * 新增车位审核
     *
     * @param zyOwnerPark 车位审核对象
     * @return 车位审核的条数
     * @throws Exception 异常
     */
    @Override
    public Result insertOwnerPark(ZyOwnerPark zyOwnerPark) throws Exception {
        Result result = new Result("添加失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
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
            return new Result("添加失败，请稍后再试", ResultTool.fail(ResultCode.COMMON_FAIL));
        }
    }

    /**
     * 更新所有者公园状态
     * 提交车位审核以后审核状态改变
     *
     * @param zyOwnerPark        车位审核对象
     * @param recordAuditOpinion 车位审核意见
     * @return {@link Result}
     * @throws Exception 异常
     */
    @Override
    public Result updateOwnerParkStatus(ZyOwnerPark zyOwnerPark, String recordAuditOpinion) throws Exception {
        //默认给失败
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //新增车位审核记录
        ZyOwnerParkRecord zyOwnerParkRecord = new ZyOwnerParkRecord();
        //审核记录的id
        zyOwnerParkRecord.setRecordId(snowflakeManager.nextId() + "");
        //车位id
        zyOwnerParkRecord.setOwnerParkId(zyOwnerPark.getOwnerParkId() + "");
        //小区id 先根据parkId找到zyPark，再拿小区id
        ZyPark zyPark = zyParkDao.queryById(zyOwnerPark.getParkId());
        zyOwnerParkRecord.setCommunityId(zyPark.getCommunityId() + "");
        //业主id
        zyOwnerParkRecord.setOwnerId(zyOwnerPark.getOwnerId());
        //业主id
        zyOwnerParkRecord.setOwnerParkId(zyOwnerPark.getOwnerParkId() + "");
        //绑定状态
        zyOwnerParkRecord.setParkBundingStatus(zyOwnerPark.getParkOwnerStatus());
        //审核意见
        zyOwnerParkRecord.setRecordAuditOpinion(recordAuditOpinion);
        //审核人类型
        zyOwnerParkRecord.setRecordAuditType("Web");
        //创建时间
        zyOwnerParkRecord.setUpdateTime(LocalDateTime.now().toString());
        zyOwnerParkRecord.setCreateTime(zyOwnerPark.getCreateTime());
        zyOwnerParkRecord.setCreateBy(zyOwnerPark.getCreateBy());
        zyOwnerParkRecord.setUpdateBy(zyOwnerPark.getUpdateBy());
        zyOwnerParkRecordDao.insert(zyOwnerParkRecord);
        //修改时间
        zyOwnerPark.setUpdateTime(LocalDateTime.now().toString());
        this.baseMapper.updateOwnerParkStatus(zyOwnerPark);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 选择所有公园限制
     * 查询所有的车位审核并分页
     *
     * @param zyOwnerParkDto 车位审核对象
     * @param page           页面
     * @return {@link Result}
     */
    @Override
    public Result selectAllParkLimit(ZyOwnerParkDto zyOwnerParkDto, Page page) {
        //默认给失败
        Result result = new Result("没有符合条件的数据", ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            MPJLambdaWrapper<ZyOwnerPark> zyOwnerParkDtoMPJLambdaWrapper = new MPJLambdaWrapper<>();
            zyOwnerParkDtoMPJLambdaWrapper.selectAll(ZyOwnerPark.class)
                    .select(ZyCommunity::getCommunityName)
                    .select(ZyPark::getParkCode)
                    .select(ZyPark::getParkType)
                    .select(ZyPark::getParkIsPublic)
                    .select(ZyPark::getParkStatus)
                    .leftJoin(ZyPark.class, ZyPark::getParkId, ZyOwnerPark::getParkId)
                    .leftJoin(ZyCommunity.class, ZyCommunity::getCommunityId, ZyPark::getCommunityId)
                    .eq(StringUtil.isNotEmpty(zyOwnerParkDto.getParkOwnerStatus()), ZyOwnerPark::getParkOwnerStatus, zyOwnerParkDto.getParkOwnerStatus());
            IPage<ZyOwnerParkDto> zyOwnerParkDtoIPage = this.baseMapper.selectJoinPage(page, ZyOwnerParkDto.class, zyOwnerParkDtoMPJLambdaWrapper);
            if (zyOwnerParkDtoIPage.getTotal() != 0) {
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
     * 查询通过id
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
     * 插入
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
     * 删除通过id
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
