package com.chris.counter.Cache;

import com.chris.counter.domain.Stock;
import com.chris.counter.mapper.StockMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class StockCache {

    private Multimap<String, Stock> invertIndex = HashMultimap.create();
    @Resource
    private StockMapper stockMapper;

    @PostConstruct
    private void createinvertIndex(){
        log.info("load stock from db");
        //1. load all stock
        //2. build inverted index
        List<Map<String, Object>> stocks = stockMapper.queryStock();
        if(CollectionUtils.isEmpty(stocks)){
            log.error("no stock find in db");
            return;
        }
        for(Map<String, Object> r: stocks){
            int code = Integer.parseInt(r.get("code").toString());
            String name = r.get("name").toString();
            String abbr = r.get("abbrname").toString();
            Stock stock = new Stock();
            stock.setAbbrname(abbr);
            stock.setCode(code);
            stock.setName(name);
            List<String> codeMetas = splitData(String.format("%6d", code));
            List<String> abbrNameMetas = splitData(abbr);
            codeMetas.addAll(abbrNameMetas);
            for(String key: codeMetas){
                Collection<Stock> stockInfos = invertIndex.get(key);
                if(!CollectionUtils.isEmpty(stockInfos) && stockInfos.size() > 10){
                    continue;
                }
                invertIndex.put(key, stock);
            }
        }
    }

    public Collection<Stock> getStocks(String key){
        return invertIndex.get(key);
    }
    private List<String> splitData(String code){
        List<String> list = Lists.newArrayList();
        int outLength = code.length();
        for(int i=0;i < outLength;i++){
            int inLength = outLength + 1;
            for(int j=i+1;j<inLength;j++){
                list.add(code.substring(i, j));
            }
        }
        return list;
    }
}
