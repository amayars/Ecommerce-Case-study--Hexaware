package com.java.ecommerce.dao;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.java.ecommerce.model.Cart;
import com.java.ecommerce.dao.Cartdao;
import com.java.ecommerce.dao.Cartdaoimpl;
import com.java.ecommerce.util.ConnectionHelper;

public class Cartdaoimpl implements Cartdao {
    Connection connection;
    PreparedStatement pst;

    @Override
    public boolean addToCart(Cart cart) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            
            String checkCmd = "SELECT * FROM cart WHERE customer_id = ? AND product_id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkCmd)) {
                checkStmt.setInt(1, cart.getCustomerId());
                checkStmt.setInt(2, cart.getProductId());
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    
                    String updateCmd = "UPDATE cart SET quantity = quantity + ? WHERE customer_id = ? AND product_id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateCmd)) {
                        updateStmt.setInt(1, cart.getQuantity());
                        updateStmt.setInt(2, cart.getCustomerId());
                        updateStmt.setInt(3, cart.getProductId());
                        return updateStmt.executeUpdate() > 0;
                    }
                } else {
                    
                    String insertCmd = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertCmd)) {
                        insertStmt.setInt(1, cart.getCustomerId());
                        insertStmt.setInt(2, cart.getProductId());
                        insertStmt.setInt(3, cart.getQuantity());
                        return insertStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean removeFromCart(int customerId, int productId) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, customerId);
            pst.setInt(2, productId);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean updateCartQuantity(int cartId, int quantity) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "UPDATE cart SET quantity = ? WHERE cart_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, quantity);
            pst.setInt(2, cartId);
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Cart getCartById(int cartId) {
        Cart cart = null;
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM cart WHERE cart_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, cartId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setCustomerId(rs.getInt("customer_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public List<Cart> getCartByCustomerId(int customerId) {
        List<Cart> cartList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM cart WHERE customer_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, customerId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setCustomerId(rs.getInt("customer_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));
                cartList.add(cart);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cartList;
    }

    @Override
    public boolean clearCart(int customerId) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "DELETE FROM cart WHERE customer_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, customerId);
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	
}
