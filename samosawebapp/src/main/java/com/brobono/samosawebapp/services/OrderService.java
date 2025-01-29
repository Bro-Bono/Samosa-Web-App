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
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCompletedOrderCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
