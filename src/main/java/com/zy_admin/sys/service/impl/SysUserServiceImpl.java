package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dao.SysDeptDao;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.dao.SysUserRoleDao;
import com.zy_admin.sys.dto.*;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.RedisService;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtils;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Resource
    private RedisService redisService;
    @Autowired
    private SysDeptDao sysDeptDao;

    /**
     * 删除用户
     *
     * @param idList
     * @return
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
     * 分页插询
     *
     * @param pageable
     * @param sysUser
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Result selectUsers(Pageable pageable, SysUser sysUser, String startTime, String endTime) {
        Result result = new Result();
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
            pageable.setPageNum(pageable.getPageNum() > pages ? pages : pageable.getPageNum());
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
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    @Transactional
    public Result insertAuthRole(Integer userId, String roleId) throws Exception {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        //先删除原本用户拥有的所有角色
        int i = this.sysUserRoleDao.deleteByUserId(userId + "");
        if (i == 0) {
            throw new Exception("修改用户角色时出错，请稍后再试");
        }
        //再插入修改后的所有角色
        int i1 = this.sysUserRoleDao.insertBatchByRoleId(userId + "", roleId);
        if (i1 < 1) {
            throw new Exception("修改用户角色时出错，请稍后再试");
        }
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 根据用户ID获取其信息和对应的角色
     *
     * @param userId
     * @return
     */
    @Override
    public Result authRole(Long userId) {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        UserRoleDto userRoleDto = this.baseMapper.authRole(userId);
        if (userRoleDto != null || userRoleDto.getUserId() != null) {
            result.setData(userRoleDto);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    /**
     * 下载模板
     *
     * @return
     */
    @Override
    public List<SysUser> uploadUserTemplate() {
        return null;
    }

    /**
     * 默认是导出全部
     *
     * @return
     */
    @Override
    public List<SysUser> getUserLists() {
        return baseMapper.getUserLists();
    }

    /**
     * 导出选中的部分
     *
     * @param userIds
     * @return
     */
    @Override
    public List<SysUser> queryUserById(ArrayList<Integer> userIds) {
        //如果有选中列表，就执行导出多个
        userIds = userIds.size() == 0 ? null : userIds;
        return baseMapper.queryUserById(userIds);
    }

    /**
     * 导入
     *
     * @param file
     * @return
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
        Integer rowNumber = sheet.getLastRowNum();
        Integer emptyRow = 0;
        String errorMsg = "";
        for (int i = 1; i < rowNumber + 1; i++) {
            // 判断当前行是否为空行
            if (!judgeRow(sheet.getRow(i))) {
                emptyRow++;
                continue;
            }
            SysUser userEntity = new SysUser();
            // 添加姓名
            checkRequire(i, 1, sheet.getRow(i));
            userEntity.setUserName(getCellValue(sheet.getRow(i).getCell(1)));
            // 添加邮箱
            userEntity.setEmail(getCellValue(sheet.getRow(i).getCell(3)));
            // 验证用户名重复
            if (!checkUserName(i, 1, getCellValue(sheet.getRow(i).getCell(1)))) {
                result.setMeta(ResultTool.fail(ResultCode.USERNAME_REPEAT));
                errorMsg += "第" + i + "条用户名重复,";
            }
//            判断为空
            if (!checkRequire(i, 1, sheet.getRow(i))) {
                result.setMeta(ResultTool.fail(ResultCode.MASSAGE_NULL));
                errorMsg += "第" + i + "条用户名为空,";
            }
            if (!checkRequire(i, 2, sheet.getRow(i))) {
                result.setMeta(ResultTool.fail(ResultCode.MASSAGE_NULL));
                errorMsg += "第" + i + "条昵称为空,";
            }
            if (!checkRequire(i, 3, sheet.getRow(i))) {
                result.setMeta(ResultTool.fail(ResultCode.MASSAGE_NULL));
                errorMsg += "第" + i + "条邮箱为空,";
            }
            if (!checkRequire(i, 4, sheet.getRow(i))) {
                result.setMeta(ResultTool.fail(ResultCode.MASSAGE_NULL));
                errorMsg += "第" + i + "条手机号为空,";
            }
            // 验证邮箱重复
            if (!checkEmail(i, 3, getCellValue(sheet.getRow(i).getCell(3)))) {
                result.setMeta(ResultTool.fail(ResultCode.EMAIL_REPEAT));
                errorMsg += "第" + i + "条邮箱重复,";
            }
//            验证邮箱正则
            if (!checkEmailJudge(i, 3, getCellValue(sheet.getRow(i).getCell(3)))) {
                result.setMeta(ResultTool.fail(ResultCode.EMAIL_NON_COMPLIANCE));
                errorMsg += "第" + i + "条邮箱不符合规则,";
            }
            //状态为0能渲染
            userEntity.setDelFlag("0");
            //添加昵称
            userEntity.setNickName(getCellValue(sheet.getRow(i).getCell(2)));
            // 添加部门
            userEntity.setDeptId(Long.valueOf(getCellValue(sheet.getRow(i).getCell(0))));
            // 添加手机号 colNum是列数
            if (!checkRequire(i, 4, sheet.getRow(i))) {
                result.setMeta(ResultTool.fail(ResultCode.DATA_REPEAT));
            }
            if (!checkRepeat(i, 4, phoneNumber, getCellValue(sheet.getRow(i).getCell(4)))) {
                result.setMeta(ResultTool.fail(ResultCode.FILE_REPEAT));
            }
            if (!checkPhoneNumber(i, 4, getCellValue(sheet.getRow(i).getCell(4)))) {
                result.setMeta(ResultTool.fail(ResultCode.USER_TELREPEAT));
                errorMsg += "第" + i + "条电话号重复,";
            }
            if (!checkPhoneNumberJudge(i, 4, getCellValue(sheet.getRow(i).getCell(4)))) {
                result.setMeta(ResultTool.fail(ResultCode.USER_TELREPEAT));
                errorMsg += "第" + i + "条电话号不符合规范,";
            }
            //思路:拿一个map去存键值，键是索引，值是内容，然后去遍历通过索引去取值，然后再前端遍历
            userEntity.setPhonenumber(getCellValue(sheet.getRow(i).getCell(4)));
            // 添加部门
            if (sheet.getRow(i).getCell(0) != null && sheet.getRow(i).getCell(0).getCellType() != CellType.BLANK) {
                //QueryWrapper 是mybatisplus的构造器，
                QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("dept_name", getCellValue(sheet.getRow(i).getCell(0)));
                List<SysDept> list = sysDeptDao.selectList(queryWrapper);
                if (list.size() != 0) {
                    userEntity.setDeptId(list.get(0).getDeptId());
                }
            }
            if (sheet.getRow(i).getCell(2) != null) {
                userEntity.setNickName(getCellValue(sheet.getRow(i).getCell(2)));
            }
            userEntity.setDeptId(100L);
            userEntity.setPassword("88888888");
            userEntityList.add(userEntity);
            phoneNumber.add(getCellValue(sheet.getRow(i).getCell(4)));
        }
        if (!"".equals(errorMsg)) {
            result.setData(errorMsg);
            return result;
        }
        if (emptyRow != rowNumber - 1) {
            if (baseMapper.saveBatch(userEntityList)) {
                result.setMeta(ResultTool.success(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.USER_REPEAT));
            }
        }
        return result;
    }

    /**
     * 验证手机号不能重复
     *
     * @param rowNum
     * @param colNum
     * @param stringCellValue
     */
    private boolean checkPhoneNumber(int rowNum, int colNum, String stringCellValue) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phonenumber", stringCellValue);
        if (baseMapper.selectList(queryWrapper).size() > 0) {
            return false;
        }
        return true;
    }
    private boolean checkPhoneNumberJudge(int rowNum, int colNum, String stringCellValue) {
        //判断手机号的正则
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stringCellValue);
        return m.matches();
    }

    /**
     * 验证用户名不能重复
     *
     * @param rowNum
     * @param colNum
     * @param stringCellValue
     */
    private boolean checkUserName(int rowNum, int colNum, String stringCellValue) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", stringCellValue);
        if (baseMapper.selectList(queryWrapper).size() > 0) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(stringCellValue);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 验证邮箱不能重复
     *
     * @param rowNum
     * @param colNum
     * @param stringCellValue
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
     * 邮箱判断正则
     * @param rowNum
     * @param colNum
     * @param stringCellValue
     * @return
     */
    private boolean checkEmailJudge(int rowNum, int colNum, String stringCellValue) {
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(stringCellValue);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 检查导入文件是重复
     *
     * @param rowNum
     * @param colNum
     * @param phoneNumber
     * @param stringCellValue
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
     * 检验必填项 数据不能为空
     *
     * @param rowNum
     * @param colNum
     * @param row
     */
    private boolean checkRequire(int rowNum, int colNum, Row row) {
        Cell cell = row.getCell(colNum);
        if (cell == null || CellType.BLANK.equals(cell.getCellType())) {
            return false;
        }
        return true;
    }

    /**
     * 判断当前行数是否为空行数
     *
     * @param row
     * @return
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
            if (blankRowNum != 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 得到单元格值
     *
     * @param cell
     * @return
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
        } catch (ClassCastException e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.PARAM_TYPE_ERROR));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
        } finally {
            return result;
        }
    }

    @Override
    public Result login(SysUser sysUser) {
        SysUser user = baseMapper.login(sysUser);
        System.err.println(user);
        String jwtToken = "";
        if (user != null) {
            jwtToken = JwtUtils.getJwtToken(user.getUserId() + "", user.getNickName());
            redisService.set(jwtToken, sysUser.getUserName());
            if ("1".equals(user.getStatus())) {
                return new Result(jwtToken, ResultTool.fail(ResultCode.USER_ACCOUNT_LOCKED));
            }
            return new Result(jwtToken, ResultTool.success(ResultCode.SUCCESS));
        }
        return new Result(jwtToken, ResultTool.fail(ResultCode.USER_WRONG_ACCOUNT_OR_PASSWORD));
    }

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

    @Override
    public Result updateUser(SysUser user) {
        int i = baseMapper.updateUser(user);
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        if (i == 1) {
            result.setData("用户ID为" + user.getUserId() + "的信息修改成功");
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } else {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        }
        return result;
    }

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
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            } else {
                result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_SAME_PASSWORD));
        }
        return result;
    }

    @Override
    public Result selectAll(Page page, SysUser sysUser) {
        return null;
    }


    /**
     * 新增用户
     *
     * @param sysUserDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertUser(UserDto sysUserDto) {
        Result result = new Result();
        if (checkUserName(0, sysUserDto)) {
            if (checkNiceName(0, sysUserDto)) {
                if (checkPhone(0, sysUserDto)) {
                    if (checkEmail(0, sysUserDto)) {
                        if(sysUserDto.getDeptId()==null) {
                            sysUserDto.setDeptId(100L);
                        }
                        this.baseMapper.insertUser(sysUserDto);
                        if (sysUserDto.getPostId()!=null) {
                            this.baseMapper.insertPost(sysUserDto.getUserId(), sysUserDto.getPostId());
                            return result;
                        }
                        if (sysUserDto.getRoleId()!=null) {
                            this.baseMapper.insertRole(sysUserDto.getUserId(), sysUserDto.getRoleId());
                            return result;
                        }
                        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
                        return result;
                    } else {
                        result.setMeta(ResultTool.fail(ResultCode.REPEAT_EMAIL));
                        return result;
                    }
                } else {
                    result.setMeta(ResultTool.fail(ResultCode.REPEAT_PHONENUMBER));
                    return result;
                }
            } else {
                result.setMeta(ResultTool.fail(ResultCode.REPEAT_NICK_NAME));
                return result;
            }
        } else {
            result.setMeta(ResultTool.fail(ResultCode.REPEAT_USER_NAME));
            return result;
        }
    }

    /**
     * 管理员修改用户
     *
     * @param userDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result adminUpdateUser(UserDto userDto) {
        Result result = new Result();
        SysUserDto user = this.baseMapper.personal(userDto.getUserId() + "");
        if (checkEquals(user, userDto)) {
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

    @Override
    public Boolean checkNiceName(int type, UserDto sysUserDto) {
        SysUser sysUser = this.baseMapper.checkNiceName(sysUserDto.getNickName());
        if (type == 0) {
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysUser.getUserId().equals(sysUserDto.getUserId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkPhone(int type, UserDto sysUserDto) {
        SysUser sysUser = this.baseMapper.checkPhone(sysUserDto.getPhonenumber());
        if (type == 0) {
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysUser.getUserId().equals(sysUserDto.getUserId())) {
                return false;
            }
            return true;
        }
        return false;
    }


    @Override
    public Boolean checkEmail(int type, UserDto sysUserDto) {
        SysUser sysUser = this.baseMapper.checkEmail(sysUserDto.getEmail());
        if (type == 0) {
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysUser.getUserId().equals(sysUserDto.getUserId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkUserName(int type, UserDto userDto) {
        SysUser sysUser = this.baseMapper.checkUserName(userDto.getUserName());
        if (type == 0) {
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
            }
        } else {
            //修改时--先判断是否为空，为空则不重名，即唯一
            if (sysUser == null || sysUser.getUserId() == null) {
                return true;
                //判断ID是否一致，若否，则重名，即不唯一
            } else if (!sysUser.getUserId().equals(userDto.getUserId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 重置密码
     *
     * @param sysUser
     * @return
     */
    @Override
    public Result resetPassword(SysUser sysUser) {
        Result result = new Result();
        SysUser user = this.baseMapper.getUserById(sysUser.getUserId() + "");

        if (!sysUser.getPassword().equals(user.getPassword())) {
            this.baseMapper.updateUser(sysUser);
            result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        } else {
            result.setMeta(ResultTool.fail(ResultCode.USER_ACCOUNT_SAME_PASSWORD));
        }
        return result;
    }

    public boolean checkEquals(SysUserDto user, UserDto userDto) {
        if (user.getSysUser().getNickName().equals(userDto.getNickName())) {
            if (user.getSysUser().getPhonenumber().equals(userDto.getPhonenumber())) {
                if (user.getSysUser().getEmail().equals(userDto.getEmail())) {
                    if (user.getSysUser().getDeptId().equals(userDto.getDeptId())) {
                        if (user.getSysUser().getSex().equals(userDto.getSex())) {
                            if (user.getSysUser().getStatus().equals(userDto.getStatus())) {
                                if (user.getSysRole().getRoleId() != null && userDto.getRoleId() != null) {
                                    if (user.getSysRole().getRoleId() == Long.parseLong(userDto.getRoleId() + "")) {
                                        if (user.getSysPost().getPostId() == Long.parseLong(userDto.getPostId() + "")) {
                                            if (user.getSysUser().getRemark() != null) {
                                                if (userDto.getRemark() != null||!"".equals(userDto.getRemark())) {
                                                    if (user.getSysUser().getRemark().equals(userDto.getRemark())) {
                                                        return true;
                                                    }
                                                }
                                            } else {
                                                if (userDto.getRemark() == null||"".equals(userDto.getRemark())) {
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

