package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.dao.SysRoleDao;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.dao.SysUserRoleDao;
import com.zy_admin.sys.dto.*;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.RedisService;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.util.ObjUtil;
import com.zy_admin.util.ResultTool;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统用户服务impl
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @date 2022/11/22
 * @since 2022-11-01 19:49:42
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    /**
     * 服务对象
     */
    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Resource
    private RedisService redisService;
    @Resource
    private SysRoleDao sysRoleDao;

    /**
     * 注销
     *
     * @param request 前端请求
     * @return 用户结果集
     */
    @Override
    public Result logout(HttpServletRequest request) {
        //根据token获取当前登录的id
        String userId = JwtUtil.getMemberIdByJwtToken(request);
        //根据id获取当前的对象
        SysUser user = this.baseMapper.queryById(userId);
        Result result = new Result(user, ResultTool.fail(ResultCode.USER_LOGOUT_FAIL));
        if (Boolean.TRUE.equals(redisService.empty())) {
            result.setMeta(ResultTool.fail(ResultCode.USER_LOGOUT_SUCCESS));
        }
        return result;
    }

    /**
     * 删除用户id
     *
     * @param idList 用户id数组列表
     * @return 删除的用户结果集
     */
    @Override
    public Result deleteUserById(List<Integer> idList) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //若等于0则此次删除的用户
        int i = this.baseMapper.deleteByIdList(idList);
        if (i >= 1) {
            result.setData("删除成功，影响的行数：" + i);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } else {
            result.setMeta(ResultTool.fail(ResultCode.ROLE_HAS_BEEN_ASSIGNED));
        }
        return result;
    }

    /**
     * 查询所有用户
     *
     * @param pageable  分页对象
     * @param sysUser   用户对象
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 查询到的用户结果集
     */
    @Override
    public Result selectUsers(Pageable pageable, SysUser sysUser, String startTime, String endTime) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        //满足条件的总数据
        long total = this.baseMapper.count(sysUser, startTime, endTime);
        long pages = 0;
        if (total > 0) {
            //计算出总页码
            pages = total % pageable.getPageSize() == 0 ? total / pageable.getPageSize() : total / pageable.getPageSize() + 1;
            pageable.setPages(pages);
            //页码修正
            pageable.setPageNum(pageable.getPageNum() < 1 ? 1 : pageable.getPageNum());
            pageable.setPageNum(Math.min(pageable.getPageNum(), pages));
            //设置起始下标
            pageable.setIndex((pageable.getPageNum() - 1) * pageable.getPageSize());
        } else {
            pageable.setPageNum(0);
        }
        pageable.setTotal(total);
        List<SysUserDeptDto> sysUserDeptDtoList = this.baseMapper.selectUsers(sysUser, pageable, startTime, endTime);
        SysUsersDto sysUsersDto = new SysUsersDto(sysUserDeptDtoList, startTime, endTime, pageable);
        result.setData(sysUsersDto);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 根据用户ID修改其对应的角色列表
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return 修改用户结果集
     * @throws Exception 异常
     */
    @Override
    @Transactional
    public Result insertAuthRole(Integer userId, String roleId) throws Exception {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        List<SysRole> roleListByUserId = sysRoleDao.getRoleListByUserId(userId + "");
        //判断该用户之前是否有角色，避免空删除
        if(roleListByUserId.size()!=0){
            //先删除原本用户拥有的所有角色
            int i = this.sysUserRoleDao.deleteByUserId(userId + "");
            if (i == 0) {
                throw new Exception("修改用户角色时出错，请稍后再试");
            }
        }
        //再插入修改后的所有角色
        int i1 = this.sysUserRoleDao.insertBatchByRoleId(userId + "", roleId);
        if (i1 < 1) {
            throw new Exception("修改用户角色时出错，请稍后再试");
        }
        result.setData("重新分配角色成功");
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 根据用户ID获取其信息和对应的角色
     *
     * @param userId 用户id对象
     * @return 查询用户结果集
     */
    @Override
    public Result authRole(Long userId) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        UserRoleDto userRoleDto = this.baseMapper.authRole(userId);
        if (userRoleDto != null) {
            result.setData(userRoleDto);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }else{
            SysUser user = this.baseMapper.queryById(userId+"");
            result.setData(user);
            result.setMeta(ResultTool.fail(ResultCode.NO_ROLE));
        }
        return result;
    }

    /**
     * 导出所有用户
     * @return 查询用户集合
     */
    @Override
    public List<SysUser> getUserLists() {
        return baseMapper.getUserLists();
    }

    /**
     * 选中用户id导出
     *
     * @param userIds 用户id数组列表
     * @return 查询用户集合
     */
    @Override
    public List<SysUser> queryUserById(ArrayList<Integer> userIds) {
        //如果有选中列表，就执行导出多个
        userIds = userIds.size() == 0 ? null : userIds;
        return baseMapper.queryUserById(userIds);
    }

    /**
     * 批量导入用户信息
     *
     * @param file 文件
     * @return 导出的用户结果集
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public Result importData(MultipartFile file) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        // 从文件流获取工作簿对象
        Workbook workbook = getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(workbook.getFirstVisibleTab());
        List<SysUser> userEntityList = new ArrayList<>();
        // 记录手机号
        List<String> phoneNumber = new ArrayList<>();
        // 获取sheet表总行数\总列数
        int rowNumber = sheet.getLastRowNum();
        int emptyRow = 0;
        StringBuilder errorMsg = new StringBuilder();
        if (rowNumber > 0) {
            for (int i = 1; i < rowNumber + 1; i++) {
                // 判断当前行是否为空行
                if (judgeRow(sheet.getRow(i))) {
                    emptyRow++;
                }
                SysUser userEntity = new SysUser();
                // 添加姓名
                checkRequire(i, 0, sheet.getRow(i));
                userEntity.setUserName(getCellValue(sheet.getRow(i).getCell(0)));
                // 添加邮箱
                userEntity.setEmail(getCellValue(sheet.getRow(i).getCell(2)));
                // 验证用户名重复
                if (!checkUserName(i, 0, getCellValue(sheet.getRow(i).getCell(0)))) {
                    result.setMeta(ResultTool.fail(ResultCode.USERNAME_REPEAT));
                    errorMsg.append("第").append(i).append("条用户名重复,");
                }
                //判断为空
                if (!checkRequire(i, 0, sheet.getRow(i))) {
                    result.setMeta(ResultTool.fail(ResultCode.MASSAGE_NULL));
                    errorMsg.append("第").append(i).append("条用户名为空,");
                }
                if (!checkRequire(i, 1, sheet.getRow(i))) {
                    result.setMeta(ResultTool.fail(ResultCode.MASSAGE_NULL));
                    errorMsg.append("第").append(i).append("条昵称为空,");
                }
                if (!checkRequire(i, 2, sheet.getRow(i))) {
                    result.setMeta(ResultTool.fail(ResultCode.MASSAGE_NULL));
                    errorMsg.append("第").append(i).append("条邮箱为空,");
                }
                if (!checkRequire(i, 3, sheet.getRow(i))) {
                    result.setMeta(ResultTool.fail(ResultCode.MASSAGE_NULL));
                    errorMsg.append("第").append(i).append("条手机号为空,");
                }
                // 验证邮箱重复
                if (!checkEmail(i, 2, getCellValue(sheet.getRow(i).getCell(2)))) {
                    result.setMeta(ResultTool.fail(ResultCode.EMAIL_REPEAT));
                    errorMsg.append("第").append(i).append("条邮箱重复,");
                }
                //验证邮箱正则
                if (!checkEmailJudge(i, 2, getCellValue(sheet.getRow(i).getCell(2)))) {
                    result.setMeta(ResultTool.fail(ResultCode.EMAIL_NON_COMPLIANCE));
                    errorMsg.append("第").append(i).append("条邮箱不符合规则,");
                }
                //状态为0能渲染
                userEntity.setDelFlag("0");
                //添加昵称
                userEntity.setNickName(getCellValue(sheet.getRow(i).getCell(1)));
                // 添加手机号 colNum是列数
                if (!checkRequire(i, 3, sheet.getRow(i))) {
                    result.setMeta(ResultTool.fail(ResultCode.DATA_REPEAT));
                }
                if (!checkRepeat(i, 3, phoneNumber, getCellValue(sheet.getRow(i).getCell(3)))) {
                    result.setMeta(ResultTool.fail(ResultCode.FILE_REPEAT));
                }
                if (!checkPhoneNumber(i, 3, getCellValue(sheet.getRow(i).getCell(3)))) {
                    result.setMeta(ResultTool.fail(ResultCode.USER_TELREPEAT));
                    errorMsg.append("第").append(i).append("条电话号重复,");
                }
                if (!checkPhoneNumberJudge(i, 3, getCellValue(sheet.getRow(i).getCell(3)))) {
                    result.setMeta(ResultTool.fail(ResultCode.USER_TELREPEAT));
                    errorMsg.append("第").append(i).append("条电话号不符合规范,");
                }
                //思路:拿一个map去存键值，键是索引，值是内容，然后去遍历通过索引去取值，然后再前端遍历
                userEntity.setPhonenumber(getCellValue(sheet.getRow(i).getCell(3)));
                // 添加昵称
                if (sheet.getRow(i).getCell(1) != null) {
                    userEntity.setNickName(getCellValue(sheet.getRow(i).getCell(1)));
                }
                userEntity.setDeptId(100L);
                String pwd = getCellValue(sheet.getRow(i).getCell(4));
                if (pwd == null || "".equals(pwd)) {
                    userEntity.setPassword("88888888");
                } else {
                    if (pwd.trim().length() < 6) {
                        result.setMeta(ResultTool.fail(ResultCode.PASSWORD_ILLEGAL));
                        errorMsg.append("第").append(i).append("条密码小于6位,");
                    } else {
                        userEntity.setPassword(pwd);
                    }
                }
                userEntityList.add(userEntity);
                phoneNumber.add(getCellValue(sheet.getRow(i).getCell(3)));
            }
        } else {
            errorMsg.append("导入数据为空");
        }
        if (!"".equals(errorMsg.toString())) {
            result.setData(errorMsg.toString());
            return result;
        }
        if ((int) rowNumber == (int) emptyRow) {
            boolean b = baseMapper.saveBatch(userEntityList);
            if (b) {
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.USER_REPEAT));
            }
        }
        return result;
    }

    /**
     * 检查电话号码
     * 验证手机号不能重复
     *
     * @param i
     * @param i1
     * @param stringCellValue 字符串单元格值
     * @return boolean
     */
    private boolean checkPhoneNumber(int i, int i1, String stringCellValue) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phonenumber", stringCellValue);
        if (baseMapper.selectList(queryWrapper).size() > 0) {
            return false;
        }
        return true;
    }
    /**
     * 检查电话号码判断
     *
     * @param i
     * @param i1
     * @param stringCellValue 字符串单元格值
     * @return boolean
     */
    private boolean checkPhoneNumberJudge(int i, int i1, String stringCellValue) {
        //判断手机号的正则
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stringCellValue);
        return m.matches();
    }
    /**
     * 检查用户名
     * 验证用户名不能重复
     * @param i
     * @param i1
     * @param stringCellValue 字符串单元格值
     * @return boolean
     */
    private boolean checkUserName(int i, int i1, String stringCellValue) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", stringCellValue);
        return baseMapper.selectList(queryWrapper).size() <= 0;
    }

    /**
     * 检查电子邮件
     * 验证邮箱不能重复
     *
     * @param rowNum          行num
     * @param colNum          列num
     * @param stringCellValue 字符串单元格值
     * @return boolean
     */
    private boolean checkEmail(int rowNum, int colNum, String stringCellValue) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", stringCellValue);
        if (baseMapper.selectList(queryWrapper).size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 查看邮件法官
     * 邮箱判断正则
     *
     * @param rowNum          行num
     * @param colNum          列num
     * @param stringCellValue 字符串单元格值
     * @return boolean
     */
    private boolean checkEmailJudge(int rowNum, int colNum, String stringCellValue) {
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(stringCellValue);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查重复
     * 检查导入文件是重复
     *
     * @param rowNum          行num
     * @param colNum          列num
     * @param phoneNumber     电话号码
     * @param stringCellValue 字符串单元格值
     * @return boolean
     */
    private boolean checkRepeat(int rowNum, int colNum, List<String> phoneNumber, String stringCellValue) {
        for (String s : phoneNumber) {
            if (s.equals(stringCellValue)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查要求
     * 检验必填项 数据不能为空
     *
     * @param rowNum 行num
     * @param colNum 列num
     * @param row    行
     * @return boolean
     */
    private boolean checkRequire(int rowNum, int colNum, Row row) {
        Cell cell = row.getCell(colNum);
        if (cell == null || CellType.BLANK.equals(cell.getCellType())) {
            return false;
        }
        return true;
    }

    /**
     * 判断行
     * 判断当前行数是否为空行数
     * @param row 行
     * @return boolean
     */
    private boolean judgeRow(Row row) {
        int blankRowNum = 0;
        // 没有数据没有格式
        if (row == null) {
            return false;
        } else {
            // 有格式没有数据
            for (Cell cell : row) {
                if (cell.getCellType() != CellType.BLANK) {
                    blankRowNum++;
                    break;
                }
            }
            return blankRowNum != 0;
        }
    }

    /**
     * 获得当前行
     * 得到单元格值
     *
     * @param cell 当前行
     * @return 行结果集
     */
    private static String getCellValue(Cell cell) {
        String cellValue;
        if (cell == null) {
            cellValue = "";
            return cellValue;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                DecimalFormat df = new DecimalFormat("0");
                cellValue = df.format(cell.getNumericCellValue());
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case FORMULA:
                cellValue = cell.getCellFormula() + "";
                break;
            case BLANK:
                cellValue = "";
                break;
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 导入excel并解析
     *
     * @param file 文件
     * @return 返回excel结果集
     */
    private Workbook getWorkBook(MultipartFile file) {
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 创建Workbook工作薄对象，包含整个excel
        Workbook workbook = null;
        try {
            // 获取excel文件的io流
            InputStream inputStream = file.getInputStream();
            // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName != null) {
                if (fileName.endsWith("xls") || fileName.endsWith("XLS")) {
                    workbook = new HSSFWorkbook(inputStream);
                } else if (fileName.endsWith("xlsx") || fileName.endsWith("XLSX")) {
                    workbook = new XSSFWorkbook(inputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 通过id获取Avatar
     *
     * @param userId 用户id
     * @return Avatar结果集
     */
    @Override
    public Result getAvatarById(String userId) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        try {
            //判断传入的id是否为空
            if (userId == null || userId.isEmpty()) {
                result.setMeta(ResultTool.fail(ResultCode.USER_NOT_LOGIN));
                return result;
            }
            String avatar = this.baseMapper.getAvatarById(userId);
            if (avatar == null || avatar.isEmpty()) {
                result.setMeta(ResultTool.fail(ResultCode.USER_AVATAR_NULL));
                return result;
            }
            result.setData(avatar);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } catch (NullPointerException e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_IS_BLANK));
            return result;
        } catch (ClassCastException e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_TYPE_ERROR));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            return result;
        }
        return result;
    }

    /**
     * 登录
     *
     * @param sysUser 用户对象
     * @return 获取用户结果集
     */
    @Override
    public Result login(SysUser sysUser) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        SysUser user = baseMapper.login(sysUser);
        String jwtToken;
        if (user != null) {
            jwtToken = JwtUtil.getJwtToken(user.getUserId() + "", user.getNickName());
            redisService.set(jwtToken, sysUser.getUserName());
            result.setData(jwtToken);
            if ("1".equals(user.getStatus())) {
                result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_LOCKED));
                return result;
            }
            result.setMeta(ResultTool.fail(ResultCode.USER_LOGIN_SUCCESS));
            return result;
        }
        result.setMeta(ResultTool.fail(ResultCode.USER_WRONG_ACCOUNT_OR_PASSWORD));
        return result;
    }

    /**
     * 按名称查询
     *
     * @param userName 用户名
     * @return 获取用户结果集
     */
    @Override
    public Result queryByName(String userName) {
        SysUser user = this.baseMapper.queryByName(userName);
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是否有查到对应用户
        if (user == null) {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        } else {
            result.setData(user);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户id对象
     * @return 查询用户结果集
     */
    @Override
    public Result queryById(String id) {
        SysUser user = this.baseMapper.queryById(id);
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是否有查到对应用户
        if (user == null) {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        } else {
            result.setData(user);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 根据用户ID获取用户所有信息
     *
     * @param userId 用户id对象
     * @return 查询用户结果集
     */
    @Override
    public Result personal(String userId) {
        SysUserDto personal = this.baseMapper.personal(userId);
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断是否有查到对应用户
        if (personal == null) {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        } else {
            result.setData(personal);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 修改用户结果集
     */
    @Override
    public Result updateUser(SysUser user) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        SysUser sysUser = this.baseMapper.queryById(user.getUserId() + "");
        String[] fields = new String[]{"nickName", "phonenumber", "email", "sex"};
        if (!ObjUtil.checkEquals(sysUser, user, fields)) {
            int i = baseMapper.updateUser(user);
            if (i == 1) {
                result.setData("用户ID为" + user.getUserId() + "的信息修改成功");
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
        }
        return result;
    }

    /**
     * 重置密码
     *
     * @param user 用户对象
     * @return 修改用户密码结果集
     */
    @Override
    public Result resetPwd(SysUser user) {
        //加密密码--未来写
        SysUser sysUser = baseMapper.queryById(user.getUserId() + "");
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //判断旧密码是否一致
        if (!sysUser.getPassword().equals(user.getPassword())) {
            int i = baseMapper.updateUser(user);
            if (i == 1) {
                result.setData("用户ID为" + user.getUserId() + "的信息修改成功");
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_SAME_PASSWORD));
        }
        return result;
    }

    /**
     * 查询所有用户
     *
     * @param page    分页对象
     * @param sysUser 用户对象
     * @return 查询用户结果集
     */
    @Override
    public Result selectAll(Page page, SysUser sysUser) {
        return null;
    }
    /**
     * 新增用户
     * @param sysUserDto 用户dto对象
     * @return 新增用户结果集
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertUser(UserDto sysUserDto) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        if (checkUserName(0, sysUserDto)) {
            if (checkNiceName(0, sysUserDto)) {
                if (checkPhone(0, sysUserDto)) {
                    if (checkEmail(0, sysUserDto)) {
                        if (sysUserDto.getDeptId() == null) {
                            sysUserDto.setDeptId(100L);
                        }
                        this.baseMapper.insertUser(sysUserDto);
                        if (sysUserDto.getPostId() != null) {
                            this.baseMapper.insertPost(sysUserDto.getUserId(), sysUserDto.getPostId());
                        }
                        if (sysUserDto.getRoleId() != null) {
                            this.baseMapper.insertRole(sysUserDto.getUserId(), sysUserDto.getRoleId());
                        }
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                    } else {
                        result.setMeta(ResultTool.fail(ResultCode.REPEAT_EMAIL));
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONENUMBER));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_NICK_NAME));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_USER_NAME));
        }
        return result;
    }

    /**
     * 管理更新用户
     * 管理员修改用户
     * @param userDto 用户dto
     * @return 用户更新结果集
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result adminUpdateUser(UserDto userDto) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        UserDto user = this.baseMapper.getUserInfo(userDto.getUserId() + "");
        //需要判断的字段名
        String[] fields = new String[]{"nickName", "deptId", "phonenumber", "sex", "email", "status", "postId", "roleId", "remark"};
        if (ObjUtil.checkEquals(user, userDto, fields)) {
            result.setMeta(ResultTool.fail(ResultCode.NO_CHANGE_IN_PARAMETER));
            return result;
        }
        if (checkNiceName(1, userDto)) {
            if (checkPhone(1, userDto)) {
                if (checkEmail(1, userDto)) {
                    //判断postId有没有值
                    if (userDto.getPostId() != null) {
                        this.baseMapper.deletePost(userDto.getUserId());
                        this.baseMapper.insertPost(userDto.getUserId(), userDto.getPostId());
                    }
                    //判断roleId有没有值
                    if (userDto.getRoleId() != null) {
                        this.baseMapper.deleteRole(userDto.getUserId());
                        this.baseMapper.insertRole(userDto.getUserId(), userDto.getRoleId());
                    }
                    this.baseMapper.adminUpdateUser(userDto);
                    result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_EMAIL));
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONENUMBER));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_NICK_NAME));
        }
        return result;
    }

    /**
     * 按账号名称查询用户
     * @param sysUserDto 用户dto对象
     * @return 查询用户结果集
     */
    @Override
    public Boolean checkNiceName(int type, UserDto sysUserDto) {
        SysUser sysUser = this.baseMapper.checkNiceName(sysUserDto.getNickName());
        if (type == 0) {
            return sysUser == null || sysUser.getUserId() == null;
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else {
                return sysUser.getUserId().equals(sysUserDto.getUserId());
            }
        }
    }
    /**
     * 检查电话
     * @param type       判断是新增0或者修改1
     * @param sysUserDto 用户dto对象
     * @return 成功或失败的结果集
     */
    @Override
    public Boolean checkPhone(int type, UserDto sysUserDto) {
        SysUser sysUser = this.baseMapper.checkPhone(sysUserDto.getPhonenumber());
        if (type == 0) {
            return sysUser == null || sysUser.getUserId() == null;
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else {
                return sysUser.getUserId().equals(sysUserDto.getUserId());
            }
        }
    }
    /**
     * 检查电子邮件
     * @param type       判断是新增0或者修改1
     * @param sysUserDto 用户dto对象
     * @return 成功或失败的结果集
     */
    @Override
    public Boolean checkEmail(int type, UserDto sysUserDto) {
        SysUser sysUser = this.baseMapper.checkEmail(sysUserDto.getEmail());
        if (type == 0) {
            return sysUser == null || sysUser.getUserId() == null;
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else {
                return sysUser.getUserId().equals(sysUserDto.getUserId());
            }
        }
    }
    /**
     * 检查用户名
     *
     * @param type    判断是新增0或者修改1
     * @param userDto 用户dto对象
     * @return 成功或失败的结果集
     */
    @Override
    public Boolean checkUserName(int type, UserDto userDto) {
        SysUser sysUser = this.baseMapper.checkUserName(userDto.getUserName());
        if (type == 0) {
            return sysUser == null || sysUser.getUserId() == null;
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else {
                return sysUser.getUserId().equals(userDto.getUserId());
            }
        }
    }
    /**
     * 重置密码
     * @param sysUser 用户对象
     * @return {@code Result}
     */
    @Override
    public Result resetPassword(SysUser sysUser) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        SysUser user = this.baseMapper.getUserById(sysUser.getUserId() + "");
        if (!sysUser.getPassword().equals(user.getPassword())) {
            this.baseMapper.updateUser(sysUser);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } else {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_SAME_PASSWORD));
        }
        return result;
    }
}

