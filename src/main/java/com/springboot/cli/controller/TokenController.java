package com.springboot.cli.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
//@RequestMapping("/token")
public class TokenController {

    @GetMapping("/get/Info")
    public String getInfo() {//测试拦截效果
        // 从全局环境中获取用户id
//        JwtUser user = AuthStorage.getUser();
//        return "用户：" + user.getUserId() + "，请求成功";
        return "111";
    }
}
