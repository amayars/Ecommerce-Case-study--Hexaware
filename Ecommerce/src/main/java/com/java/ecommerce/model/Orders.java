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

public class Orders {
	private int orderId;
    private int customerId;
    private Timestamp orderDate;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private OrderStatus status;
	

}
