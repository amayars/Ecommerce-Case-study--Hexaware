package com.java.ecommerce.model;
import java.math.BigDecimal;

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

public class Products {
	private int productId;
    private String name;
    private BigDecimal price;
    private String description;
    private int stockQuantity;

}
