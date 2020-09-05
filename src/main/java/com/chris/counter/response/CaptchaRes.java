package com.chris.counter.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaRes {

    private String id;
    private String imageBase64;
}
