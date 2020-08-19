package com.chris.counter.config;

import com.chris.common.bean.CommonMsg;
import com.chris.common.bean.MsgConstants;
import com.chris.common.order.OrderDto;
import com.chris.common.tcp.TcpDirectSender;
import com.chris.counter.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class GateWayConn {

    @Autowired
    private CounterConfig config;

    private TcpDirectSender directSender;

    @PostConstruct
    private void init(){
        log.info("Init Gateway connection");
        directSender = new TcpDirectSender(config.getSendIp(), config.getSendPort(), config.getVertx());
        directSender.startup();
    }

    public void sendOrder(OrderDto orderDto){
        byte[] data = null;
        try{
            data = config.getBodyCodec().serialize(orderDto);
        }catch (Exception e){
            log.error("encode error for orderdto:{}", orderDto, e);
        }
        CommonMsg msg = new CommonMsg();
        msg.setBodyLength(data.length);
        msg.setChecksum(config.getCs().getCheckSum(data));
        msg.setMsgSrc(config.getId());
        msg.setMsgDst(config.getGatewayId());
        msg.setMsgType(MsgConstants.COUNTER_NEW_ORDER);
        msg.setStatus(MsgConstants.NORMAL);
        msg.setMsgNo(new UuidUtil(config.getDataCenterId(), config.getWorkerId()).getUuid());
        msg.setBody(data);
        directSender.send(msg);
    }

}
