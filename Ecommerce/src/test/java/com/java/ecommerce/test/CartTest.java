package com.java.ecommerce.test;

import com.java.ecommerce.dao.*;
import com.java.ecommerce.model.Cart;
import com.java.ecommerce.model.Customers;
import com.java.ecommerce.model.Products;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class CartTest {

    private static Cartdao cartDao;
    private static Customerdao customerDao;
    private static Productdao productDao;
    private static int customerId;
    private static int productId;

    @BeforeClass
    public static void setup() throws SQLException {
        cartDao = new Cartdaoimpl();
        customerDao = new Customerdaoimpl();
        productDao = new Productdaoimpl();

        
        Customers customer = new Customers();
        customer.setName("Test User");
        customer.setEmail("test" + System.currentTimeMillis() + "@example.com");
        customer.setPassword("test123");
        customerId = customerDao.addCustomer(customer);

        
        Products product = new Products();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));
        productId = productDao.addProduct(product);

       
        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        cart.setProductId(productId);
        cart.setQuantity(1);
        cartDao.addToCart(cart);
    }

    @Test
    public void testAddToCart() {
        Cart cart = new Cart(0, customerId, productId, 2);
        boolean added = cartDao.addToCart(cart);
        assertTrue("Cart item should be added", added);
    }

    @Test
    public void testGetCartByCustomerId() {
        List<Cart> cartList = cartDao.getCartByCustomerId(customerId);
        assertNotNull("Cart list should not be null", cartList);
        assertFalse("Cart list should not be empty", cartList.isEmpty());
    }

    @Test
    public void testUpdateCart() {
        List<Cart> cartList = cartDao.getCartByCustomerId(customerId);
        assertFalse("Cart list should not be empty", cartList.isEmpty());

        Cart cart = cartList.get(0);
        boolean updated = cartDao.updateCartQuantity(cart.getCartId(), 5);
        assertTrue("Cart quantity should be updated", updated);
    }

    @Test
    public void testDeleteFromCart() {
        boolean deleted = cartDao.removeFromCart(customerId, productId);
        assertTrue("Cart item should be deleted", deleted);
    }
}
