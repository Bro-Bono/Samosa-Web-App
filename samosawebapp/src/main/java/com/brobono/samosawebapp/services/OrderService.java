package com.brobono.samosawebapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brobono.samosawebapp.models.Order;
import com.brobono.samosawebapp.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Order getOrderById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	public int getNewOrderCount() {
		return Math.toIntExact(orderRepository.countByStatus("NEW"));
	}

	public int getInProgressOrderCount() {
		return Math.toIntExact(orderRepository.countByStatus("IN PROGRESS"));
	}

	public int getCompletedOrderCount() {
		return Math.toIntExact(orderRepository.countByStatus("COMPLETED"));
	}
	
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}
