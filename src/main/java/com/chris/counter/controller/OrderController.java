package com.chris.counter.controller;

import com.chris.common.order.CmdType;
import com.chris.common.order.OrderDirection;
import com.chris.common.order.OrderDto;
import com.chris.common.order.OrderType;
import com.chris.counter.Cache.StockCache;
import com.chris.counter.Exception.CounterException;
import com.chris.counter.domain.*;
import com.chris.counter.response.CommonResponse;
import com.chris.counter.service.AccountService;
import com.chris.counter.service.OrderService;
import com.chris.counter.service.PosiService;
import com.chris.counter.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Value("${counter.id}")
    private short memberId;

    @Resource
    private AccountService accountService;

    @Resource
    private OrderService orderService;

    @Resource
    private PosiService posiService;

    @Resource
    private TradeService tradeService;

    @Autowired
    private StockCache stockCache;

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

    @PostMapping("/code")
    public Collection<Stock> stockQuery(@RequestParam String key){
        return stockCache.getStocks(key);
    }

    @PostMapping("/sendorder")
    public CommonResponse saveOrder(@RequestParam int uid,
                                    @RequestParam short type,
                                    @RequestParam long timestamp,
                                    @RequestParam int code,
                                    @RequestParam byte direction,
                                    @RequestParam long price,
                                    @RequestParam long volume,
                                    @RequestParam byte ordertype)
    throws CounterException{
        OrderDto orderDto = OrderDto.builder()
                .type(CmdType.of(type))
                .timestamp(timestamp)
                .mid(memberId)
                .uid(uid)
                .code(code)
                .direction(OrderDirection.of(ordertype))
                .price(price)
                .volume(volume)
                .orderType(OrderType.of(ordertype))
                .direction(OrderDirection.of(direction))
                .build();
        orderService.saveOrder(orderDto);
        return new CommonResponse(0, "OK");
    }
}
