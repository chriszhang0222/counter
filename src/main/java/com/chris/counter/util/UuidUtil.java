package com.chris.counter.util;

public class UuidUtil {

    private SnowflakeIdWorker idWorker;

    public UuidUtil(long centerId, long workerId){
        idWorker = new SnowflakeIdWorker(workerId, centerId);
    }

    public long getUuid(){
        return idWorker.nextId();
    }
}
