package com.zy_admin.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy_admin.common.Pageable;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.community.entity.ZyOwnerRoom;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import com.zy_admin.community.service.ZyOwnerRoomService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.Result;
import org.springframework.transaction.annotation.Transactional;
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

    @Resource
    private RequestUtil requestUtil;

    /**
     * 修改业主审核的状态为审核失败
     * @return
     */
    @PutMapping("/updateOwnerRoomStatusReject")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "业主审核", optParam = "#{zyOwnerRoom}", businessType = BusinessType.UPDATE)
    public Result updateOwnerRoomStatusReject(@RequestBody ZyOwnerRoom zyOwnerRoom, ZyOwnerRoomRecord zyOwnerRoomRecord, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyOwnerRoom.setUpdateBy(user.getUserName());
        return zyOwnerRoomService.updateOwnerRoomStatusReject(zyOwnerRoom,zyOwnerRoomRecord,request);
    }

    /**
     * 修改业主审核的状态为绑定
     * @return
     */
    @PutMapping("/updateOwnerRoomStatusBinding")
    @Transactional(rollbackFor = Exception.class)
    @MyLog(title = "业主审核", optParam = "#{zyOwnerRoom}", businessType = BusinessType.UPDATE)
    public Result updateOwnerRoomStatusBinding(@RequestBody ZyOwnerRoom zyOwnerRoom,ZyOwnerRoomRecord zyOwnerRoomRecord, HttpServletRequest request) throws Exception {
        SysUser user = requestUtil.getUser(request);
        zyOwnerRoom.setUpdateBy(user.getUserName());
        return zyOwnerRoomService.updateOwnerRoomStatusBinding(zyOwnerRoom,zyOwnerRoomRecord,request);
    }

    /**
     * 分页和查询业主审核
     * @param zyOwnerRoom
     * @param pageable
     * @return
     */
    @GetMapping("selectAllOwnerRoomLimit")
    public Result selectAllOwnerRoomLimit(ZyOwnerRoom zyOwnerRoom, Pageable pageable){
        Result result = zyOwnerRoomService.selectAllOwnerRoomLimit(zyOwnerRoom,pageable);
        return result;
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
     * 新增数据
     *
     * @param zyOwnerRoom 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ZyOwnerRoom zyOwnerRoom) {
        return success(this.zyOwnerRoomService.save(zyOwnerRoom));
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

