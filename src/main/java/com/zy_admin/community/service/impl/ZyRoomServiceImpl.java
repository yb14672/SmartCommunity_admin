//package com.zy_admin.community.service.impl;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.zy_admin.common.Pageable;
//import com.zy_admin.common.enums.ResultCode;
//import com.zy_admin.community.dao.ZyRoomDao;
//import com.zy_admin.community.dto.ZyRoomDto;
//import com.zy_admin.community.entity.ZyRoom;
//import com.zy_admin.community.service.ZyRoomService;
//import com.zy_admin.util.Result;
//import com.zy_admin.util.ResultTool;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * 房间 (ZyRoom)表服务实现类
// *
// * @author makejava
// * @since 2022-11-01 19:49:03
// */
//@Service("zyRoomService")
//public class ZyRoomServiceImpl extends ServiceImpl<ZyRoomDao, ZyRoom> implements ZyRoomService {
//
//    /**
//     * 查询所有和分页
//     * @param zyRoom
//     * @param pageable
//     * @return
//     */
//    @Override
//    public Result selectAllRoomLimit(ZyRoom zyRoom, Pageable pageable) {
//        //默认给失败
//        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
//        //总数
//        Long total = this.baseMapper.count(zyRoom);
//        //设置默认页面为0
//        long pages = 0;
//        if (total>0){
//            //计算总页数
//            pages = total%pageable.getPageSize()==0?total/pageable.getPageSize():total/pageable.getPageSize()+1;
//            pageable.setPages(pages);
//            //页码修正
//            pageable.setPageNum(pageable.getPageNum()<1?1:pageable.getPageNum());
//            pageable.setPageNum(pageable.getPageNum()>pages?pages:pageable.getPageNum());
//            //设置起始下标
//            pageable.setIndex((pageable.getPageNum()-1)*pageable.getPageSize());
//        }else {
//            pageable.setPageNum(0);
//        }
//        pageable.setTotal(total);
//        List<ZyRoomDto> zyRoomDtoList = this.baseMapper.selectAllRoomLimit(zyRoom, pageable);
//        //存到data数据里面
//        result.setData(zyRoomDtoList);
//        //返回信号
//        result.setMeta(ResultTool.success(ResultCode.SUCCESS));
//        System.out.println(result);
//        return result;
//    }
//}
//
