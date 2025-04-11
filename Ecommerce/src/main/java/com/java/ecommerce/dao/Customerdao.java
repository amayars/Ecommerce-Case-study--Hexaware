package com.java.ecommerce.dao;

import com.java.ecommerce.model.Customers;
import com.java.ecommerce.myexceptions.CustomerNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface Customerdao {
	int addCustomer(Customers customer) throws SQLException;
	boolean deleteCustomer(int customerId) throws SQLException;
	Customers getCustomerById(int customerId) throws CustomerNotFoundException;
    List<Customers> getAllCustomers() throws SQLException;
    boolean updateCustomer(Customers customer) throws SQLException;
    
    }


