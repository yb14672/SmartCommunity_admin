package com.zy_admin.community.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dto.ZyCommunityInteractionDto;
import com.zy_admin.community.service.ZyCommunityInteractionService;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(value = "/system/interaction", tags = {"社区互动Controller"})
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
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "communityId", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "响应", required = true)
    })
    @ApiOperation(value = "根据ID导出Excel", notes = "根据ID导出Excel", httpMethod = "GET")
    @GetMapping("/export")
    @MyLog(title = "互动信息", optParam = "#{ids}", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAnyAuthority('system:interaction:export')")
    public Result getExcel(@RequestParam("ids") ArrayList<String> ids, String communityId, HttpServletResponse response) throws IOException {
        Result result = new Result(null, ResultTool.fail(ResultCode.COMMON_FAIL));
        Result result1 = zyCommunityInteractionService.getListByIdList(ids, communityId);
        List<ZyCommunityInteractionDto> list = (List<ZyCommunityInteractionDto>) result1.getData();
        String fileName = URLEncoder.encode("互动信息", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // 内容样式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        ExcelWriterSheetBuilder excel = EasyExcel.write(response.getOutputStream(), ZyCommunityInteractionDto.class)
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
     *
     * @param page           分页对象
     * @param interactionDto 查询实体
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Page", name = "page", value = "分页对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "ZyCommunityInteractionDto", name = "interactionDto", value = "查询实体", required = true)
    })
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据", httpMethod = "GET")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_common','ROLE_admin')")
    public Result selectAll(Page page, ZyCommunityInteractionDto interactionDto) {
        return this.zyCommunityInteractionService.selectAllLimit(page, interactionDto);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "主键", required = true)
    })
    @ApiOperation(value = "通过主键查询单条数据", notes = "通过主键查询单条数据", httpMethod = "GET")
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('system:interaction:query')")
    public Result selectOne(@PathVariable String id) {
        return this.zyCommunityInteractionService.getInteractionInfoById(id);
    }


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
    @MyLog(title = "互动信息", optParam = "#{idList}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAnyAuthority('system:interaction:remove')")
    public Result delete(@RequestParam("ids") List<String> idList) throws Exception {
        return this.zyCommunityInteractionService.deleteInteractionByIdList(idList);
    }
}
