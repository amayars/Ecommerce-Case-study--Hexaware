package com.java.ecommerce.test;

import com.java.ecommerce.model.Payment;
import com.java.ecommerce.model.PaymentStatus;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class PaymentTest {

    @Test
    public void testPaymentModel() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Payment payment = new Payment(1, 2, 3, new BigDecimal("499.99"), timestamp, PaymentStatus.Completed);

        assertEquals(1, payment.getPaymentId());
        assertEquals(2, payment.getCustomerId());
        assertEquals(new BigDecimal("499.99"), payment.getAmount());
        assertEquals(PaymentStatus.Completed, payment.getPaymentStatus());

        payment.setPaymentStatus(PaymentStatus.Pending);
        assertEquals(PaymentStatus.Pending, payment.getPaymentStatus());

        assertTrue(payment.toString().contains("Pending"));
    }
}
