package com.chris.counter.advice;

import com.chris.counter.Exception.CounterException;
import com.chris.counter.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(value = CounterException.class)
    @ResponseBody
    public CommonResponse counterExceptionHandler(CounterException e){
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setMessage(e.getErrorMessage());
        log.error("Counter exception: {}", e.getErrorMessage());
        return response;
    }
}
