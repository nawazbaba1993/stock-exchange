package com.navi.stockexchange.models;

import com.navi.stockexchange.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private LocalTime time;
    private Stock stock;
    private OrderType orderType;
    private BigDecimal askingPrice;
    private int quantity;
}



