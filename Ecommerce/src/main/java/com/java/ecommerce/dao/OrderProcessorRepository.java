package com.java.ecommerce.dao;

import com.java.ecommerce.model.Customers;
import com.java.ecommerce.model.Products;
import com.java.ecommerce.myexceptions.OrderNotFoundException;
import com.java.ecommerce.myexceptions.ProductNotFoundException;

import java.sql.SQLException;
import java.util.Map;

public interface OrderProcessorRepository {
    int createProduct(Products product);
    int createCustomer(Customers customer) throws SQLException;
    boolean deleteProduct(int productId);
    boolean deleteCustomer(int customerId) throws SQLException;
    boolean addToCart(Customers customer, Products product, int quantity);
    boolean removeFromCart(Customers customer, Products product);
    Map<Products, Integer> getCartByCustomerId(int customerId) throws ProductNotFoundException;
    boolean placeOrder(Customers customer, Map<Products, Integer> productMap, String shippingAddress)
            throws ProductNotFoundException;
    Map<Products, Integer> getOrdersByCustomer(int customerId)
            throws OrderNotFoundException, ProductNotFoundException;
}
