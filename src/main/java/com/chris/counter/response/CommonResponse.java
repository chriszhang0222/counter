package com.chris.counter.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private int code;
    private String message;
    private T data;
    private boolean success = true;

    public CommonResponse(T data){
        this(0, "", data, true);
    }


}
