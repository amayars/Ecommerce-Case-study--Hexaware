package com.java.ecommerce.test;

import com.java.ecommerce.model.Customers;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomersTest {

    @Test
    public void testCustomersModel() {
        Customers customer = new Customers(1, "Alice", "alice@example.com", "pass123");

        assertEquals(1, customer.getCustomerId());
        assertEquals("Alice", customer.getName());
        assertEquals("alice@example.com", customer.getEmail());
        assertEquals("pass123", customer.getPassword());

        customer.setName("Bob");
        assertEquals("Bob", customer.getName());

        assertTrue(customer.toString().contains("Bob"));
    }
}
