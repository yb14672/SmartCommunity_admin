package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.service.ZyOwnerRoomService;
import com.zy_admin.util.Result;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerRoom)表控制层
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@RestController
@RequestMapping("zyOwnerRoom")
public class ZyOwnerRoomController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ZyOwnerRoomService zyOwnerRoomService;

    /**
     * 新增数据
     *
     * @param ownerRoom 实体对象
     * @return 新增结果
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody ZyOwnerRoom ownerRoom, HttpServletRequest request) throws Exception {
        return this.zyOwnerRoomService.ownerInsert(ownerRoom,request);
    }

    /**
     *
     * @return
     */
    @GetMapping("/getTree")
    public Result getTreeData(){
        return this.zyOwnerRoomService.getTreeData();
    }

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param zyOwnerRoom 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ZyOwnerRoom> page, ZyOwnerRoom zyOwnerRoom) {
        return success(this.zyOwnerRoomService.page(page, new QueryWrapper<>(zyOwnerRoom)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.zyOwnerRoomService.getById(id));
    }

    /**
     * 修改数据
     *
     * @param zyOwnerRoom 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ZyOwnerRoom zyOwnerRoom) {
        return success(this.zyOwnerRoomService.updateById(zyOwnerRoom));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.zyOwnerRoomService.removeByIds(idList));
    }
}

