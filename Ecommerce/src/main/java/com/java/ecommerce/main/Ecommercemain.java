package com.java.ecommerce.main;

import com.java.ecommerce.dao.OrderProcessorRepository;
import com.java.ecommerce.dao.OrderProcessorRepositoryImpl;
import com.java.ecommerce.model.Customers;
import com.java.ecommerce.model.Products;
import com.java.ecommerce.myexceptions.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.*;

public class Ecommercemain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OrderProcessorRepository orderService = new OrderProcessorRepositoryImpl();

        while (true) {
            System.out.println("\n=== E-Commerce Menu ===");
            System.out.println("1. Register Customer");
            System.out.println("2. Create Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Add to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Place Order");
            System.out.println("7. View Customer Orders");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            try {
                switch (choice) {
                    case 1: {
                        System.out.print("Enter customer name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter password: ");
                        String password = sc.nextLine();

                        Customers c = new Customers(0, name, email, password);
                        int customerId = orderService.createCustomer(c);
                        System.out.println(customerId > 0 ? "Customer registered." : "Failed to register.");
                        break;
                    }

                    case 2: {
                        System.out.print("Enter product name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter price: ");
                        BigDecimal price = sc.nextBigDecimal();
                        sc.nextLine();
                        System.out.print("Enter description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter Stock quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();

                        Products p = new Products(0, name, price, desc, qty);
                        int added = orderService.createProduct(p);
                        System.out.println(added > 0 ? "Product added." : "Failed to add product.");
                        break;
                    }

                    case 3: {
                        System.out.print("Enter product ID to delete: ");
                        int productId = sc.nextInt();
                        sc.nextLine();
                        boolean deleted = orderService.deleteProduct(productId);
                        System.out.println(deleted ? "Product deleted." : "Failed to delete.");
                        break;
                    }

                    case 4: {
                        System.out.print("Enter customer ID: ");
                        int customerId = sc.nextInt();
                        System.out.print("Enter product ID: ");
                        int productId = sc.nextInt();
                        System.out.print("Enter quantity: ");
                        int quantity = sc.nextInt();
                        sc.nextLine();

                        Customers c = new Customers();
                        c.setCustomerId(customerId);
                        Products p = new Products();
                        p.setProductId(productId);

                        boolean added = orderService.addToCart(c, p, quantity);
                        System.out.println(added ? "Product added to cart." : "Failed to add to cart.");
                        break;
                    }

                    case 5: {
                        System.out.print("Enter customer ID: ");
                        int customerId = sc.nextInt();
                        sc.nextLine();
                        Customers c = new Customers();
                        c.setCustomerId(customerId);

                        Map<Products, Integer> cartItems = orderService.getCartByCustomerId(c.getCustomerId());
                        System.out.println("Cart Items:");
                        for (Map.Entry<Products, Integer> entry : cartItems.entrySet()) {
                            System.out.println(entry.getKey() + " -> Quantity: " + entry.getValue());
                        }

                        break;
                    }

                    case 6: {
                        System.out.print("Enter customer ID: ");
                        int customerId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter shipping address: ");
                        String shippingAddress = sc.nextLine();

                        Customers c = new Customers();
                        c.setCustomerId(customerId);

                        // Here you would fetch cart contents, mockup for now
                        Map<Products, Integer> cartList = orderService.getCartByCustomerId(c.getCustomerId());

                        // Populate with real cart contents

                        boolean success = orderService.placeOrder(c, cartList, shippingAddress);
                        System.out.println(success ? "Order placed successfully." : "Order failed.");
                        break;
                    }

                    case 7: {
                        System.out.print("Enter customer ID: ");
                        int customerId = sc.nextInt();
                        sc.nextLine();

                        Map<Products, Integer> orders = orderService.getOrdersByCustomer(customerId);
                        System.out.println("Customer Orders:");
                        for (Map.Entry<Products, Integer> entry : orders.entrySet()) {
                            System.out.println("Product: " + entry.getKey() + ", Quantity: " + entry.getValue());
                        }
                        break;
                    }

                    case 8: {
                        System.out.println("Exiting application...");
                        sc.close();
                        return;
                    }

                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            } catch (ProductNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }

            }
        }
    }

