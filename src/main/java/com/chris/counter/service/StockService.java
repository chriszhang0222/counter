package com.chris.counter.service;

import com.chris.counter.mapper.StockMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class StockService {

    @Resource
    private StockMapper stockMapper;

}
