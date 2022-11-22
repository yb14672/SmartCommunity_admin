package com.zy_admin.common.core.controller;

import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.common.core.text.StrFormatter;
import com.zy_admin.common.enums.BusinessType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yb14672
 * Time:2022/11/22 - 10:10
 **/
@Controller
@RequestMapping
public class SwaggerController {
    @RequestMapping("/tool/swagger")
    @MyLog(title = "swagger", businessType = BusinessType.GENERATING)
    public String index() {
        return StrFormatter.format("redirect:{}", "/swagger-ui.html");
    }
}
