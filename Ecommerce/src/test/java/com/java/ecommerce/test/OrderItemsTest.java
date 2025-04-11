package com.java.ecommerce.test;

import com.java.ecommerce.model.OrderItems;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderItemsTest {

    @Test
    public void testOrderItemsModel() {
        OrderItems item = new OrderItems(1, 100, 200, 3);

        assertEquals(1, item.getOrderItemId());
        assertEquals(100, item.getOrderId());
        assertEquals(200, item.getProductId());
        assertEquals(3, item.getQuantity());

        item.setQuantity(5);
        assertEquals(5, item.getQuantity());

        assertTrue(item.toString().contains("5"));
    }
}
