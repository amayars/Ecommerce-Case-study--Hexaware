package com.java.ecommerce.dao;

import com.java.ecommerce.model.OrderItems;
import java.util.List;

public interface OrderItemdao {
    boolean addOrderItem(OrderItems item);
    List<OrderItems> getOrderItemsByOrderId(int orderId);
    boolean updateOrderItem(OrderItems item);
    boolean deleteOrderItemsByOrderId(int orderId);
}