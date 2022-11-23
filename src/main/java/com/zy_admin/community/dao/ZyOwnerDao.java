package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.community.dto.OwnerListDto;
import com.zy_admin.community.dto.OwnerRoomExcel;
import com.zy_admin.community.entity.ZyOwner;
import com.zy_admin.community.entity.ZyOwnerRoomRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * zy所有者刀
 * 业主 (ZyOwner)表数据库访问层
 *
 * @author makejava
 * @date 2022/11/22
 * @since 2022-11-01 19:49:02
 */
public interface ZyOwnerDao extends BaseMapper<ZyOwner> {

    /**
     * 业主个人信息设置
     * @param owner 修改的信息
     * @return 修改影响的行数
     */
    Integer ownerUpdate(ZyOwner owner);

    /**
     * 用户登录
     * @param owner 手机号与密码
     * @return 业主对象
     */
    @Select("select * from zy_owner where owner_phone_number = #{ownerPhoneNumber} and owner_password = #{ownerPassword}")
    ZyOwner ownerLogin(ZyOwner owner);

    /**
     * 注册验重
     * @param phoneNumber 手机号
     * @return 业主对象
     */
    @Select("select * from zy_owner where owner_phone_number = #{phoneNumber}")
    ZyOwner checkPhoneNumber(String phoneNumber);

    /**
     * @param zyOwner  户主信息
     * @param pageable 页码
     * @return {@link List}<{@link OwnerListDto}>
     */
    List<OwnerListDto> getOwnerList(@Param("zyOwner") ZyOwner zyOwner, @Param("pageable") Pageable pageable);

    /**
     * 数
     * 获取总数据量
     *
     * @param zyOwner zy所有者
     * @return long
     */
    long count( ZyOwner zyOwner);

    /**
     * 删除业主房间id
     * 解绑业主
     *
     * @param ownerRoomId 业主房间id
     */
    @Delete("delete from zy_owner_room where owner_room_id = #{ownerRoomId}")
    void deletOwnerRoomId(String ownerRoomId);

    /**
     * 得到业主房间
     * 获取被解绑房屋的所有信息
     *
     * @param ownerRoomId 业主房间id
     * @return {@link ZyOwnerRoomRecord}
     */
    @Select("select * from zy_owner_room where owner_room_id = #{ownerRoomId}")
    ZyOwnerRoomRecord getZyOwnerRoom(String ownerRoomId);

    /**
     * 更新到房间记录
     *
     * @param zyOwnerRoomRecord 业主房间记录
     */
    void updateIntoRoomRecord(ZyOwnerRoomRecord zyOwnerRoomRecord);


    /**
     * 房主列表
     *
     * @return {@link List}<{@link OwnerRoomExcel}>
     */
    List<OwnerRoomExcel> getOwenLists();

    /**
     * 查询所有者通过id
     *
     * @param OwnerIds 所有者id
     * @return {@link List}<{@link OwnerRoomExcel}>
     */
    List<OwnerRoomExcel> queryOwnerById(@Param("OwnerIds") List<String> OwnerIds);
}

