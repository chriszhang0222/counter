package com.chris.counter.service;

import com.alibaba.fastjson.JSON;
import com.chris.common.order.OrderDirection;
import com.chris.common.order.OrderDto;
import com.chris.common.order.OrderStatus;
import com.chris.counter.Exception.CounterException;
import com.chris.counter.config.CounterConfig;
import com.chris.counter.config.GateWayConn;
import com.chris.counter.domain.OrderInfo;
import com.chris.counter.domain.PosiInfo;
import com.chris.counter.domain.PosiInfoExample;
import com.chris.counter.mapper.AccountMapper;
import com.chris.counter.mapper.OrderInfoMapper;
import com.chris.counter.mapper.PosiInfoMapper;
import com.chris.counter.util.CacheType;
import com.chris.counter.util.IDConverter;
import com.chris.counter.util.RedisStringCache;
import com.chris.counter.util.TimeformatUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private PosiInfoMapper posiInfoMapper;

    @Autowired
    private CounterConfig config;

    @Autowired
    private GateWayConn gateWayConn;

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

    @Transactional
    public boolean saveOrder(OrderDto orderDto) throws CounterException{
        int res = -1;
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUid(orderDto.getUid());
        orderInfo.setCode(orderDto.getCode());
        orderInfo.setDirection((int) orderDto.getDirection().getDirection());
        orderInfo.setType((int) orderDto.orderType.getType());
        orderInfo.setPrice(orderDto.getPrice());
        orderInfo.setOcount(orderDto.getVolume());
        //orderInfo.setTcount(0L);
        orderInfo.setStatus(OrderStatus.NOT_SET.getCode());
        orderInfo.setDate(TimeformatUtil.yyyyMMdd(orderDto.timestamp));
        orderInfo.setTime(TimeformatUtil.hhMMss(orderDto.timestamp));
        try {
            res = orderInfoMapper.insert(orderInfo);
        }catch (Exception e){
            throw new CounterException("Order insert failed!");
        }
        if(res < 0){
            return false;
        }else{
            if(orderDto.direction == OrderDirection.BUY){
                minusBanalce(orderDto.uid, orderDto.price * orderDto.volume);
            }else if(orderDto.direction == OrderDirection.SELL){
                minusPosi(orderDto.uid, orderDto.code, orderDto.volume, orderDto.price);
            }else{
                log.error("wrong direction[{}], order:[{}]", orderDto.direction, orderDto);
                return false;
            }
        }
        orderDto.oid = IDConverter.combineInt2Long(config.getId(), res);
        gateWayConn.sendOrder(orderDto);
        return true;
    }

    public void addBalance(long uid, long balance){
        accountMapper.updateBalance(uid, balance);
    }

    public void minusBanalce(long uid, long balance){
        addBalance(uid, -balance);
    }

    public void addPosi(long uid, int code, long volume, long price){
        PosiInfo posiInfo;
        PosiInfoExample example = new PosiInfoExample();
        example.createCriteria().andUidEqualTo(uid).andCodeEqualTo(code);
        List<PosiInfo> posiInfos = posiInfoMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(posiInfos)){
            posiInfo = new PosiInfo();
            posiInfo.setUid(uid);
            posiInfo.setCode(code);
            posiInfo.setCount(volume);
            posiInfo.setCost(volume * price);
            posiInfoMapper.insert(posiInfo);
        }else{
            posiInfo = posiInfos.get(0);
            posiInfo.setCount(posiInfo.getCount() + volume);
            posiInfo.setCost(posiInfo.getCost()+ price*volume);
            posiInfoMapper.updateByPrimaryKey(posiInfo);
        }
    }

    public void minusPosi(long uid, int code, long volume, long price){
        addPosi(uid, code, -volume, price);
    }
}
