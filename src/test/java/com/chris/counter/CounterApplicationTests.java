package com.chris.counter;

import com.chris.counter.domain.AccountExample;
import com.chris.counter.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CounterApplicationTests {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    void contextLoads() {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andIdEqualTo(1);
        System.out.println(accountMapper.countByExample(accountExample));
    }

}
