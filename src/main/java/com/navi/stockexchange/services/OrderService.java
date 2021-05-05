package com.navi.stockexchange.services;

import com.google.common.base.Splitter;
import com.navi.stockexchange.enums.OrderType;
import com.navi.stockexchange.models.Order;
import com.navi.stockexchange.models.Stock;
import com.navi.stockexchange.repos.OrderRepository;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class OrderService {

    public static final String TIME_FORMAT = "HH:mm";

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(String orderInput) {
        if(StringUtils.isBlank(orderInput)) {
            return;
        }
        Splitter spaceSplitter = Splitter.on(' ').omitEmptyStrings().trimResults();
        Iterator<String> iterator = spaceSplitter.split(orderInput).iterator();

        Order order = new Order();
        order.setId(iterator.next());
        order.setTime(LocalTime.parse(iterator.next(), DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.getDefault())));
        order.setStock(new Stock(iterator.next()));
        order.setOrderType(OrderType.valueOf(iterator.next().toUpperCase()));
        order.setAskingPrice(Float.parseFloat(iterator.next()));
        order.setQuantity(Integer.parseInt(iterator.next()));

        orderRepository.addOrder(order);
    }

    public Map<Stock, Set<Order>> getAllBuyOrders() {
        return orderRepository.getBuyOrders();
    }

    public Map<Stock, Set<Order>> getAllSellOrders() {
        return orderRepository.getSellOrders();
    }

}
