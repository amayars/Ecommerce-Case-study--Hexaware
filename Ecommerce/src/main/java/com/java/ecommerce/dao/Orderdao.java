package com.java.ecommerce.dao;

import com.java.ecommerce.model.OrderStatus;
import com.java.ecommerce.model.Orders;
import com.java.ecommerce.myexceptions.OrderNotFoundException;

import java.util.List;

public interface Orderdao {
    boolean createOrder(Orders order);
    Orders getOrderById(int orderId) throws OrderNotFoundException;
    List<Orders> getOrdersByCustomer(int customerId) throws OrderNotFoundException;
    boolean updateOrderStatus(int orderId, OrderStatus status);
    boolean deleteOrder(int orderId);
    int getLastInsertedOrderId();
 
}
