package com.chris.counter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PosiDto {
    private Integer id;

    private Long uid;

    private Integer code;

    private Long cost;

    private Long count;

    private String name;
}
