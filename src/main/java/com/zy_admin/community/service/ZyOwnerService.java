package com.zy_admin.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.OwnerRoomExcel;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.common.core.Result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 业主 (ZyOwner)表服务接口
 *
 * @author makejava
 * @date 2022/11/22
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerService extends IService<ZyOwner> {

    /**
     * 用户修改头像
     * @param owner 头像地址
     * @param request 请求
     * @return 修改结果
     */
    Result updateOwnerPortrait(ZyOwner owner,HttpServletRequest request);

    /**
     * 修改密码
     * @param owner 新密码
     * @param request 请求
     * @return {@link Result}
     */
    Result updatePassword(ZyOwner owner,HttpServletRequest request);

    /**
     * 通过id获取业主
     *
     * @param ownerId 业主id
     * @return {@link Result}
     */
    Result getOwnerById(String ownerId);

    /**
     * 检查电话号码唯一
     *
     * @param type    类型
     * @param owner 业主
     * @return boolean
     */
    boolean checkPhoneNumberUnique(int type,ZyOwner owner);

    /**
     * 检查身份证号是否唯一
     * @param zyOwner 业主
     * @return boolean
     */
    boolean checkIdCardUnique(ZyOwner zyOwner);

    /**
     * 业主个人信息设置
     * @param owner 需要修改的信息
     * @return 修改结果
     */
    Result ownerUpdate(ZyOwner owner);

    /**
     * 业主登录
     * @param owner 账号密码
     * @return 封装结果集
     */
    Result ownerLogin(ZyOwner owner);

    /**
     * 业主注册
     * @param owner 注册信息
     * @return 是否成功结果集
     * @throws Exception
     */
    Result ownerRegister(ZyOwner owner) throws Exception;

    /**
     * 得到业主名单
     * 获取户主信息并分页
     *
     * @param zyOwner  户主信息
     * @param pageable 分页对象
     * @param communityId 小区id
     * @return {@link Result}
     */
    Result getOwnerList(ZyOwner zyOwner, Pageable pageable,String communityId);

    /**
     * 解绑房间
     *
     * @param request    请求
     * @param owenRoomId 房主房间id
     * @return {@link Result}
     */
    Result deleteOwenRome(HttpServletRequest request, String owenRoomId);

    /**
     * 得到列表
     *
     * @return {@link List}<{@link OwnerRoomExcel}>
     */
    List<OwnerRoomExcel> getLists();

    /**
     * 查询所有者通过id
     *
     * @param userIds 用户id
     * @return {@link List}<{@link OwnerRoomExcel}>
     */
    List<OwnerRoomExcel>queryOwnerById(List<String> userIds);



}

