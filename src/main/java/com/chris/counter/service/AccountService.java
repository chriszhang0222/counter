package com.chris.counter.service;

import com.alibaba.fastjson.JSON;
import com.chris.counter.Exception.CounterException;
import com.chris.counter.config.CounterConfig;
import com.chris.counter.domain.Account;
import com.chris.counter.domain.AccountExample;
import com.chris.counter.dto.AccountDto;
import com.chris.counter.mapper.AccountMapper;
import com.chris.counter.util.CacheType;
import com.chris.counter.util.RedisStringCache;
import com.chris.counter.util.TimeformatUtil;
import com.chris.counter.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Autowired
    private CounterConfig counterConfig;


   public AccountDto login(String name, String password, String captcha, String captchaId)
    throws CounterException {
            if(StringUtils.isAnyBlank(name, password, captcha, captchaId)){
                throw new CounterException("Name, Password, Captcha can not be empty");
            }
            String captchaInCache = RedisStringCache.get(captchaId, CacheType.CAPTCHA);
            if(StringUtils.isEmpty(captchaInCache)){
                throw new CounterException("Captcha has expired!");
            }else if(!captchaInCache.toLowerCase().equals(captcha.toLowerCase())){
                throw new CounterException("Wrong captcha");
            }
            RedisStringCache.remove(captchaId, CacheType.CAPTCHA);
            AccountDto accountDto = accountMapper.queryAccount(name, password);
            if(accountDto == null){
                throw new CounterException("Wrong Name or Password");
            }
            UuidUtil uuidUtil = new UuidUtil(counterConfig.getDataCenterId(), counterConfig.getWorkerId());
            accountDto.setToken(
                    String.valueOf(uuidUtil.getUuid())
            );
            Date date = new Date();
            accountMapper.updateLoginTime(accountDto.getId(),
                    TimeformatUtil.yyyyMMdd(date),
                    TimeformatUtil.hhMMss(date));
            RedisStringCache.cache(accountDto.getToken(), JSON.toJSONString(accountDto),
                    CacheType.ACCOUNT);
            return accountDto;
    }

    public boolean accountInCache(String token){
        if(StringUtils.isEmpty(token)){
            return false;
        }
        String acc = RedisStringCache.get(token, CacheType.ACCOUNT);
        if(!StringUtils.isEmpty(acc)){
            RedisStringCache.cache(token, acc, CacheType.ACCOUNT);
            return true;
        }
        return false;
    }

    public boolean logout(String token){
       RedisStringCache.remove(token, CacheType.ACCOUNT);
       return true;
    }

    public void savePassword(Long uid, String oldPassword, String newPassword) throws CounterException{
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andUidEqualTo(uid).andPasswordEqualTo(oldPassword);
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        if(accountList.size() == 0){
            throw new CounterException("Old password does not correct!");
        }
        Account account = accountList.get(0);
        account.setPassword(newPassword);
        accountMapper.updateByPrimaryKey(account);
    }

}
