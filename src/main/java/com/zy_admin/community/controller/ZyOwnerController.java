package com.zy_admin.community.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.OwnerRoomExcel;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyOwnerService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.ExcelUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import org.apache.ibatis.annotations.Select;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param zyOwner 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyOwner> page, ZyOwner zyOwner) {
        return success(this.zyOwnerService.page(page, new QueryWrapper<>(zyOwner)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyOwnerService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param zyOwner 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyOwner zyOwner) {
        return success(this.zyOwnerService.save(zyOwner));
    }

    /**
     * 修改数据
     *
     * @param zyOwner 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyOwner zyOwner) {
        return success(this.zyOwnerService.updateById(zyOwner));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyOwnerService.removeByIds(idList));
    }

    /**
     * 获取户主信息并分页
     * @param zyOwner 户主信息
     * @param pageable 页码
     * @return {@link Result}
     */

    @GetMapping("/getOwnerList")
    public Result getOwnerList(ZyOwner zyOwner, Pageable pageable){
        Result ownerList = zyOwnerService.getOwnerList(zyOwner, pageable);
        System.out.println(ownerList);
        return ownerList;

    }


    /**
     * 删除欧文罗马
     *
     * @param request     请求
     * @param ownerRoomId 房主id
     * @return {@link Result}
     */
    @MyLog(title = "房主信息", optParam = "#{ownerRoomId}", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteOwner")
    public Result deleteOwenRome(HttpServletRequest request, String ownerRoomId){
        System.out.println(ownerRoomId);
       return zyOwnerService.deleteOwenRome(request,ownerRoomId);
    }

    /**
     * 得到excle表格
     *
     * @param ids      id
     * @param response 响应
     * @return {@link Result}
     * @throws IOException ioexception
     */
    @GetMapping("/getExcel")
    @MyLog(title = "房主信息", optParam = "#{ids}", businessType = BusinessType.EXPORT)
    public Result getExcel(@RequestParam("ownerIds") ArrayList<String> ids, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        System.out.println(ids);
        List<OwnerRoomExcel> ownerRoomExcelList = new ArrayList<>();
        //如果前台传的集合为空或者长度为0.则全部导出。
        if (ids == null || ids.size() == 0) {
            ownerRoomExcelList = zyOwnerService.getLists();
        } else {
            //执行查询用户列表的sql语句
            ownerRoomExcelList = zyOwnerService.queryOwnerById(ids);
        }
        String fileName = URLEncoder.encode("房主信息表", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtil.getContentStyle();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), OwnerRoomExcel.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("用户信息");
        excel.doWrite(ownerRoomExcelList);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }
}

