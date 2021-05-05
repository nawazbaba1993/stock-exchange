package com.navi.stockexchange.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Transaction {
    private String buyerOrderId;
    private Float sellPrice;
    private int quantity;
    private String sellerOrderId;
}
