package com.zy_admin.community.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.zy_admin.community.dto.OwnerParkExcelDto;
import com.zy_admin.community.dto.OwnerParkListDto;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.util.RoomTree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * 房屋绑定表 (ZyOwnerPark)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-01 15:50:35
 */
public interface ZyOwnerParkDao extends MPJBaseMapper<ZyOwnerPark> {

    /**
     * 根据ID集合查询车位列表列表
     * @param ids id集合
     * @return 车位列表
     */
    List<OwnerParkExcelDto> getDtoList(@Param("ids") ArrayList<String> ids,String communityId);

    /**
     * 根据小区id查询车位列表
     * @param communityId 小区id
     * @return 车位列表
     */
    List<OwnerParkExcelDto> getAllDtoList(String communityId);

    /**
     * 根据parkId查车位状态
     * @param parkId 车位id
     * @return 状态
     */
    @Select("select park_owner_status from zy_owner_park where park_id = #{parkId}")
    String selectParkOwnerStatus(String parkId);



    /**
     * 解绑停车位
     *
     * @param ownerParkId 业主停车位Id;
     */
    @Update("Update  zy_owner_park set park_owner_status = 'Unbinding' where owner_park_id = #{ownerParkId} and del_flag = 0")
    void deleteOwnerPark(String ownerParkId);

    /**
     * 查询车牌号有没有重复
     * @param carNumber 车牌号
     * @return
     */
    ZyOwnerPark selectCarNumber(String carNumber);

    /**
     *
     * 查询未被绑定和启用0的车位
     *
     * @param ownerId 所有者id
     * @return {@link List}<{@link ZyOwnerPark}>
     */
    @Select("select community_id id,community_name name,0 parent_id from zy_community c where exists (select * from (select community_id from zy_owner_room where owner_id = #{ownerId} and room_status = 'Binding' GROUP BY community_id) co where c.community_id = co.community_id) union select z.park_id id, z.park_code name ,z.community_id parent_id from zy_park z where  not exists (select * from zy_owner_park where z.park_id = owner_park_id) and exists (select community_id from zy_owner_room where owner_id = #{ownerId} and room_status = 'Binding'  GROUP BY community_id HAVING z.community_id = community_id) and z.del_flag = 0 and z.park_id not in (select park_id from zy_owner_park GROUP BY park_id )")
    List<RoomTree> selectNoBindingAndStatusPark(String ownerId);

    /**
     * 批量删除
     * @param idList id集合
     * @return
     */
    Integer deleteOwnerParkByIds(@Param("idList") List<String> idList);

    /**
     * 修改车位审核的状态
     * @param zyOwnerPark 车位审核对象
     * @return 修改的数量
     */
    int updateOwnerParkStatus(@Param("zyOwnerPark") ZyOwnerPark zyOwnerPark);


    /**
     * 获取车位业主信息
     *
     * @param ownerParkId 公园所有者id
     * @return {@link OwnerParkListDto}
     */
    @Select("select*from zy_owner_park where owner_park_id =#{ownerParkId} and del_flag = 0")
    OwnerParkListDto getOwnerPark(String ownerParkId);

    @Select("select community_id from zy_park where park_id = #{parkId} and del_flag = 0")
    String getCommunityId(String parkId);


    void insertRecord (OwnerParkListDto ownerParkListDto);
    /**
     * 通过ID查询单条数据
     *
     * @param ownerParkId 主键
     * @return 实例对象
     */
    ZyOwnerPark queryById(String ownerParkId);


    /**
     * 新增数据
     *
     * @param zyOwnerPark 实例对象
     * @return 影响行数
     */
    @Override
    int insert(ZyOwnerPark zyOwnerPark);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ZyOwnerPark> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ZyOwnerPark> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ZyOwnerPark> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ZyOwnerPark> entities);

    /**
     * 修改数据
     *
     * @param zyOwnerPark 实例对象
     * @return 影响行数
     */
    int updateOwnerPark(@Param("zyOwnerPark") ZyOwnerPark zyOwnerPark);

    /**
     * 通过主键删除数据
     *
     * @param ownerParkId 主键
     * @return 影响行数
     */
    int deleteById(Long ownerParkId);

}

