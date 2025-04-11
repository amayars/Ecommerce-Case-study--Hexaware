package com.java.ecommerce.dao;

import com.java.ecommerce.model.Payment;
import com.java.ecommerce.model.PaymentStatus;
import com.java.ecommerce.util.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Paymentdaoimpl implements Paymentdao {

    Connection connection;
    PreparedStatement pst;

    @Override
    public boolean addPayment(Payment payment) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "INSERT INTO payments (customer_id, order_id, amount, payment_date, payment_status) VALUES (?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, payment.getCustomerId());
            pst.setInt(2, payment.getOrderId());
            pst.setBigDecimal(3, payment.getAmount());
            pst.setTimestamp(4, payment.getPaymentDate());
            pst.setString(5, payment.getPaymentStatus().toString());
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        Payment payment = null;
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM payments WHERE payment_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, paymentId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setCustomerId(rs.getInt("customer_id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                payment.setPaymentStatus(PaymentStatus.valueOf(rs.getString("payment_status")));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }

    @Override
    public List<Payment> getPaymentsByCustomerId(int customerId) {
        List<Payment> payments = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM payments WHERE customer_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, customerId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setCustomerId(rs.getInt("customer_id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                payment.setPaymentStatus(PaymentStatus.valueOf(rs.getString("payment_status")));
                payments.add(payment);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public boolean updatePaymentStatus(int paymentId, String status) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "UPDATE payments SET payment_status = ? WHERE payment_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setString(1, status);
            pst.setInt(2, paymentId);
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePayment(int paymentId) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "DELETE FROM payments WHERE payment_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, paymentId);
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
