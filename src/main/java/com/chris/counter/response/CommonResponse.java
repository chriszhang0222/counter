package com.chris.counter.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

    private int code;
    private String message;
    private T data;
    private boolean success = true;

    public CommonResponse(T data){
        this(0, "", data, true);
    }

    public CommonResponse(int code, String message){
        this.success = true;
        this.code = code;
        this.message = message;
    }


}
