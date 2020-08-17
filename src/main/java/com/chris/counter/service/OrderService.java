package com.chris.counter.service;

import com.alibaba.fastjson.JSON;
import com.chris.counter.domain.OrderInfo;
import com.chris.counter.domain.OrderInfoExample;
import com.chris.counter.mapper.OrderInfoMapper;
import com.chris.counter.util.CacheType;
import com.chris.counter.util.RedisStringCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    public List<OrderInfo> getOrderList(long uid){
        List<OrderInfo> res = null;
        String suid = String.valueOf(uid);
        String order = RedisStringCache.get(suid, CacheType.ORDER);
        if(StringUtils.isEmpty(order)){
            List<OrderInfo> orderInfos = orderInfoMapper.queryOrder(uid);
            res = orderInfos;
            if(!CollectionUtils.isEmpty(orderInfos)){
                RedisStringCache.cache(suid, JSON.toJSONString(orderInfos), CacheType.ORDER);
            }
        }else{
            res = JSON.parseObject(order, List.class);
        }
        return res;
    }
}
