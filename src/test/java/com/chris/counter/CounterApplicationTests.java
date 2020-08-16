package com.chris.counter;

import com.alibaba.fastjson.JSON;
import com.chris.counter.domain.AccountExample;
import com.chris.counter.dto.AccountDto;
import com.chris.counter.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CounterApplicationTests {

    @Resource
    private AccountMapper accountMapper;

    @Test
    void contextLoads() {
        AccountDto accountDto = accountMapper.queryAccount("admin", "123456");
        System.out.println(JSON.toJSONString(accountDto));
    }

}
