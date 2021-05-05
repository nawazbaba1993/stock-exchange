package com.navi.stockexchange.services;

import com.navi.stockexchange.comparators.BuyOrderComparator;
import com.navi.stockexchange.enums.OrderType;
import com.navi.stockexchange.models.Order;
import com.navi.stockexchange.models.Stock;
import com.navi.stockexchange.models.Transaction;
import com.navi.stockexchange.services.OrderService;
import com.navi.stockexchange.services.StockExchangeService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class StockExchangeServiceTest {

    public static final String TIME_FORMAT = "HH:mm";

    @Mock
    OrderService orderService;

    StockExchangeService exchangeService;



    Map<Stock, Set<Order>> buyOrders;
    Map<Stock, Set<Order>> sellOrders;
    List<Transaction> transactionList;
    @Before
    public void beforeRun() {
        exchangeService = new StockExchangeService(orderService);
        buyOrders = new HashMap<>();
        sellOrders = new HashMap<>();
        transactionList = new ArrayList<>();
        createOrdersMock();
        createTransactionMock();
    }

    /**
     * Creating transaction mock
     *
     * #3 237.45 90 #2
     * #3 236.00 20 #6
     * #4 236.00 10 #6
     * #5 236.00 20 #6
     */
    private void createTransactionMock() {
        transactionList.add(new Transaction("#3", new BigDecimal(237.45), 90, "#2"));
        transactionList.add(new Transaction("#3", new BigDecimal(236.00), 20, "#6"));
        transactionList.add(new Transaction("#4", new BigDecimal(236.00), 10, "#6"));
        transactionList.add(new Transaction("#5", new BigDecimal(236.00), 20, "#6"));
    }

    /**
     *  Creating mock order data
     *
     *      * #1 09:45 BAC sell 240.12 100
     *      * #2 09:46 BAC sell 237.45  90
     *      * #3 09:47 BAC buy  238.10 110
     *      * #4 09:48 BAC buy  237.80  10
     *      * #5 09:49 BAC buy  237.80  40
     *      * #6 09:50 BAC sell 236.00  50
     */
    private void createOrdersMock() {
        Stock stock = new Stock("BAC");
        Set<Order> buyOrderSet = buyOrders.computeIfAbsent(stock, e -> new TreeSet<>(new BuyOrderComparator()));
        Set<Order> sellOrderSet = sellOrders.computeIfAbsent(stock, e -> new TreeSet<>(new BuyOrderComparator()));

        sellOrderSet.add(new Order("#1",
                LocalTime.parse("09:45", DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.getDefault())),
                stock, OrderType.SELL, new BigDecimal(240.12), 100));
        sellOrderSet.add(new Order("#2",
                LocalTime.parse("09:46", DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.getDefault())),
                stock, OrderType.BUY, new BigDecimal(237.45), 90));
        buyOrderSet.add(new Order("#3",
                LocalTime.parse("09:47", DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.getDefault())),
                stock, OrderType.BUY, new BigDecimal(238.10), 110));
        buyOrderSet.add(new Order("#4",
                LocalTime.parse("09:48", DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.getDefault())),
                stock, OrderType.BUY, new BigDecimal(237.80), 10));
        buyOrderSet.add(new Order("#5",
                LocalTime.parse("09:49", DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.getDefault())),
                stock, OrderType.BUY, new BigDecimal(237.80), 40));
        sellOrderSet.add(new Order("#6",
                LocalTime.parse("09:50", DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.getDefault())),
                stock, OrderType.SELL, new BigDecimal(236.00), 50));
    }

    @Test
    public void testNoBuyOrders() {
        List<Transaction> transactions = exchangeService.processOrders();
        Assert.assertTrue(CollectionUtils.isEmpty(transactions));
    }

    @Test
    public void testNoSellOrders() {
        List<Transaction> transactions = exchangeService.processOrders();
        Assert.assertTrue(CollectionUtils.isEmpty(transactions));
    }

    @Test
    public void testProcessOrder() {
        Mockito.when(orderService.getAllBuyOrders()).thenReturn(buyOrders);
        Mockito.when(orderService.getAllSellOrders()).thenReturn(sellOrders);
        List<Transaction> transactions = exchangeService.processOrders();
        Assert.assertTrue(transactionList.equals(transactions));
    }


}
