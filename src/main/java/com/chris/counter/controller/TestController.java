package com.chris.counter.controller;

import com.chris.counter.util.DbUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test")
    public Long getId(){
        return DbUtil.getId();
    }
}
