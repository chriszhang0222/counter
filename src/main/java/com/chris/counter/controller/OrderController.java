package com.chris.counter.controller;

import com.chris.counter.Exception.CounterException;
import com.chris.counter.domain.AccountExample;
import com.chris.counter.domain.OrderInfo;
import com.chris.counter.domain.PosiInfo;
import com.chris.counter.domain.TradeInfo;
import com.chris.counter.service.AccountService;
import com.chris.counter.service.OrderService;
import com.chris.counter.service.PosiService;
import com.chris.counter.service.TradeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Resource
    private AccountService accountService;

    @Resource
    private OrderService orderService;

    @Resource
    private PosiService posiService;

    @Resource
    private TradeService tradeService;

    @PostMapping("/balance")
    public long balanceQuery(@RequestParam long uid) throws CounterException{
        return accountService.getBalance(uid);
    }

    @PostMapping("/posiInfo")
    public List<PosiInfo> posiQuery(@RequestParam Long uid)
    throws CounterException{
        return posiService.getPosiList(uid);
    }

    @PostMapping("/orderInfo")
    public List<OrderInfo> orderQuery(@RequestParam Long uid)
    throws CounterException{
        return orderService.getOrderList(uid);
    }

    @PostMapping("/tradeInfo")
    public List<TradeInfo> tradeQuery(@RequestParam Long uid)
    throws CounterException{
        return tradeService.getTradeList(uid);
    }



}
