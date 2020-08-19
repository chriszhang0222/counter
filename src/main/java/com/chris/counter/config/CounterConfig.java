package com.chris.counter.config;

import com.chris.common.checksum.ByteCheckSum;
import com.chris.common.checksum.ICheckSum;
import com.chris.common.codec.BodyCodec;
import com.chris.common.codec.IBodyCodec;
import com.chris.common.codec.IMsgCodec;
import com.chris.common.codec.MsgCodec;
import io.vertx.core.Vertx;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class CounterConfig {

    @Value("${counter.id}")
    private short id;

    @Value("${counter.dataCenterId}")
    private long dataCenterId;

    @Value("${counter.workerId}")
    private long workerId;

    @Value("${counter.sendip}")
    private String sendIp;

    @Value("${counter.sendport}")
    private int sendPort;

    @Value("${counter.gatewayid}")
    private short gatewayId;

    private Vertx vertx = Vertx.vertx();

    private ICheckSum cs;

    private IBodyCodec bodyCodec;

    private IMsgCodec msgCodec;

    @PostConstruct
    public void init(){
        cs = new ByteCheckSum();
        bodyCodec = new BodyCodec();
        msgCodec = new MsgCodec();

    }
}
