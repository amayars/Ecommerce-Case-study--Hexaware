package com.java.ecommerce.model;

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

public class OrderItems {
	 private int orderItemId;
	    private int orderId;
	    private int productId;
	    private int quantity;

}
