package com.navi.stockexchange.repos;

import com.navi.stockexchange.comparators.BuyOrderComparator;
import com.navi.stockexchange.exceptions.OrderException;
import com.navi.stockexchange.models.Order;
import com.navi.stockexchange.models.Stock;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static com.navi.stockexchange.enums.OrderType.BUY;
import static com.navi.stockexchange.enums.OrderType.SELL;

@Getter
public class OrderRepository {
    private final Map<Stock, Set<Order>> buyOrders;
    private final Map<Stock, Set<Order>> sellOrders;

    public OrderRepository(Map<Stock, Set<Order>> buyOrders, Map<Stock, Set<Order>> sellOrders) {
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    public void addOrder(Order order) {
        if(Objects.isNull(order)) {
            return;
        }
        if(Objects.isNull(order.getStock())) {
            throw new OrderException("No stock order found for id:"+order.getId());
        }
        Set<Order> orderSet = null;
        if(BUY.equals(order.getOrderType())) {
            orderSet = buyOrders.computeIfAbsent(order.getStock(), e->new TreeSet<>(new BuyOrderComparator()));
            if(orderSet.contains(order)) {
                throw new OrderException("Duplicate order found for id:"+order.getId());
            }
            orderSet.add(order);
        } else if(SELL.equals(order.getOrderType())) {
            orderSet = sellOrders.computeIfAbsent(order.getStock(), e->new TreeSet<>(new BuyOrderComparator()));
            if(orderSet.contains(order)) {
                throw new OrderException("Duplicate order found for id:"+order.getId());
            }
            orderSet.add(order);
        }

    }
}
