package com.java.ecommerce.dao;

import com.java.ecommerce.model.Payment;
import java.util.List;

public interface Paymentdao {
    boolean addPayment(Payment payment);
    Payment getPaymentById(int paymentId);
    List<Payment> getPaymentsByCustomerId(int customerId);
    boolean updatePaymentStatus(int paymentId, String status);
    boolean deletePayment(int paymentId);
}
