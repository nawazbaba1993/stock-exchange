package com.navi.stockexchange.services;

import com.navi.stockexchange.models.Order;
import com.navi.stockexchange.models.Stock;
import com.navi.stockexchange.models.Transaction;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StockExchangeService {

    private OrderService orderService;

    public StockExchangeService(OrderService orderService) {
        this.orderService = orderService;
    }

    public List<Transaction> processOrders() {
        List<Transaction> transactions = new ArrayList<>();
        Map<Stock, Set<Order>> buyOrders = orderService.getAllBuyOrders();
        Map<Stock, Set<Order>> sellOrders = orderService.getAllSellOrders();

        if(MapUtils.isEmpty(buyOrders) || MapUtils.isEmpty(sellOrders)) {
            return transactions;
        }

        buyOrders.forEach((stock, buyOrderSet) -> {
            if(CollectionUtils.isEmpty(buyOrderSet)) {
                return;
            }

            Set<Order> sellOrderSet = sellOrders.get(stock);
            if(CollectionUtils.isEmpty(sellOrderSet)) {
                return;
            }
            for (Order buyOrder : buyOrderSet) {
                if(buyOrder.getQuantity() > 0) {
                    for (Order sellOrder : sellOrderSet) {
                        if(sellOrder.getQuantity() > 0 && buyOrder.getAskingPrice().compareTo(sellOrder.getAskingPrice()) >= 0) {
                            int executionQuantity;
                            if(sellOrder.getQuantity() > buyOrder.getQuantity()) {
                                executionQuantity = buyOrder.getQuantity();
                                sellOrder.setQuantity(sellOrder.getQuantity() - executionQuantity);
                                buyOrder.setQuantity(0);
                            } else {
                                executionQuantity = sellOrder.getQuantity();
                                buyOrder.setQuantity(buyOrder.getQuantity() - executionQuantity);
                                sellOrder.setQuantity(0);
                            }
                            Transaction transaction = new Transaction(buyOrder.getId(), sellOrder.getAskingPrice(), executionQuantity, sellOrder.getId());
                            transactions.add(transaction);
                        }
                    }
                }
            }
            
        });
        return transactions;
    }
}
