package com.zy_admin.community.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.CommunityExcel;
import com.zy_admin.community.dto.ZyCommunityInteractionDto;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 社区互动Controller
 *
 * @author yb14672
 * @date 2022/11/23 - 20:58
 */
@Api(value = "/system/interaction", tags = {""})
@RestController
@RequestMapping("/system/interaction")
public class ZyWebInteractionController {
    /**
     * 服务对象
     */
    @Resource
    private ZyCommunityInteractionService zyCommunityInteractionService;


    /**
     * 根据ID导出Excel
     *
     * @param ids      id
     * @param response 响应
     * @return {@link Result}
     * @throws IOException ioException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "ArrayList<String>", name = "ids", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "响应", required = true)
    })
    @ApiOperation(value = "根据ID导出Excel", notes = "根据ID导出Excel", httpMethod = "GET")
    @GetMapping("/export")
    public Result getExcel(@RequestParam("ids") ArrayList<String> ids, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        Result result1 = zyCommunityInteractionService.getListByIdList(ids);
        List<ZyCommunityInteractionDto> list= (List<ZyCommunityInteractionDto>) result1.getData();
        String fileName = URLEncoder.encode("互动信息", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), CommunityExcel.class)
                .excelType(ExcelTypeEnum.XLS)
                //自适应表格格式
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .autoCloseStream(true)
                .sheet("互动信息");
        excel.doWrite(list);
        result.setData(excel);
        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
        return result;
    }

    /**
     * 分页查询所有数据
     * @param page                   分页对象
     * @param interactionDto 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "ZyCommunityInteractionDto", name = "interactionDto", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    public Result selectAll(Page page, ZyCommunityInteractionDto interactionDto) {
        return this.zyCommunityInteractionService.selectAllLimit(page, interactionDto);
    }

//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "path", dataType = "Serializable", name = "id", value = "主键", required = true)
//    })
//    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
//    @GetMapping("{id}")
//    public R selectOne(@PathVariable Serializable id) {
//        return success(this.zyCommunityInteractionService.getById(id));
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param zyCommunityInteraction 实体对象
//     * @return 新增结果
//     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "body", dataType = "ZyCommunityInteraction", name = "zyCommunityInteraction", value = "实体对象", required = true)
//    })
//    @ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
//    @PostMapping
//    public R insert(@RequestBody ZyCommunityInteraction zyCommunityInteraction) {
//        return success(this.zyCommunityInteractionService.save(zyCommunityInteraction));
//    }
//
//    /**
//     * 修改数据
//     *
//     * @param zyCommunityInteraction 实体对象
//     * @return 修改结果
//     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "body", dataType = "ZyCommunityInteraction", name = "zyCommunityInteraction", value = "实体对象", required = true)
//    })
//    @ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "PUT")
//    @PutMapping
//    public R update(@RequestBody ZyCommunityInteraction zyCommunityInteraction) {
//        return success(this.zyCommunityInteractionService.updateById(zyCommunityInteraction));
//    }
//
    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "List<String>", name = "idList", value = "主键结合", required = true)
    })
    @ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "DELETE")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<String> idList) {
        return this.zyCommunityInteractionService.deleteInteractionByIdList(idList);
    }
}
