package com.navi.stockexchange.repos;

import com.navi.stockexchange.enums.OrderType;
import com.navi.stockexchange.exceptions.OrderException;
import com.navi.stockexchange.models.Order;
import com.navi.stockexchange.models.Stock;
import com.navi.stockexchange.repos.OrderRepository;
import org.apache.commons.collections4.MapUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class OrderRepositoryTest {
    public static final String TIME_FORMAT = "HH:mm";

    Order mockOrder;

    Map<Stock, Set<Order>> buyOrders;
    Map<Stock, Set<Order>> sellOrders;

    OrderRepository orderRepository;

    @Before
    public void beforeRun() {
        Stock stock = new Stock("BAC");
        mockOrder = new Order("#1",
                LocalTime.parse("09:45", DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.getDefault())),
                stock, OrderType.SELL, new Float(240.12), 100);
        buyOrders = new HashMap<>();
        sellOrders = new HashMap<>();
        orderRepository = new OrderRepository(buyOrders, sellOrders);
    }

    @Test
    public void testAddingNullOrder() {
        orderRepository.addOrder(null);
        Assert.assertTrue(MapUtils.isEmpty(orderRepository.getBuyOrders()));
        Assert.assertTrue(MapUtils.isEmpty(orderRepository.getSellOrders()));
    }

    @Test(expected = OrderException.class)
    public void testAddingOrderWithOutStock() {
        Order order = new Order();
        orderRepository.addOrder(order);
    }

    @Test
    public void testAddingBuyOrder() {
        mockOrder.setOrderType(OrderType.BUY);
        orderRepository.addOrder(mockOrder);
        Assert.assertTrue(MapUtils.isNotEmpty(orderRepository.getBuyOrders()));
    }

    @Test
    public void testAddingSellOrder() {
        mockOrder.setOrderType(OrderType.SELL);
        orderRepository.addOrder(mockOrder);
        Assert.assertTrue(MapUtils.isNotEmpty(orderRepository.getSellOrders()));
    }

    @Test(expected = OrderException.class)
    public void testAddingDuplicateBuyOrder() {
        mockOrder.setOrderType(OrderType.BUY);
        orderRepository.addOrder(mockOrder);
        orderRepository.addOrder(mockOrder);
    }

    @Test(expected = OrderException.class)
    public void testAddingDuplicateSellOrder() {
        mockOrder.setOrderType(OrderType.SELL);
        orderRepository.addOrder(mockOrder);
        orderRepository.addOrder(mockOrder);
    }
}
