package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.service.ZyOwnerService;
import com.zy_admin.util.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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
     * @return
     */

    @GetMapping("/getOwnerList")
    public Result getOwnerList(ZyOwner zyOwner, Pageable pageable){
        Result ownerList = zyOwnerService.getOwnerList(zyOwner, pageable);
        System.out.println(ownerList);
        return ownerList;

    }

    /**
     * 解绑
     * @param ownerRoomId
     * @return
     */
    @DeleteMapping("/deleteOwner")
    public Result deleteOwenRome(HttpServletRequest request, String ownerRoomId){
        System.out.println(ownerRoomId);
       return zyOwnerService.deleteOwenRome(request,ownerRoomId);
    }
}

