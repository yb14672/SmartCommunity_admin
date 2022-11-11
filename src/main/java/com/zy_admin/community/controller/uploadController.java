package com.zy_admin.community.controller;//package com.zy_admin.community.controller;
//
//import com.zy_admin.community.entity.ZyFiles;
//import com.zy_admin.util.MinioUtilS;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@Slf4j
//public class uploadController {
//    @Autowired
//    private MinioUtilS minioUtilS;
//    @Value("${minio.endpoint}")
//    private String address;
//    @Value("${minio.bucketName}")
//    private String bucketName;
//
////    @Autowired
////    RedisUtils redisUtils;
////    @Autowired
////    private ZyCommunityInteractionDao dao;
//
//    @PostMapping("/uploadComment")
//    public Map<Object, Object> upload(MultipartFile[] file, String interactionId) {
//        System.out.println(interactionId);
//        System.out.println(file.length);
//        ZyFiles zyFiles;
//        Map<Object, Object> map = new HashMap<>();
////        ZyOwner owner = (ZyOwner) redisUtils.get("owner");
//        List<String> upload = minioUtilS.upload(file);
//        List<String> list = new ArrayList<>();
//        List<String> path = new ArrayList<>();
//        List<ZyFiles> files = new ArrayList<>();
//        for (String s : upload) {
//            list.add(address + "/" + bucketName + "/" + s);
//        }
//        for (int i = 0; i < list.size(); i++) {
//            zyFiles = new ZyFiles();
//            zyFiles.setFilesUrl(list.get(i));
//            for (int j = 0; j < list.size(); j++) {
////                zyFiles.setFilesId(IdUtils.fastSimpleUUID());
//                zyFiles.setRemark("CommunityInteraction");
////                zyFiles.setUserId(owner.getOwnerId());
////                zyFiles.setParentId(interactionId);
//            }
//            files.add(zyFiles);
//        }
//        for (String s : list) {
//            path.add("<img src=" + s + ">");
//        }
////        dao.addImg(files);
//        map.put("path", path);
//        map.put("list", files);
//        return map;
//    }
//
