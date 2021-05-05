package com.navi.stockexchange;

import com.navi.stockexchange.exceptions.StockExchangeException;
import com.navi.stockexchange.models.Transaction;
import com.navi.stockexchange.services.OrderService;
import com.navi.stockexchange.services.StockExchangeService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class StockExchangeApp {

    private OrderService orderService;
    private StockExchangeService exchangeService;

    public StockExchangeApp(OrderService orderService) {
        this.orderService = orderService;
        this.exchangeService = new StockExchangeService(this.orderService);
    }

    public void processOrdersFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner  = new Scanner(file);
            while(scanner.hasNext()) {
                orderService.createOrder(scanner.nextLine());
            }
            List<Transaction> transactions = exchangeService.processOrders();
            transactions.forEach(transaction -> {
                System.out.println(transaction.getBuyerOrderId()+" "+transaction.getSellPrice()+" "+transaction.getQuantity()+" "+transaction.getSellerOrderId());
            });
        } catch (FileNotFoundException e) {
            throw new StockExchangeException("No file exist's at given path:"+filePath);
        }
    }
}
