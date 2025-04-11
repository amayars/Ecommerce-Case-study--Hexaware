package com.java.ecommerce.dao;

import com.java.ecommerce.model.*;
import com.java.ecommerce.myexceptions.OrderNotFoundException;
import com.java.ecommerce.myexceptions.ProductNotFoundException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

public class OrderProcessorRepositoryImpl implements OrderProcessorRepository {

    private Productdao productDao;
    private Customerdao customerDao;
    private Cartdao cartDao;
    private Orderdao orderDao;
    private OrderItemdao orderItemDao;

    public OrderProcessorRepositoryImpl() {
        this.productDao = new Productdaoimpl();
        this.customerDao = new Customerdaoimpl();
        this.cartDao = new Cartdaoimpl();
        this.orderDao = new Orderdaoimpl();
        this.orderItemDao = new OrderItemdaoimpl();
    }

    public OrderProcessorRepositoryImpl(Productdao productDao, Customerdao customerDao,
                                        Cartdao cartDao, Orderdao orderDao, OrderItemdao orderItemDao) {
        this.productDao = productDao;
        this.customerDao = customerDao;
        this.cartDao = cartDao;
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
    }

    @Override
    public int createProduct(Products product) {
        return productDao.addProduct(product);
    }

    @Override
    public int createCustomer(Customers customer) throws SQLException {
        return customerDao.addCustomer(customer);
    }

    @Override
    public boolean deleteProduct(int productId) {
        return productDao.deleteProduct(productId);
    }

    @Override
    public boolean deleteCustomer(int customerId) throws SQLException {
        return customerDao.deleteCustomer(customerId);
    }

    @Override
    public boolean addToCart(Customers customer, Products product, int quantity) {
        Cart cart = new Cart();
        cart.setCustomerId(customer.getCustomerId());
        cart.setProductId(product.getProductId());
        cart.setQuantity(quantity);
        return cartDao.addToCart(cart);
    }

    @Override
    public boolean removeFromCart(Customers customer, Products product) {
        return cartDao.removeFromCart(customer.getCustomerId(), product.getProductId());
    }

    @Override
    public Map<Products, Integer> getCartByCustomerId(int customerId) throws ProductNotFoundException {
        List<Cart> cartItems = cartDao.getCartByCustomerId(customerId);
        Map<Products, Integer> cartMap = new HashMap<>();

        for (Cart cart : cartItems) {
            Products product = productDao.getProductById(cart.getProductId()); 
            cartMap.put(product, cart.getQuantity());
        }

        return cartMap;
    }


    @Override
    public boolean placeOrder(Customers customer, Map<Products, Integer> productMap, String shippingAddress) {
        Orders order = new Orders();
        order.setCustomerId(customer.getCustomerId());
        order.setShippingAddress(shippingAddress);
        order.setStatus(OrderStatus.Placed);

        
        BigDecimal total_Price = BigDecimal.ZERO;
        for (Map.Entry<Products, Integer> entry : productMap.entrySet()) {
            Products product = entry.getKey();
            int quantity = entry.getValue();

            BigDecimal price = product.getPrice();
            BigDecimal quantityBD = BigDecimal.valueOf(quantity);

            total_Price = total_Price.add(price.multiply(quantityBD));
        }
        order.setTotalPrice(total_Price);

        order.setOrderDate(new java.sql.Timestamp(System.currentTimeMillis()));

        boolean orderCreated = orderDao.createOrder(order);
        if (!orderCreated) return false;

        int orderId = orderDao.getLastInsertedOrderId();

        for (Map.Entry<Products, Integer> entry : productMap.entrySet()) {
            OrderItems item = new OrderItems();
            item.setOrderId(orderId);
            item.setProductId(entry.getKey().getProductId());
            item.setQuantity(entry.getValue());
            orderItemDao.addOrderItem(item);
        }

        return true;
    }


    @Override
    public Map<Products, Integer> getOrdersByCustomer(int customerId) 
            throws OrderNotFoundException, ProductNotFoundException {

        List<Orders> orders = orderDao.getOrdersByCustomer(customerId);
        
        if (orders == null || orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for customer ID: " + customerId);
        }

        Map<Products, Integer> result = new HashMap<>();

        for (Orders order : orders) {
            List<OrderItems> items = orderItemDao.getOrderItemsByOrderId(order.getOrderId());

            for (OrderItems item : items) {
                Products product = productDao.getProductById(item.getProductId());

                if (product == null) {
                    throw new ProductNotFoundException("Product not found for ID: " + item.getProductId());
                }

                result.merge(product, item.getQuantity(), Integer::sum);
            }
        }

        return result;
    }


}
