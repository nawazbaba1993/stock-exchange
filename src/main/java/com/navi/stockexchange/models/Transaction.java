package com.navi.stockexchange.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Transaction {
    private String buyerOrderId;
    private BigDecimal sellPrice;
    private int quantity;
    private String sellerOrderId;
}
