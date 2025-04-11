package com.java.ecommerce.dao;

import java.util.List;
import com.java.ecommerce.model.Products;
import com.java.ecommerce.myexceptions.ProductNotFoundException;

public interface Productdao {
	 int addProduct(Products product);
	 Products getProductById(int productId) throws ProductNotFoundException;
	    boolean updateProduct(Products product);
	    boolean deleteProduct(int productId);
	    List<Products> getAllProducts();

}
