package com.java.ecommerce.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.java.ecommerce.model.Products;
import com.java.ecommerce.myexceptions.ProductNotFoundException;
import com.java.ecommerce.util.ConnectionHelper;

public class Productdaoimpl implements Productdao {
    Connection connection;
    PreparedStatement pst;

    @Override
    public int addProduct(Products product) {
        int rowsAffected = 0;
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "INSERT INTO products (product_id, name, price, description, stockQuantity) VALUES (?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, product.getProductId());
            pst.setString(2, product.getName());
            pst.setBigDecimal(3, product.getPrice());
            pst.setString(4, product.getDescription());
            pst.setInt(5, product.getStockQuantity());

            rowsAffected = pst.executeUpdate(); 
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }


    @Override
    public boolean deleteProduct(int productId) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "DELETE FROM products WHERE product_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, productId);
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Products getProductById(int productId) throws ProductNotFoundException {
        Products product = null;
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM products WHERE product_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, productId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                product = new Products();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setDescription(rs.getString("description"));
                product.setStockQuantity(rs.getInt("stockQuantity"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Products> getAllProducts() {
        List<Products> productList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM products";
            pst = connection.prepareStatement(cmd);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setDescription(rs.getString("description"));
                product.setStockQuantity(rs.getInt("stockQuantity"));
                productList.add(product);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public boolean updateProduct(Products product) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "UPDATE products SET name = ?, price = ?, description = ?, stockQuantity = ? WHERE product_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setString(1, product.getName());
            pst.setBigDecimal(2, product.getPrice());
            pst.setString(3, product.getDescription());
            pst.setInt(4, product.getStockQuantity());
            pst.setInt(5, product.getProductId());
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
