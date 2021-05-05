package com.navi.stockexchange;

import com.navi.stockexchange.repos.OrderRepository;
import com.navi.stockexchange.services.OrderService;

import java.util.HashMap;

public class Geektrust {
    public static void main(String[] args) {
        if(args.length == 0 || args[0].trim().length() == 0) {
            System.out.println("Please provide the path of file 'java StockExchangeApp.java <file_path_to_read_stock_data>'.");
            return;
        }

        OrderRepository orderRepository = new OrderRepository(new HashMap<>(), new HashMap<>());
        OrderService orderService = new OrderService(orderRepository);

        StockExchangeApp app = new StockExchangeApp(orderService);

        app.processOrdersFromFile(args[0]);
    }
}
