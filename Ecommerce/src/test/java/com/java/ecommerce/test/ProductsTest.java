package com.java.ecommerce.test;

import com.java.ecommerce.model.Products;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProductsTest {

    @Test
    public void testProductsModel() {
        Products product = new Products(1, "Phone", new BigDecimal("799.99"), "Smartphone", 50);

        assertEquals(1, product.getProductId());
        assertEquals("Phone", product.getName());
        assertEquals("Smartphone", product.getDescription());
        assertEquals(50, product.getStockQuantity());

        product.setStockQuantity(30);
        assertEquals(30, product.getStockQuantity());

        assertTrue(product.toString().contains("Phone"));
    }
}
