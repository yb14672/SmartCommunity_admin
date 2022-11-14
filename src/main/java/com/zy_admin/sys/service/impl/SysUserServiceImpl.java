package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.dto.SysUserDto;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDeptService;
import com.zy_admin.sys.service.SysPostService;
import com.zy_admin.sys.service.SysRoleService;
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

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;
    @Autowired
    private SysPostService sysPostService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 下载模板
     *
     * @return
     */
    @Override
    public List<SysUser> uploadUser() {
        return null;
    }

    /**
     * 默认是导出全部
     *
     * @return
     */
    @Override
    public List<SysUser> getUserLists() {
        return sysUserDao.getUserLists();
    }

    /**
     * 导出选中的部分
     *
     * @param userIds
     * @return
     */
    @Override
    public List<SysUser> queryUserById(ArrayList<Integer> userIds) {
        //        如果有选中列表，就执行导出多个
        if (userIds != null) {
            userIds = userIds.size() == 0 ? null : userIds;
        }
        return sysUserDao.queryUserById(userIds);
    }

    //    导入
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
        for (int i = 1; i < rowNumber + 1; i++) {
// 判断当前行是否为空行
            if (!judgeRow(sheet.getRow(i))) {
                emptyRow++;
                continue;
            }
            SysUser userEntity = new SysUser();
// 添加姓名
            checkRequire(i, 0, sheet.getRow(i));
            userEntity.setUserName(getCellValue(sheet.getRow(i).getCell(0)));
//状态为0能渲染
            userEntity.setDelFlag("0");
//添加昵称
            userEntity.setNickName(getCellValue(sheet.getRow(i).getCell(1)));
// 添加手机号 colNum是列数
            if (!checkRequire(i, 3, sheet.getRow(i))) {
                result.setMeta(ResultTool.fail(ResultCode.DATA_REPEAT));
                return result;
            }
            if (!checkRepeat(i, 3, phoneNumber, getCellValue(sheet.getRow(i).getCell(3)))) {
                result.setMeta(ResultTool.fail(ResultCode.FILE_REPEAT));
                return result;
            }
            if (!checkPhoneNumber(i, 3, getCellValue(sheet.getRow(i).getCell(3)))) {
                result.setMeta(ResultTool.fail(ResultCode.USER_TELREPEAT));
                return result;
            }
            userEntity.setPhonenumber(getCellValue(sheet.getRow(i).getCell(3)));
// 添加部门
            if (sheet.getRow(i).getCell(2) != null && sheet.getRow(i).getCell(2).getCellType() != CellType.BLANK) {
//                QueryWrapper 是mybatisplus的构造器，
                QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("dept_name", getCellValue(sheet.getRow(i).getCell(2)));
                List<SysDept> list = sysDeptService.list(queryWrapper);
                if (list.size() != 0) {
                    userEntity.setDeptId(list.get(0).getDeptId());
                }
            }
// 添加昵称
            if (sheet.getRow(i).getCell(1) != null) {
                userEntity.setNickName(getCellValue(sheet.getRow(i).getCell(1)));
            }
            userEntity.setPassword("88888888");
            userEntityList.add(userEntity);
            phoneNumber.add(getCellValue(sheet.getRow(i).getCell(3)));
        }
        if (emptyRow != rowNumber - 1) {
            if (sysUserService.saveBatch(userEntityList)) {
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
        System.out.println("sysUserService.list(queryWrapper).size()" + sysUserService.list(queryWrapper).size());
        if (sysUserService.list(queryWrapper).size() > 0) {
            return false;
        }
        return true;
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
        Result result = new Result();
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
        SysUser user = sysUserDao.login(sysUser);
        String jwtToken = "";
        if (user != null) {
            jwtToken = JwtUtils.getJwtToken(user.getUserId() + "", user.getNickName());
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
        Result result = new Result();
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
        Result result = new Result();
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
        Result result = new Result();
//判断是否有查到对应用户
        if (personal == null) {
            result.setJsonResult(ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST));
        } else {
            result.setData(personal);
            result.setJsonResult(ResultTool.success(ResultCode.SUCCESS));
        }
        return result;
    }

    @Override
    public Result updateUser(SysUser user) {
        int i = baseMapper.updateUser(user);
        Result result = new Result();
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
        Result result = new Result();
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
}

