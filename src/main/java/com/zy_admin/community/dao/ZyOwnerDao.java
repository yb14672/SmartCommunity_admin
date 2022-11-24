package com.zy_admin.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.community.entity.ZyOwner;
import org.apache.ibatis.annotations.Select;

/**
 * 业主 (ZyOwner)表数据库访问层
 *
 * @author makejava
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
}

