package com.java.ecommerce.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.java.ecommerce.model.Customers;
import com.java.ecommerce.myexceptions.CustomerNotFoundException;
import com.java.ecommerce.util.ConnectionHelper;

public class Customerdaoimpl implements Customerdao{
	Connection connection;
	PreparedStatement pst;
	ResultSet rs;
	

	@Override
	public int addCustomer(Customers customer) throws SQLException {
	    int customerId = -1;
	    try {
	        connection = ConnectionHelper.getConnection();
	        String cmd = "INSERT INTO customers (name, email, password) VALUES (?, ?, ?)";
	        pst = connection.prepareStatement(cmd, PreparedStatement.RETURN_GENERATED_KEYS);
	        pst.setString(1, customer.getName());
	        pst.setString(2, customer.getEmail());
	        pst.setString(3, customer.getPassword());
	        pst.executeUpdate();

	        ResultSet rs = pst.getGeneratedKeys();
	        if (rs.next()) {
	            customerId = rs.getInt(1); 
	        }
	    } catch (SQLIntegrityConstraintViolationException e) {
	        
	        System.out.println("❌ Email already exists: " + customer.getEmail());
	        return 0; 
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        throw new SQLException("Error adding customer", e);
	    } finally {
	        closeResources();
	    }
	    return customerId;
	}

	 @Override
	 public boolean deleteCustomer(int customerId) {
	     try {
	         connection = ConnectionHelper.getConnection();
	         String cmd = "DELETE FROM customers WHERE Customer_id = ?";
	         pst = connection.prepareStatement(cmd);
	         pst.setInt(1, customerId);
	         return pst.executeUpdate() > 0;
	     } catch (ClassNotFoundException | SQLException e) { 
	         e.printStackTrace();
	     }
	     return false;
	 }

	 @Override
	 public Customers getCustomerById(int customerId) throws CustomerNotFoundException {
	     Customers customer = null;
	     try {
	         connection = ConnectionHelper.getConnection();
	         String cmd = "SELECT * FROM customers WHERE Customer_id = ?";
	         pst = connection.prepareStatement(cmd);
	         pst.setInt(1, customerId);
	         rs = pst.executeQuery();

	         if (rs.next()) {
	             customer = new Customers();
	             customer.setCustomerId(rs.getInt("Customer_id"));
	             customer.setName(rs.getString("name"));
	             customer.setEmail(rs.getString("email"));
	             customer.setPassword(rs.getString("password"));
	         } else {
	             
	             throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
	         }
	     } catch (ClassNotFoundException | SQLException e) {
	         e.printStackTrace();
	     }
	     return customer;
	 }


	    @Override
	    public List<Customers> getAllCustomers() {
	        List<Customers> customerList = new ArrayList<>();
	        try {
	            connection = ConnectionHelper.getConnection();
	            String cmd = "SELECT * FROM customers";
	            pst = connection.prepareStatement(cmd);
	            ResultSet rs = pst.executeQuery();
	            while (rs.next()) {
	                Customers customer = new Customers();
	                customer.setCustomerId(rs.getInt("Customer_id"));
	                customer.setName(rs.getString("name"));
	                customer.setEmail(rs.getString("email"));
	                customer.setPassword(rs.getString("password"));
	                customerList.add(customer);
	            }
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	        return customerList;
	    }

	    @Override
	    public boolean updateCustomer(Customers customer) {
	        try {
	            connection = ConnectionHelper.getConnection();
	            String cmd = "UPDATE customers SET name = ?, email = ?, password = ? WHERE Customer_id = ?";
	            pst = connection.prepareStatement(cmd);
	            pst.setString(1, customer.getName());
	            pst.setString(2, customer.getEmail());
	            pst.setString(3, customer.getPassword());
	            pst.setInt(4, customer.getCustomerId());
	            return pst.executeUpdate() > 0;
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    private void closeResources() {
	        try {
	            if (rs != null) rs.close();
	            if (pst != null) pst.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    }



	