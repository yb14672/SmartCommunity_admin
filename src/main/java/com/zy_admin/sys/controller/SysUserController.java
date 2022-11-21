package com.zy_admin.sys.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.sys.dto.UserDto;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.entity.SysUserUpload;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.*;
import com.zy_admin.common.enums.ResultCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户信息表(SysUser)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;
    @Resource
    private RequestUtil requestUtil;


    /**
     * 用户登出日志接口
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        return sysUserService.logout(request);
    }
    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @MyLog(title = "用户管理", optParam = "#{idList}", businessType = BusinessType.DELETE)
    public Result deleteUserById(@RequestParam String[] idList) {
        List<Integer> idList1 = new ArrayList<Integer>();
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            for (String id : idList) {
                idList1.add(Integer.valueOf(id));
                if ("1".equals(id)) {
                    result.setMeta(ResultTool.fail(ResultCode.ADMIN_NOT_ALLOWED_DELETE));
                    return result;
                }
            }
            //逻辑删除用户表
            result = this.sysUserService.deleteUserById(idList1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 分页查询所有数据
     *
     * @param pageable 分页对象
     * @param sysUser  查询实体
     * @return 所有数据
     */
    @GetMapping("/selectUsers")
    public Result selectUsers(Pageable pageable, SysUser sysUser, String startTime, String endTime) {
        return this.sysUserService.selectUsers(pageable, sysUser, startTime, endTime);
    }

    /**
     * 根据用户ID获取其信息和对应的角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/authRole/{userId}")
    public Result authRole(@PathVariable("userId") Long userId) {
        return this.sysUserService.authRole(userId);
    }

    /**
     * 根据用户ID修改其对应的角色列表
     *
     * @param map
     * @return
     */
    @PutMapping("/authRole")
    @MyLog(title = "分配角色", optParam = "#{map}", businessType = BusinessType.INSERT)
    public Result insertAuthRole(@RequestBody Map<String, Object> map) throws Exception {
        Integer userId = (Integer) map.get("userId");
        String roleId = map.get("roleId").toString();
        return this.sysUserService.insertAuthRole(userId, roleId);
    }

    /**
     * 导入
     *
     * @param file
     */
    @PostMapping("/import-data")
    @MyLog(title = "用户管理", optParam = "#{file}", businessType = BusinessType.IMPORT)
    public Result importData(@RequestParam("file") MultipartFile file) {
        return sysUserService.importData(file);
    }

    /**
     * 用于批量导出用户列表数据
     *
     * @param userIds
     * @param response
     */
    @GetMapping("/getExcel")
    @MyLog(title = "用户管理", optParam = "#{userIds}", businessType = BusinessType.EXPORT)
    public void getExcel(@RequestParam("userIds") ArrayList<Integer> userIds, HttpServletResponse response) throws IOException {
        List<SysUser> sysUserList = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        if (userIds == null || userIds.size() == 0) {
            sysUserList = sysUserService.getUserLists();
        } else {
            //执行查询用户列表的sql语句
            sysUserList = sysUserService.queryUserById(userIds);
        }
        String fileName = URLEncoder.encode("用户表数据", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), SysUser.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(sysUserList);
    }

    /**
     * 下载模板
     *
     * @param
     * @param response
     * @throws IOException
     */
    @GetMapping("/uploadExcel")
    @MyLog(title = "用户管理", optParam = "#{response}", businessType = BusinessType.EXPORT)
    public void uploadExcel(HttpServletResponse response) throws IOException {
        List<SysUser> sysUserList = new ArrayList<>();
        //直接下载模板
        sysUserList = sysUserService.uploadUserTemplate();
        String fileName = URLEncoder.encode("下载模板表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        EasyExcel.write(response.getOutputStream(), SysUserUpload.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板")
                .doWrite(sysUserList);
    }
    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysUser 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(Page page, SysUser sysUser) {
        return this.sysUserService.selectAll(page, sysUser);
    }

    /**
     * 根据用户ID获取头像
     *
     * @param request
     * @return
     */
    @GetMapping("/getAvatarById")
    public Result getAvatarById(HttpServletRequest request) {
        String id = JwtUtil.getMemberIdByJwtToken(request);
        Result avatarById = this.sysUserService.getAvatarById(id);
        return avatarById;
    }

    /**
     * 新增数据
     *
     * @param sysUser 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysUser sysUser) {
        return success(this.sysUserService.save(sysUser));
    }

    /**
     * 修改数据
     *
     * @param sysUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysUser sysUser) {
        return success(this.sysUserService.updateById(sysUser));
    }

    /**
     * 登录
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/login")
    public Result login(SysUser sysUser) {
        Result result = sysUserService.login(sysUser);
        return result;
    }

    /**
     * 根据ID获取用户信息
     *
     *
     * @param userId 用户ID
     * @return 查询的用户结果+http状态
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(String userId) {
        return this.sysUserService.personal(userId);
    }

    /**
     * 根据ID获取用户信息
     *
     * @param request 用户ID
     * @return 查询的用户结果+http状态
     */
    @GetMapping("/personal")
    public Result personal(HttpServletRequest request) {
        String memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(request);
        return this.sysUserService.personal(memberIdByJwtToken);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/id/{id}")
    public Result selectOne(@PathVariable String id) {
        return this.sysUserService.queryById(id);
    }

    /**
     * 通过账号查询单条数据
     *
     * @param name 主键
     * @return 单条数据
     */
    @GetMapping("/name/{name}")
    public Result queryByName(@PathVariable String name) {
        return this.sysUserService.queryByName(name);
    }

    /**
     * 修改用户基本信息
     *
     * @param sysUser
     * @return
     */
    @PutMapping("/updateUser")
    @MyLog(title = "用户管理", optParam = "#{sysUser}", businessType = BusinessType.UPDATE)
    public Result updateUser(@RequestBody SysUser sysUser) {
        return this.sysUserService.updateUser(sysUser);
    }

    /**
     * 修改密码
     *
     * @param sysUser
     * @return
     */
    @PutMapping("/resetPwd")
    @MyLog(title = "重置密码", optParam = "#{sysUser}", businessType = BusinessType.UPDATE)
    public Result resetPwd(@RequestBody SysUser sysUser) {
        return this.sysUserService.resetPwd(sysUser);
    }


    /**
     * 用户管理 新增用户
     * @param request
     * @param sysUserDto
     * @return
     */
    @PostMapping("/insertUser")
    @MyLog(title = "用户管理", optParam = "#{sysUserDto}", businessType = BusinessType.INSERT)
    public Result insertUser(HttpServletRequest request, @RequestBody UserDto sysUserDto) {
        sysUserDto.setCreateTime(LocalDateTime.now().toString());
        SysUser user = this.requestUtil.getUser(request);
        sysUserDto.setCreateBy(user.getUserName());
        return sysUserService.insertUser(sysUserDto);
    }

    /**
     * 用户管理修改用户
     * @param request
     * @param userDto
     * @return
     */
    @PutMapping("/adminUpdateUser")
    @MyLog(title = "用户管理", optParam = "#{userDto}", businessType = BusinessType.UPDATE)
    public Result updateUser(HttpServletRequest request, @RequestBody UserDto userDto) {
        userDto.setUpdateTime(LocalDateTime.now().toString());
        SysUser user = this.requestUtil.getUser(request);
        userDto.setCreateBy(user.getUserName());
        return sysUserService.adminUpdateUser(userDto);
    }

    /**
     * 用户管理重置密码
     * @param request
     * @param sysUser
     * @return
     */
    @PostMapping("/resetPassword")
    @MyLog(title = "重置密码", optParam = "#{sysUser}", businessType = BusinessType.UPDATE)
    public Result resetPassword(HttpServletRequest request, @RequestBody SysUser sysUser) {
        SysUser user = this.requestUtil.getUser(request);
        sysUser.setUpdateBy(user.getUserName());
        return sysUserService.resetPassword(sysUser);
    }
}

