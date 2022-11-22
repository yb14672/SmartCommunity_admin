package com.zy_admin.sys.controller;

import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.MinioUtil;
import com.zy_admin.util.Result;
import com.zy_admin.util.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吕蔚霖
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "MinIO文件上传")
public class MinioController {
    @Resource
    private MinioUtil minioUtil;

    @ApiModelProperty("MinIo桶地址")
    @Value("${minio.endpoint}")
    private String address;

    @ApiModelProperty("MinIo桶名称")
    @Value("${minio.bucketName}")
    private String bucketName;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    @ApiImplicitParam(name="file",value = "要上传的文件",required = true)
    public Result upload(MultipartFile file) {
        Result result = new Result(null,ResultTool.fail(ResultCode.COMMON_FAIL));
        List<String> upload = minioUtil.upload(new MultipartFile[]{file});
        String url = address + "/" + bucketName + "/" + upload.get(0);
        result.setData(url == null ? null : url);
        result.setMeta(url == null ? ResultTool.fail(ResultCode.USER_AVATAR_UPLOAD_FAILED) : ResultTool.fail(ResultCode.SUCCESS));
        return result;
    }
}
