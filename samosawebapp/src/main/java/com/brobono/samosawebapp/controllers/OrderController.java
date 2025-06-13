package com.brobono.samosawebapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.brobono.samosawebapp.models.Order;
import com.brobono.samosawebapp.services.OrderService;

@RestController
@RequestMapping("/orders")
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
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
		// debugging
		System.out.println("Received request to update order " + id + " to status " + status);
		status = status.replace("%20", " "); // Handle encoded spaces
		Order order = orderService.getOrderById(id);
		if (order == null) {
			System.out.println("Order not found!");
			return ResponseEntity.notFound().build();
		}
		if (!status.equals("NEW") && !status.equals("IN PROGRESS") && !status.equals("COMPLETED")) {
			System.out.println("Invalid status update: " + status);
			return ResponseEntity.badRequest().build();
		}

		order.setStatus(status);
		orderService.saveOrder(order);

		System.out.println("Order updated successfully!" + " and orderedAt is " + order.getOrderedAtIso());
		return ResponseEntity.ok(order);
	}

	@GetMapping("/summary")
	public Map<String, Integer> getOrderSummary() {
		Map<String, Integer> summary = new HashMap<>();
		summary.put("newOrders", orderService.getNewOrderCount());
		summary.put("inProgress", orderService.getInProgressOrderCount());
		summary.put("completed", orderService.getCompletedOrderCount());
		;
		return summary;
	}

	@PostMapping("/{id}/archive")
	public ResponseEntity<?> archiveOrder(@PathVariable Long id) {
		System.out.println("Archiving order " + id); // debug
		orderService.archiveOrder(id); // same logic for cancel and archive
		return ResponseEntity.ok().build();
	}
}
