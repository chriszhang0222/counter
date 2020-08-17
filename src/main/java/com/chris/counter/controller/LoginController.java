package com.chris.counter.controller;

import com.chris.counter.Exception.CounterException;
import com.chris.counter.config.CounterConfig;
import com.chris.counter.dto.AccountDto;
import com.chris.counter.response.CaptchaRes;
import com.chris.counter.response.CommonResponse;
import com.chris.counter.service.AccountService;
import com.chris.counter.util.CacheType;
import com.chris.counter.util.Captcha;
import com.chris.counter.util.RedisStringCache;
import com.chris.counter.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private CounterConfig counterConfig;

    @Resource
    private AccountService accountService;

    @RequestMapping(value = "/captcha")
    public CaptchaRes getCaptcha() throws Exception{
        Captcha captcha = new Captcha(120, 40, 4, 10);
        UuidUtil uuidUtil = new UuidUtil(counterConfig.getDataCenterId(), counterConfig.getWorkerId());
        String uuid = String.valueOf(uuidUtil.getUuid());
        RedisStringCache.cache(uuid, captcha.getCode().toLowerCase(), CacheType.CAPTCHA);
        CaptchaRes res = new CaptchaRes(uuid, captcha.getBase64ByteStr());
        return res;
    }

    @RequestMapping("/userlogin")
    public AccountDto login(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String captcha,
            @RequestParam String captchaId

    ) throws CounterException {
        AccountDto accountDto = accountService.login(name, password, captcha, captchaId);
        return accountDto;
    }

    @RequestMapping("loginfail")
    public CommonResponse loginfail(){
        return new CommonResponse(1, "You need to login!", null, false);
    }
}
