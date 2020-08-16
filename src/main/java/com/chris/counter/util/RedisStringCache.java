package com.chris.counter.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
@Data
public class RedisStringCache {

    private static RedisStringCache redisStringCache;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${cacheexpire.captcha}")
    private int captchaExpireTime;

    @Value("${cacheexpire.account}")
    private int accountExpireTime;

    @Value("${cacheexpire.order}")
    private int orderExpireTime;

    private RedisStringCache(){

    }

    @PostConstruct
    private void init(){
        redisStringCache = new RedisStringCache();
        redisStringCache.setRedisTemplate(redisTemplate);
        redisStringCache.setAccountExpireTime(accountExpireTime);
        redisStringCache.setCaptchaExpireTime(captchaExpireTime);
        redisStringCache.setOrderExpireTime(orderExpireTime);
    }

    public static void remove(String key, CacheType cacheType){

        redisStringCache.getRedisTemplate().delete(cacheType + key);
    }

    public static void cache(String key, String value, CacheType cacheType){
        int expTime;
        switch(cacheType){
            case CAPTCHA:
                expTime = redisStringCache.getCaptchaExpireTime();
                break;
            case ACCOUNT:
                expTime = redisStringCache.getAccountExpireTime();
                break;
            case POSI:
            case TRADE:
            case ORDER:
                expTime = redisStringCache.getOrderExpireTime();
                break;
            default:
                expTime = 10;
        }
        redisStringCache.getRedisTemplate()
                .opsForValue().set(cacheType.type() + key, value, expTime , TimeUnit.SECONDS);
    }

    public static String get(String key, CacheType cacheType){
        return redisStringCache.getRedisTemplate()
                .opsForValue().get(cacheType.type() + key);
    }

}
