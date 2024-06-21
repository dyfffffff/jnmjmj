package com.springboot.order.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderCoffeeResponseDto {
    private long coffeeId;
    private String korName;
    private String engName;
    private int price;
    private int quantity;
}
