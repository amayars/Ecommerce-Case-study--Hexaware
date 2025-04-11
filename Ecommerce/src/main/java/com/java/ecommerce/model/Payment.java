package com.java.ecommerce.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor 
@NoArgsConstructor
@ToString

public class Payment {
	private int paymentId;
    private int customerId;
    private int orderId;
    private BigDecimal amount;
    private Timestamp paymentDate;
    private PaymentStatus paymentStatus;

}
