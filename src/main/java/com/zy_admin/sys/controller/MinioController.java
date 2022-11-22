package com.zy_admin.sys.controller;

import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.MinioUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部署MinIO服务
 * @author lvwei
 */
@RestController
@Slf4j
@CrossOrigin
public class MinioController {
    @Resource
    private MinioUtil minioUtil;
    @Value("${minio.endpoint}")
    private String address;
    @Value("${minio.bucketName}")
    private String bucketName;
    /**
     * 下载文件
     * @param file 存放需要下载的文件
     * @return 查询下载的文件结果集
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        List<String> upload = minioUtil.upload(new MultipartFile[]{file});
        String url = address + "/" + bucketName + "/" + upload.get(0);
        result.setData(url);
        result.setMeta(ResultTool.fail(ResultCode.SUCCESS));
        return result;
    }
}
