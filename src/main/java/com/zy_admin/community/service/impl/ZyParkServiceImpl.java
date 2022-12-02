package com.zy_admin.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.community.dao.ZyParkDao;
import com.zy_admin.community.entity.ZyOwnerPark;
import com.zy_admin.community.entity.ZyPark;
import com.zy_admin.community.service.ZyParkService;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.SnowflakeManager;
import com.zy_admin.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (ZyPark)表服务实现类
 *
 * @author makejava
 * @since 2022-12-01 15:13:40
 */
@Service("zyParkService")
public class ZyParkServiceImpl extends ServiceImpl<ZyParkDao, ZyPark> implements ZyParkService {
    @Resource
    private SnowflakeManager snowflakeManager;


    @Override
    public Result batchInsert(ZyPark zyPark, int number) throws Exception {
        Result result = new Result();
        List<ZyPark> zyParks = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            ZyPark zyPark1 = new ZyPark();
            if (StringUtil.isNotEmpty(zyPark.getRemark())) {
                zyPark1.setRemark(zyPark.getRemark());
            }
            zyPark1.setParkId(snowflakeManager.nextId() + "");
            long now = System.currentTimeMillis();
            zyPark1.setParkCode("PK_" + Long.toString(now).substring(0, 13));
            zyParks.add(zyPark1);
        }
        try {
            int i = this.baseMapper.insertBatch(zyParks);
            if (i > 0) {
                result.setData("批量添加成功，影响的行数：" + i);
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.INSERT_PARK_FAIL));
        }
        return result;
    }

    @Override
    public Result deletePark(List<String> parkIds) {
        Result result = new Result();
        List<ZyOwnerPark> zyOwnerParks = this.baseMapper.selectOwnerParkByParkId(parkIds);
        if (zyOwnerParks.size() > 0) {
            result.setMeta(ResultTool.fail(ResultCode.DELETED_PARK_FAIL));
        } else {
            try {
                this.baseMapper.deletedPark(parkIds);
                result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
            } catch (Exception e) {
                e.printStackTrace();
                result.setMeta(ResultTool.fail(ResultCode.COMMON_FAIL));
            }
        }
        return result;
    }

    /**
     * 修改停车位
     *
     * @param zyPark 停车位
     * @return {@link Result}
     */
    @Override
    public Result updatePark(ZyPark zyPark) {
        Result result = new Result();
        try {
            this.baseMapper.updatePark(zyPark);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.UPDATE_PARK_FAIL));
        }
        return result;
    }

    /**
     * 新增停车位
     *
     * @param zyPark 停车位
     * @return {@link Result}
     */
    @Override
    public Result insertPark(ZyPark zyPark) {
        Result result = new Result();
        try {
            this.baseMapper.insertPark(zyPark);
            result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMeta(ResultTool.fail(ResultCode.INSERT_PARK_FAIL));
        }
        return result;
    }
}
