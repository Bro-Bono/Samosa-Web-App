package com.brobono.samosawebapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.brobono.samosawebapp.models.Order;
import com.brobono.samosawebapp.services.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	  @Autowired
	    private OrderService orderService;

	    // Get all orders
	    @GetMapping
	    public List<Order> getAllOrders() {
	        return orderService.getAllOrders();
	    }

	    // Get an order by ID
	    @GetMapping("/{id}")
	    public Order getOrderById(@PathVariable Long id) {
	        return orderService.getOrderById(id);
	    }

	    // Create a new order
	    @PostMapping
	    public Order createOrder(@RequestBody Order order) {
	        return orderService.saveOrder(order);
	    }

	    // Update an order's status
	    @PutMapping("/{id}/status")
	    public Order updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
	        Order order = orderService.getOrderById(id);
	        if (order != null) {
	            order.setStatus(status);
	            return orderService.saveOrder(order);
	        }
	        return null;
	    }
	    
//      WE WILL IMPLEMENT THIS LATER
//	    // Delete an order
//	    @DeleteMapping("/{id}")
//	    public void deleteOrder(@PathVariable Long id) {
//	        orderService.deleteOrder(id);
//	    }
}
