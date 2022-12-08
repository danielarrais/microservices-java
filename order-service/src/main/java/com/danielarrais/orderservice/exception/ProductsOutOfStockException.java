package com.danielarrais.orderservice.exception;

public class ProductsOutOfStockException extends Exception {
    public ProductsOutOfStockException(String message) {
        super(message);
    }
}
