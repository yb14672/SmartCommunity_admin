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
import com.zy_admin.sys.service.RedisService;
import com.zy_admin.util.*;
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

    /**
     * 雪花算法
     */
    @Resource
    SnowflakeManager snowflakeManager;

    /**
     * 请求工具类
     */
    @Resource
    private RequestUtil requestUtil;
    @Resource
    private RedisService redisService;

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
     * 检查电话号码唯一
     *
     * @param type  类型
     * @param owner 业主
     * @return boolean
     */
    @Override
    public boolean checkPhoneNumberUnique(int type, ZyOwner owner) {
        ZyOwner zyOwner = this.baseMapper.checkPhoneNumber(owner.getOwnerPhoneNumber());
        //新增
        if (type == 0) {
            return zyOwner == null||zyOwner.getOwnerId().isEmpty() ? true : false;
        } else {
            //修改
            if (zyOwner == null || zyOwner.getOwnerId().isEmpty()) {
                return true;
            //判断业主是否唯一
            } else {
                return zyOwner.getOwnerId().equals(owner.getOwnerId());
            }
        }
    }

    /**
     * 业主更新
     *
     * @param owner   老板
     * @return {@link Result}
     */
    @Override
    public Result ownerUpdate(ZyOwner owner) {
        Result result = new Result(null, ResultTool.fail(ResultCode.OWNER_UPDATE_FAIL));
        owner.setUpdateTime(LocalDateTime.now().toString());
        if(checkPhoneNumberUnique(1,owner)){
            int i = this.baseMapper.updateById(owner);
            if (i > 0) {
                result.setData("修改成功");
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            }
        }else{
            result.setData("修改失败，手机号重复");
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONE_NUMBER));
        }
        return result;
    }

    /**
     * 业主登录
     *
     * @param owner 老板
     * @return {@link Result}
     */
    @Override
    public Result ownerLogin(ZyOwner owner) {
        Result result = new Result("登陆失败", ResultTool.fail(ResultCode.USER_WRONG_ACCOUNT_OR_PASSWORD));
        ZyOwner zyOwner = this.baseMapper.ownerLogin(owner);
        if (zyOwner != null) {
            String jwtToken = JwtUtil.getJwtToken(zyOwner.getOwnerId(), zyOwner.getOwnerPhoneNumber());
            redisService.set(jwtToken, zyOwner.getOwnerId());
            result.setData(jwtToken);
            if (!"Enable".equals(zyOwner.getOwnerStatus())){
                result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_LOCKED));
                return result;
            }
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 所有者注册
     *
     * @param owner 老板
     * @return {@link Result}
     * @throws Exception 异常
     */
    @Override
    public Result ownerRegister(ZyOwner owner) throws Exception {
        Result result = new Result("注册失败", ResultTool.fail(ResultCode.USER_REGISTER_FAIL));
        owner.setOwnerId(snowflakeManager.nextId() + "");
        owner.setCreateTime(LocalDateTime.now().toString());
        if (!checkPhoneNumberUnique(0,owner)) {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONE_NUMBER));
            return result;
        }
        int b = this.baseMapper.insert(owner);
        if (b == 1) {
            result.setData("注册成功");
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 得到业主名单
     * 根据条件查询业主列表并分页
     *
     * @param zyOwner  户主信息
     * @param pageable 页码
     * @return 获取分页结果集
     */
    @Override
    public Result getOwnerList(ZyOwner zyOwner, Pageable pageable) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        Long total = this.baseMapper.countOwner(zyOwner);
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
        OwnerDto ownerDto = new OwnerDto(pageable, ownerList);
        result.setData(ownerDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 删除欧文罗马
     * 解绑房间
     *
     * @param request    获取修改者信息
     * @param owenRoomId 绑定房间信息
     * @return 修改结果集
     */
    @Override
    public Result deleteOwenRome(HttpServletRequest request, String owenRoomId) {
        Result result = new Result();
        try {
            ZyOwnerRoomRecord zyOwnerRoom = this.baseMapper.getZyOwnerRoom(owenRoomId);
            //将解绑记录放入历史表中
            SysUser user = this.requestUtil.getUser(request);
            zyOwnerRoom.setRecordAuditOpinion("由" + user.getUserName() + "解绑");
            zyOwnerRoom.setUpdateBy(user.getUserName());
            zyOwnerRoom.setUpdateTime(LocalDateTime.now().toString());
            zyOwnerRoom.setRecordId(snowflakeManager.nextId() + "");
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
     * 查询所有者通过id
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

