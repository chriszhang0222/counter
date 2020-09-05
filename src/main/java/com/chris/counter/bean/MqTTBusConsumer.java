package com.chris.counter.bean;

import com.alipay.sofa.common.profile.ArrayUtil;
import com.chris.common.bean.CommonMsg;
import com.chris.common.checksum.ICheckSum;
import com.chris.common.codec.IMsgCodec;
import com.chris.common.codec.MsgCodec;
import com.google.common.collect.Maps;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.chris.common.bean.MsgConstants.MATCH_HQ_DATA;
import static com.chris.common.bean.MsgConstants.MATCH_ORDER_DATA;

@RequiredArgsConstructor
@Log4j2
public class MqTTBusConsumer{

    @NonNull
    private String busIp;

    @NonNull
    private int busPort;

    @NonNull
    private String recvAddr;

    @NonNull
    private ICheckSum iCheckSum;

    @NonNull
    private IMsgCodec iMsgCodec;

    @NonNull
    private Vertx vertx;

    private static final String HQ_ADDR = "-1";

    private static final String INNER_MARKET_DATA_CACHE_ADDR = "l1_market_data";

    public static final String INNER_MATCH_DATA_ADDR = "match_data_addr";

    public void startUp(){
        MqTTConnect(vertx, busIp, busPort);
    }

    private void MqTTConnect(Vertx vertx, String busIp, int busPort) {
        MqttClient mqttClient = MqttClient.create(vertx);
        mqttClient.connect(busPort, busIp, res -> {
            if(res.succeeded()){
                log.info("connected mqtt bus");
                Map<String, Integer> topic = Maps.newHashMap();
                topic.put(recvAddr, MqttQoS.AT_LEAST_ONCE.value());
                topic.put(HQ_ADDR, MqttQoS.AT_LEAST_ONCE.value());

                mqttClient.subscribe(topic, null)
                        .publishHandler(h -> {
                            CommonMsg msg = iMsgCodec.decodeFromBuffer(h.payload());
                            if(msg.getChecksum() != iCheckSum.getCheckSum(msg.getBody())){
                                return;
                            }
                            byte[] body = msg.getBody();
                            if(ArrayUtil.isNotEmpty(body)){
                                short msgType = msg.getMsgType();
                                long msgNo = msg.getMsgNo();
                                if(msgType == MATCH_HQ_DATA){
                                        vertx.eventBus().send(INNER_MARKET_DATA_CACHE_ADDR, Buffer.buffer(body));
                                }else if(msgType == MATCH_ORDER_DATA){
                                        vertx.eventBus().send(INNER_MATCH_DATA_ADDR, Buffer.buffer(body));
                                }else{
                                    log.error("Message Type: {} unsupported", msgType);
                                }

                            }
                        });
            }else{
                log.warn("connect to mqtt bus:{}-{} falied", busIp, busPort);
            }
        });

        mqttClient.closeHandler(h -> {
           try{
               TimeUnit.SECONDS.sleep(5);;
           }catch (Exception e){
               log.error(e);
           }
           MqTTConnect(vertx, busIp, busPort);
        });
    }

}
