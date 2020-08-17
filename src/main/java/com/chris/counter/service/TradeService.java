package com.chris.counter.service;

import com.alibaba.fastjson.JSON;
import com.chris.counter.domain.TradeInfo;
import com.chris.counter.domain.TradeInfoExample;
import com.chris.counter.mapper.TradeInfoMapper;
import com.chris.counter.util.CacheType;
import com.chris.counter.util.RedisStringCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TradeService {

    @Resource
    private TradeInfoMapper tradeInfoMapper;

    public List<TradeInfo> getTradeList(long uid){
        List<TradeInfo> res = null;
        String suid = String.valueOf(uid);
        String trade = RedisStringCache.get(suid, CacheType.TRADE);
        if(StringUtils.isEmpty(trade)){
            List<TradeInfo> tradeInfos = tradeInfoMapper.queryTrade(uid);
            res = tradeInfos;
            if(!CollectionUtils.isEmpty(tradeInfos)){
                RedisStringCache.cache(suid, JSON.toJSONString(tradeInfos), CacheType.TRADE);
            }
        }else{
            res = JSON.parseObject(trade, List.class);
        }
        return res;
    }
}
