package com.chris.counter.service;

import com.alibaba.fastjson.JSON;
import com.chris.counter.domain.PosiInfo;
import com.chris.counter.domain.PosiInfoExample;
import com.chris.counter.mapper.PosiInfoMapper;
import com.chris.counter.util.CacheType;
import com.chris.counter.util.RedisStringCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PosiService {

    @Resource
    private PosiInfoMapper posiInfoMapper;


    public List<PosiInfo> getPosiList(long uid){
        List<PosiInfo> res = null;
        String suid = Long.toString(uid);
        String posi = RedisStringCache.get(suid, CacheType.POSI);
        if(StringUtils.isEmpty(posi)){
            List<PosiInfo> posiInfos = posiInfoMapper.queryPosi(uid);
            res = posiInfos;
            if(!CollectionUtils.isEmpty(posiInfos)){
                RedisStringCache.cache(suid, JSON.toJSONString(posiInfos), CacheType.POSI);
            }
        }else{
           res = JSON.parseObject(posi, List.class);
        }
        return res;
    }
}
