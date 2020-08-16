package com.chris.counter.service;

import com.chris.counter.dto.AccountDto;
import com.chris.counter.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountService {

    @Resource
    private AccountMapper accountMapper;

}
