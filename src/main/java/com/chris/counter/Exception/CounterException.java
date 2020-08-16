package com.chris.counter.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounterException extends Exception{

    private String errorMessage;

    public CounterException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
