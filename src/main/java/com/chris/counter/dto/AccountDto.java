package com.chris.counter.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AccountDto {

    @NonNull
    private Integer id;
    @NonNull
    private Long uid;
    private String name;
    private String lastLoginDate;
    private String lastLoginTime;
    private Long balance;
    private String token;

}
