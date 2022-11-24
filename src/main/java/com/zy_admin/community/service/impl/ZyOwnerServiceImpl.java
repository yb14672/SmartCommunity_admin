package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyOwnerDao;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyOwnerService;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 业主 (ZyOwner)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@Service("zyOwnerService")
public class ZyOwnerServiceImpl extends ServiceImpl<ZyOwnerDao, ZyOwner> implements ZyOwnerService {

    @Autowired
    private SnowflakeManager snowflakeManager;


    @Override
    public Result getOwner(HttpServletRequest request) {
        Result result = new Result("信息",ResultTool.fail(ResultCode.USER_INFO_GET_FAIL));
        try {
            String ownerId = JwtUtil.getMemberIdByJwtToken(request);
            ZyOwner zyOwner = this.baseMapper.selectById(ownerId);
            result.setData(zyOwner);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public Result ownerUpdate(ZyOwner owner, HttpServletRequest request) {
        Result result = new Result(null,ResultTool.fail(ResultCode.OWNER_UPDATE_FAIL));
        String ownerId = JwtUtil.getMemberIdByJwtToken(request);
        ZyOwner zyOwner = this.baseMapper.selectById(ownerId);
        owner.setOwnerId(ownerId);
        owner.setUpdateBy(zyOwner.getOwnerNickname());
        owner.setUpdateTime(LocalDateTime.now().toString());
        int i = this.baseMapper.updateById(owner);
        if (i>0){
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        }
        return result;
    }

    @Override
    public Result ownerLogin(ZyOwner owner) {
        Result result = new Result("登陆失败", ResultTool.fail(ResultCode.USER_WRONG_ACCOUNT_OR_PASSWORD));
        ZyOwner zyOwner = this.baseMapper.ownerLogin(owner);
        if (zyOwner != null){
            String jwtToken = JwtUtil.getJwtToken(zyOwner.getOwnerId(), zyOwner.getOwnerPhoneNumber());
            result.setData(jwtToken);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result ownerRegister(ZyOwner owner) throws Exception {
        Result result = new Result("注册失败", ResultTool.fail(ResultCode.USER_REGISTER_FAIL));
        owner.setOwnerType("qt");
        owner.setOwnerId(snowflakeManager.nextId() + "");
        owner.setCreateTime(LocalDateTime.now().toString());
        ZyOwner zyOwner = this.baseMapper.checkPhoneNumber(owner.getOwnerPhoneNumber());
        if (zyOwner != null) {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONE_NUMBER));
            return result;
        }
        int b = this.baseMapper.insert(owner);
        if (b == 1) {
            result.setData("注册成功");
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            return result;
        }
        return result;
    }

}

