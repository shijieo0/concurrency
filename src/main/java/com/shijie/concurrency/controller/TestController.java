package com.shijie.concurrency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ShiJie on 2018-04-15 11:54
 */
@Controller
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

}
