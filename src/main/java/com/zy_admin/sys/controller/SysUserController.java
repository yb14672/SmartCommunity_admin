package com.zy_admin.sys.controller;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.dto.UserDto;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.entity.SysUserUpload;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Api(value = "sysUser", tags = {"用户信息表(SysUser)表控制层"})
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
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 查询制定部门
     * @param communityId
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "long", name = "communityId", value = "", required = true)
    })
    @ApiOperation(value = "查询制定部门", notes = "查询制定部门")
    @GetMapping("/getUserByDeptAndCommunityId")
    public Result getUserByDeptAndCommunityId(@RequestParam("communityId") Long communityId){
        return sysUserService.getUserByDeptAndCommunityId(communityId);
    }
    /**
     * 查询用户用户id
     *
     * @param userId 用户id
     * @return {@code Result}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "userId", value = "用户id", required = true)
    })
    @ApiOperation(value = "查询用户用户id", notes = "查询用户用户id")
    @GetMapping("/queryUserByUserId")
    public Result queryUserByUserId(@RequestParam("userId") Long userId) {
        return sysUserService.queryUserByUserId(userId);
    }
    /**
     * 退出
     * @param request 前端请求
     * @return 成功或失败的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "退出", notes = "退出", httpMethod = "POST")
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        return sysUserService.logout(request);
    }
    /**
     * 删除用户数据
     * @param idList 删除用户的主键数组
     * @return 删除用户信息结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String[]", name = "idList", value = "删除用户的主键数组", required = true)
    })
    @ApiOperation(value = "删除用户数据", notes = "删除用户数据", httpMethod = "DELETE")
    @DeleteMapping
    @MyLog(title = "用户管理", optParam = "#{idList}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAnyAuthority('system:user:remove')")
    public Result deleteUserById(@RequestParam String[] idList) {
        List<Integer> idList1 = new ArrayList<>();
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
     * 分页查询所有用户对象数据
     * @param pageable 分页对象
     * @param sysUser  用户对象
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Pageable", name = "pageable", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "SysUser", name = "sysUser", value = "用户对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "startTime", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "endTime", value = "", required = true)
    })
    @ApiOperation(value = "分页查询所有用户对象数据", notes = "分页查询所有用户对象数据", httpMethod = "GET")
    @GetMapping("/selectUsers")
    @PreAuthorize("hasAnyAuthority('system:user:query')")
    public Result selectUsers(Pageable pageable, SysUser sysUser, String startTime, String endTime) {
        return this.sysUserService.selectUsers(pageable, sysUser, startTime, endTime);
    }
    /**
     * 根据用户ID获取其信息和对应的角色
     * @param userId 用户主键
     * @return 查询的用户结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "userId", value = "用户主键", required = true)
    })
    @ApiOperation(value = "根据用户ID获取其信息和对应的角色", notes = "根据用户ID获取其信息和对应的角色", httpMethod = "GET")
    @GetMapping("/authRole/{userId}")
    @PreAuthorize("hasAnyAuthority('system:user:query')")
    public Result authRole(@PathVariable("userId") Long userId) {
        return this.sysUserService.authRole(userId);
    }
    /**
     * 根据用户ID修改其对应的角色列表
     * @param map 前端获值
     * @return 获取角色列表结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Map<String, Object>", name = "map", value = "前端获值", required = true)
    })
    @ApiOperation(value = "根据用户ID修改其对应的角色列表", notes = "根据用户ID修改其对应的角色列表", httpMethod = "PUT")
    @PutMapping("/authRole")
    @MyLog(title = "分配角色", optParam = "#{map}", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAnyAuthority('system:user:edit')")
    public Result insertAuthRole(@RequestBody Map<String, Object> map) throws Exception {
        Integer userId = (Integer) map.get("userId");
        String roleId = map.get("roleId").toString();
        return this.sysUserService.insertAuthRole(userId, roleId);
    }
    /**
     * 导入用户数据
     * @param file 导入的文件对象
     * @return 导入用户信息的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "file", value = "导入的文件对象", required = true)
    })
    @ApiOperation(value = "导入用户数据", notes = "导入用户数据", httpMethod = "POST")
    @PostMapping("/import-data")
    @MyLog(title = "用户管理", optParam = "#{file}", businessType = BusinessType.IMPORT)
    @PreAuthorize("hasAnyAuthority('system:user:import')")
    public Result importData(@RequestParam("file") MultipartFile file) {
        return sysUserService.importData(file);
    }
    /**
     * 用于批量导出用户列表数据
     * @param userIds 用户的主键数组
     * @param response 前端请求
     * @return 导出的用户列表信息结果集
     * @throws IOException 抛出数据流异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<Integer>", name = "userIds", value = "用户的主键数组", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端请求", required = true)
    })
    @ApiOperation(value = "用于批量导出用户列表数据", notes = "用于批量导出用户列表数据", httpMethod = "GET")
    @MyLog(title = "用户管理", optParam = "#{userIds}", businessType = BusinessType.EXPORT)
    @GetMapping("/getExcel")
    @PreAuthorize("hasAnyAuthority('system:user:export')")
    public Result getExcel(@RequestParam("userIds") ArrayList<Integer> userIds, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysUser> sysUserList;
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
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), SysUser.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("用户信息");
        excel.doWrite(sysUserList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 下载输入模板
     * @param response 前端响应
     * @return 空的模板结果集
     * @throws IOException 抛出数据流异常
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "前端响应", required = true)
    })
    @ApiOperation(value = "下载输入模板", notes = "下载输入模板", httpMethod = "GET")
    @GetMapping("/uploadExcel")
    @MyLog(title = "下载模板", optParam = "#{response}", businessType = BusinessType.EXPORT)
    public Result uploadExcel(HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysUser> sysUserList = new ArrayList<>();
        String fileName = URLEncoder.encode("下载模板", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), SysUserUpload.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("模板");
        excel.doWrite(sysUserList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
    /**
     * 分页查询所有数据
     * @param page    分页对象
     * @param sysUser 查询用户对象
     * @return 查询的用户信息结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "SysUser", name = "sysUser", value = "查询用户对象", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('system:user:query')")
    public Result selectAll(Page page, SysUser sysUser) {
        return this.sysUserService.selectAll(page, sysUser);
    }
    /**
     * 根据用户ID获取头像
     * @param request 前端请求
     * @return 头像结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true)
    })
    @ApiOperation(value = "根据用户ID获取头像", notes = "根据用户ID获取头像", httpMethod = "GET")
    @GetMapping("/getAvatarById")
//    @PreAuthorize("hasAnyAuthority('system:user:query')")
    public Result getAvatarById(HttpServletRequest request) {
        String id = JwtUtil.getMemberIdByJwtToken(request,"token");
        return this.sysUserService.getAvatarById(id);
    }
    /**
     * 登录
     * @param sysUser 用户信息
     * @return 成果或失败的结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "SysUser", name = "sysUser", value = "用户信息", required = true)
    })
    @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST")
    @PostMapping("/login")
    public Result login(SysUser sysUser) {
        return sysUserService.login(sysUser);
    }
    /**
     * 根据ID获取用户信息
     * @param userId 用户主键
     * @return 查询的用户结果+http状态
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "userId", value = "用户主键", required = true)
    })
    @ApiOperation(value = "根据ID获取用户信息", notes = "根据ID获取用户信息", httpMethod = "GET")
    @GetMapping("/getUserInfo")
    public Result getUserInfo(String userId) {
        return this.sysUserService.personal(userId);
    }
    /**
     * 根据ID获取用户信息
     * @param request 用户主键
     * @return 查询的用户结果+http状态
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "用户主键", required = true)
    })
    @ApiOperation(value = "根据ID获取用户信息", notes = "根据ID获取用户信息", httpMethod = "GET")
    @GetMapping("/personal")
    public Result personal(HttpServletRequest request) {
        String userId = requestUtil.getUserId(request);
        return this.sysUserService.personal(userId);
    }
    /**
     * 通过主键查询单条数据
     * @param id 用户主键
     * @return 查询的单条数据结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "用户主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("/id/{id}")
    public Result selectOne(@PathVariable String id) {
        return this.sysUserService.queryById(id);
    }
    /**
     * 通过账号查询单条数据
     * @param name 用户名称
     * @return 查询的单条用户账户结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "name", value = "用户名称", required = true)
    })
    @ApiOperation(value = "通过账号查询单条数据", notes = "通过账号查询单条数据", httpMethod = "GET")
    @GetMapping("/name/{name}")
    public Result queryByName(@PathVariable String name) {
        return this.sysUserService.queryByName(name);
    }
    /**
     * 修改用户基本信息
     * @param sysUser 用户对象
     * @return 修改的用户信息结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysUser", name = "sysUser", value = "用户对象", required = true)
    })
    @ApiOperation(value = "修改用户基本信息", notes = "修改用户基本信息", httpMethod = "PUT")
    @PutMapping("/updateUser")
    @MyLog(title = "用户管理", optParam = "#{sysUser}", businessType = BusinessType.UPDATE)
    public Result updateUser(@RequestBody SysUser sysUser) {
        return this.sysUserService.updateUser(sysUser);
    }
    /**
     * 修改密码
     * @param sysUser 用户对象
     * @return 修改的用户密码信息结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysUser", name = "sysUser", value = "用户对象", required = true)
    })
    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "PUT")
    @PutMapping("/resetPwd/{oldPwd}")
    @MyLog(title = "重置密码", optParam = "#{sysUser}", businessType = BusinessType.UPDATE)
    public Result resetPwd(@RequestBody SysUser sysUser,@PathVariable String oldPwd) {
        return this.sysUserService.resetPwd(sysUser,oldPwd);
    }
    /**
     * 新增用户
     * @param request 前端请求
     * @param sysUserDto 用户dto对象
     * @return 新增用户结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "UserDto", name = "sysUserDto", value = "用户dto对象", required = true)
    })
    @ApiOperation(value = "新增用户", notes = "新增用户", httpMethod = "POST")
    @PostMapping("/insertUser")
    @MyLog(title = "用户管理", optParam = "#{sysUserDto}", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAnyAuthority('system:user:add')")
    public Result insertUser(HttpServletRequest request, @RequestBody UserDto sysUserDto) {
        String encode = bCryptPasswordEncoder.encode(sysUserDto.getPassword());
        sysUserDto.setPassword(encode);
        sysUserDto.setCreateTime(LocalDateTime.now().toString());
        SysUser user = this.requestUtil.getUser(request);
        sysUserDto.setCreateBy(user.getUserName());
        return sysUserService.insertUser(sysUserDto);
    }
    /**
     * 修改用户
     * @param request 前端请求
     * @param userDto 用户dto对象
     * @return 修改用户结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "UserDto", name = "userDto", value = "用户dto对象", required = true)
    })
    @ApiOperation(value = "修改用户", notes = "修改用户", httpMethod = "PUT")
    @PutMapping("/adminUpdateUser")
    @MyLog(title = "用户管理", optParam = "#{userDto}", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyAuthority('system:user:edit')")
    public Result updateUser(HttpServletRequest request, @RequestBody UserDto userDto) {
        userDto.setUpdateTime(LocalDateTime.now().toString());
        SysUser user = this.requestUtil.getUser(request);
        userDto.setCreateBy(user.getUserName());
        return sysUserService.adminUpdateUser(userDto);
    }
    /**
     * 重置密码
     * @param request 前端请求
     * @param sysUser 用户对象
     * @return 重置后的用户信息结果集
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "前端请求", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "SysUser", name = "sysUser", value = "用户对象", required = true)
    })
    @ApiOperation(value = "重置密码", notes = "重置密码", httpMethod = "POST")
    @PostMapping("/resetPassword")
    @MyLog(title = "重置密码", optParam = "#{sysUser}", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAnyAuthority('system:user:resetPwd')")
    public Result resetPassword(HttpServletRequest request, @RequestBody SysUser sysUser) {
        String encode = bCryptPasswordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encode);
        SysUser user = this.requestUtil.getUser(request);
        sysUser.setUpdateBy(user.getUserName());
        return sysUserService.resetPassword(sysUser);
    }
}

