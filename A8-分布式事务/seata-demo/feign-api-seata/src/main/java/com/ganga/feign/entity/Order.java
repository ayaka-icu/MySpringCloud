package com.ganga.feign.entity;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private Integer money;
}
