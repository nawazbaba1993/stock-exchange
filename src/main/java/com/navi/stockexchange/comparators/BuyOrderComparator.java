package com.navi.stockexchange.comparators;

import com.navi.stockexchange.models.Order;

import java.util.Comparator;

public class BuyOrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if(o1.getId().equalsIgnoreCase(o2.getId())) {
            return 0;
        }
        int timeCompare = o1.getTime().compareTo(o2.getTime());

        return timeCompare == 0 ? o1.getId().compareTo(o2.getId()) : timeCompare;
    }
}
