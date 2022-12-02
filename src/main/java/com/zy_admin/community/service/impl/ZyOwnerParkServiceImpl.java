package com.zy_admin.community.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.dao.ZyOwnerParkDao;
import com.zy_admin.community.dto.OwnerParkListDto;
import com.zy_admin.community.dto.ZyOwnerRoomDto;
import com.zy_admin.community.dto.ZyOwnerRoomDtoAll;
import com.zy_admin.community.entity.*;
import com.zy_admin.community.service.ZyOwnerParkService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import com.zy_admin.util.StringUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerPark)表服务实现类
 *
 * @author makejava
 * @since 2022-12-01 15:18:41
 */
@Service("zyOwnerParkService")
public class ZyOwnerParkServiceImpl extends ServiceImpl<ZyOwnerParkDao, ZyOwnerPark> implements ZyOwnerParkService {
    @Resource
    private ZyOwnerParkDao zyOwnerParkDao;

    @Resource
    private SnowflakeManager snowflakeManager;

    @Resource
    private RequestUtil requestUtil;


    @Override
    public Result deleteOwnerPark(String ownerParkId, HttpServletRequest request) throws Exception {
        Result result = new Result();

        //获取车位车主信息
        OwnerParkListDto ownerPark = this.baseMapper.getOwnerPark(ownerParkId);
        System.err.println(ownerPark);
        SysUser user = this.requestUtil.getUser(request);
        String communityId = this.baseMapper.getCommunityId(ownerPark.getParkId());
        ownerPark.setCommunityId(communityId);
        ownerPark.setRecordId(snowflakeManager.nextId()+"");
        ownerPark.setUpdateBy(user.getUserName());
        ownerPark.setParkBundingStatus("3");
        ownerPark.setUpdateTime(LocalDateTime.now().toString());
        System.err.println("11"+ownerParkId);
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
                .eq(ZyOwnerPark::getParkOwnerStatus,"Building");
        IPage<OwnerParkListDto> ownerParkListDtoIPage = this.baseMapper.selectJoinPage(page, OwnerParkListDto.class, zyOwnerParkMPJLambdaWrapper);
        if (ownerParkListDtoIPage.getTotal()>=0)
        {
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            result.setData(ownerParkListDtoIPage);
        }
        return result;
    }
//


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
