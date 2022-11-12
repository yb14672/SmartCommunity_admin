package com.zy_admin.sys.controller;

import com.zy_admin.util.MinioUtilS;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultCode;
import com.zy_admin.util.ResultTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class MinioController {
    @Autowired
    private MinioUtilS minioUtilS;
    @Value("${minio.endpoint}")
    private String address;
    @Value("${minio.bucketName}")
    private String bucketName;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        Result result = new Result();
        List<String> upload = minioUtilS.upload(new MultipartFile[]{file});
        String url = address + "/" + bucketName + "/" + upload.get(0);
        result.setData(url == null ? null : url);
        result.setMeta(url == null ? ResultTool.fail(ResultCode.USER_AVATAR_UPLOAD_FAILED) : ResultTool.fail(ResultCode.SUCCESS));
        return result;
    }

}
