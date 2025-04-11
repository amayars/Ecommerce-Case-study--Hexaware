package com.java.ecommerce.dao;

import com.java.ecommerce.model.Cart;
import java.util.List;

public interface Cartdao {
    boolean addToCart(Cart cart);
    boolean removeFromCart(int customerId, int productId);
    boolean updateCartQuantity(int cartId, int quantity);
    Cart getCartById(int cartId);
    List<Cart> getCartByCustomerId(int customerId);
    boolean clearCart(int customerId);

}
