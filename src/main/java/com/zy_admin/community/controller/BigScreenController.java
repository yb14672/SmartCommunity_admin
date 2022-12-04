package com.zy_admin.community.controller;

import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.entity.UserNum;
import com.zy_admin.util.ResultTool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yb14672
 * Time:2022 - 2022/12/4 - 16:48
 **/
@RestController
@RequestMapping("bigScreen")
public class BigScreenController {
    @GetMapping("/countUserNum")
    public Result index() {
        return new Result(new UserNum(), ResultTool.fail(ResultCode.COMMON_FAIL));
    }
}
