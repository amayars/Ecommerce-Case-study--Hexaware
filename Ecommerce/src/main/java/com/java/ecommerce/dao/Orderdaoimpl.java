package com.java.ecommerce.dao;

import com.java.ecommerce.model.OrderStatus;

import com.java.ecommerce.model.Orders;
import com.java.ecommerce.myexceptions.OrderNotFoundException;
import com.java.ecommerce.util.ConnectionHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



public class Orderdaoimpl implements Orderdao {
    Connection connection;
    PreparedStatement pst;

    @Override
    public boolean createOrder(Orders order) {
        
        if (order == null ||
            order.getCustomerId() <= 0 ||
            order.getOrderDate() == null ||
            order.getTotalPrice() == null ||
            order.getShippingAddress() == null ||
            order.getStatus() == null) {
            return false;
        }

        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "INSERT INTO orders (customer_id, order_date, total_price, shipping_address, status) VALUES (?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, order.getCustomerId());
            pst.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            pst.setBigDecimal(3, order.getTotalPrice());
            pst.setString(4, order.getShippingAddress());
            pst.setString(5, order.getStatus().name());

            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }


    @Override
    public Orders getOrderById(int orderId) {
        Orders order = null;

        try {
            connection = ConnectionHelper.getConnection();
            String query = "SELECT * FROM orders WHERE order_id = ?";
            pst = connection.prepareStatement(query);
            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                order = new Orders();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalPrice(BigDecimal.valueOf(rs.getDouble("total_price")));
            }

        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            closeResources(); 
        }

        return order; 
    }


    public List<Orders> getOrdersByCustomer(int customerId) throws OrderNotFoundException {
        List<Orders> orders = new ArrayList<>();

        try {
            connection = ConnectionHelper.getConnection();
            String query = "SELECT * FROM orders WHERE customer_id = ?";
            pst = connection.prepareStatement(query);
            pst.setInt(1, customerId);
            ResultSet rs = pst.executeQuery();

            rs = pst.executeQuery();

            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalPrice(rs.getBigDecimal("total_price"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                orders.add(order);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for customer ID: " + customerId);
        }

        return orders;
    }




    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "UPDATE orders SET status = ? WHERE order_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setString(1, status.name());  
            pst.setInt(2, orderId);

            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    @Override
    public boolean deleteOrder(int orderId) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "DELETE FROM orders WHERE order_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, orderId);

            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

   
    private void closeResources() {
        try {
            if (pst != null) pst.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
	@Override
	public int getLastInsertedOrderId() {
	    int orderId = -1;
	    ResultSet rs = null; 
	    
	    try {
	        connection = ConnectionHelper.getConnection();
	        String cmd = "SELECT MAX(order_id) FROM orders";
	        pst = connection.prepareStatement(cmd);
	        rs = pst.executeQuery(); 

	        if (rs.next()) {
	            orderId = rs.getInt(1);
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();  
	            if (pst != null) pst.close(); 
	            if (connection != null) connection.close(); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return orderId;
	}

}
