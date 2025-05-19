package com.brobono.samosawebapp.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brobono.samosawebapp.models.ArchivedOrder;
import com.brobono.samosawebapp.models.Order;
import com.brobono.samosawebapp.repositories.ArchivedOrderRepository;
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
    

    @Autowired
    private ArchivedOrderRepository archivedOrderRepository;

    public void archiveOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // If not already completed, mark as cancelled
            if (!"COMPLETED".equalsIgnoreCase(order.getStatus())) {
                order.setStatus("CANCELLED");
            }

            // Copy data to archived order
            ArchivedOrder archivedOrder = new ArchivedOrder();
            archivedOrder.setId(order.getId());
            archivedOrder.setCustomerName(order.getCustomerName());
            archivedOrder.setCustomerEmail(order.getCustomerEmail());
            archivedOrder.setOrderDetails(order.getOrderDetails());
            archivedOrder.setStatus(order.getStatus()); // Will now be CANCELLED or COMPLETED
            archivedOrder.setArchivedAt(LocalDateTime.now());

            archivedOrderRepository.save(archivedOrder);
            orderRepository.delete(order);
        }
    }

  }

