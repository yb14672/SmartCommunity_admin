package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyOwnerService;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 业主 (ZyOwner)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@RestController
@RequestMapping("zyOwner")
public class ZyOwnerController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerService zyOwnerService;

    @GetMapping("/getOwner")
    public Result getOwner(HttpServletRequest request){
        return this.zyOwnerService.getOwner(request);
    }

    /**
     * 修改数据
     *
     * @param zyOwner 实体对象
     * @return 修改结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody ZyOwner zyOwner, HttpServletRequest request) {
        return this.zyOwnerService.ownerUpdate(zyOwner,request);
    }


    /**
     * 用户登录
     * @param zyOwner 手机号和密码
     * @return 登陆结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody ZyOwner zyOwner){
        return zyOwnerService.ownerLogin(zyOwner);
    }

    /**
     * 新增数据
     *
     * @param zyOwner 实体对象
     * @return 新增结果
     */
    @PostMapping("/register")
    public Result insert(@RequestBody ZyOwner zyOwner) throws Exception {
        return zyOwnerService.ownerRegister(zyOwner);
    }

}

