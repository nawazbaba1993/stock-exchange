package com.navi.stockexchange.comparators;

import com.navi.stockexchange.models.Order;

import java.util.Comparator;

public class SellOrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if(o1.getId().equalsIgnoreCase(o2.getId())) {
            return 0;
        }
        int priceCompare = o1.getAskingPrice().compareTo(o2.getAskingPrice());

        return priceCompare == 0 ? o1.getTime().compareTo(o2.getTime()) : priceCompare;
    }
}
