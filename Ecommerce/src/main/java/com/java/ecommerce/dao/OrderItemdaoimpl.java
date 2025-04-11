package com.java.ecommerce.dao;

import com.java.ecommerce.model.OrderItems;
import com.java.ecommerce.util.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemdaoimpl implements OrderItemdao {

    Connection connection;
    PreparedStatement pst;

    @Override
    public boolean addOrderItem(OrderItems item) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, item.getOrderId());
            pst.setInt(2, item.getProductId());
            pst.setInt(3, item.getQuantity());
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OrderItems> getOrderItemsByOrderId(int orderId) {
        List<OrderItems> items = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM order_items WHERE order_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OrderItems item = new OrderItems();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public boolean updateOrderItem(OrderItems item) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "UPDATE order_items SET product_id = ?, quantity = ? WHERE order_item_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, item.getProductId());
            pst.setInt(2, item.getQuantity());
            pst.setInt(3, item.getOrderItemId());
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteOrderItemsByOrderId(int orderId) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "DELETE FROM order_items WHERE order_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, orderId);
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
