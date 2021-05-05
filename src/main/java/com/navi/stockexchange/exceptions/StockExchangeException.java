package com.navi.stockexchange.exceptions;

import java.util.function.Supplier;

public class StockExchangeException extends RuntimeException {

    public StockExchangeException() {
    }

    public StockExchangeException(String message) {
        super(message);
    }

    public StockExchangeException(Supplier<String> messageSupplier) {
        super(messageSupplier.get());
    }

    public StockExchangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockExchangeException(Throwable cause) {
        super(cause);
    }

    public StockExchangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
