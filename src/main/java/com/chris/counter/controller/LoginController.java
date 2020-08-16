package com.chris.counter.controller;

import com.chris.counter.config.CounterConfig;
import com.chris.counter.response.CaptchaRes;
import com.chris.counter.response.CommonResponse;
import com.chris.counter.util.CacheType;
import com.chris.counter.util.Captcha;
import com.chris.counter.util.RedisStringCache;
import com.chris.counter.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private CounterConfig counterConfig;

    @RequestMapping(value = "/captcha")
    public CommonResponse getCaptcha() throws Exception{
        Captcha captcha = new Captcha(120, 40, 4, 10);
        UuidUtil uuidUtil = new UuidUtil(counterConfig.getDataCenterId(), counterConfig.getWorkerId());
        String uuid = String.valueOf(uuidUtil.getUuid());
        RedisStringCache.cache(uuid, captcha.getCode().toLowerCase(), CacheType.CAPTCHA);
        CaptchaRes res = new CaptchaRes(uuid, captcha.getBase64ByteStr());
        return new CommonResponse(res);
    }

    @RequestMapping("/userlogin")
    public CommonResponse login(){
        return new CommonResponse();
    }
}
