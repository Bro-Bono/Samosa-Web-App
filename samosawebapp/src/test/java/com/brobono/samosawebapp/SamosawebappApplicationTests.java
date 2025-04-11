package com.brobono.samosawebapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.brobono.samosawebapp.services.OrderService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.brobono.samosawebapp.models.Order;
import com.brobono.samosawebapp.repositories.OrderRepository;


@SpringBootTest
@AutoConfigureMockMvc
class SamosawebappApplicationTests {
	@MockBean
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
	    MockitoAnnotations.openMocks(this);
	}

	// testing the creation of a new order
	@Test
	void testSaveOrder() {
		Order order = new Order();
		order.setCustomerName("John Doe");
		order.setCustomerEmail("john@example.com");
		order.setOrderDetails("2x Samosas");
		order.setStatus("NEW");

		when(orderRepository.save(order)).thenReturn(order);

		Order savedOrder = orderService.saveOrder(order);

		assertNotNull(savedOrder);
		assertEquals("John Doe", savedOrder.getCustomerName());
		assertEquals("john@example.com", savedOrder.getCustomerEmail());
		assertEquals("2x Samosas", savedOrder.getOrderDetails());
		assertEquals("NEW", savedOrder.getStatus());
		verify(orderRepository, times(1)).save(order);
	}

	// testing getAllOrders to see if we have a return
	@Test
	void testgetAllOrders() {
		Order order = new Order();

		order.setCustomerName("John Doe");
		order.setCustomerEmail("john@example.com");
		order.setOrderDetails("2x Samosas");
		order.setStatus("NEW");

		Order order2 = new Order();

		order2.setCustomerName("Babe Doe");
		order2.setCustomerEmail("bdoe@example.com");
		order2.setOrderDetails("2x Samosas");
		order2.setStatus("NEW");

		List<Order> orders = Arrays.asList(order, order2);

		when(orderRepository.findAll()).thenReturn(orders);

		List<Order> result = orderService.getAllOrders();

		assertEquals(2, result.size());
		assertEquals("John Doe", result.get(0).getCustomerName());
		assertEquals("Babe Doe", result.get(1).getCustomerName());

	}

	@Test
	public void testgetOrderById() {
		Order order = new Order();

		order.setCustomerName("John Doe");
		order.setCustomerEmail("john@example.com");
		order.setOrderDetails("2x Samosas");
		order.setStatus("NEW");
		order.setId(230L);
		assertNotNull(order.getId());
		assertEquals(order.getId(), 230L);

	}

	@Test
	void testGetOrderById_NotFound() {
		when(orderRepository.findById(99L)).thenReturn(Optional.empty());

		Order result = orderService.getOrderById(99L);

		assertNull(result);
		verify(orderRepository, times(1)).findById(99L);
	}

	@Test
	void testGetNewOrderCount() {
		when(orderRepository.countByStatus("NEW")).thenReturn(5L);

		int count = orderService.getNewOrderCount();

		assertEquals(5, count);
		verify(orderRepository, times(1)).countByStatus("NEW");
	}

	@Test
	void testGetInProgressOrderCount() {
		when(orderRepository.countByStatus("IN PROGRESS")).thenReturn(3L);

		int count = orderService.getInProgressOrderCount();

		assertEquals(3, count);
		verify(orderRepository, times(1)).countByStatus("IN PROGRESS");
	}

	@Test
	void testGetCompletedOrderCount() {
		when(orderRepository.countByStatus("COMPLETED")).thenReturn(8L);

		int count = orderService.getCompletedOrderCount();

		assertEquals(8, count);
		verify(orderRepository, times(1)).countByStatus("COMPLETED");
	}

	private static final String[] STATUSES = { "NEW", "IN PROGRESS", "COMPLETED" };

	static Stream<org.junit.jupiter.params.provider.Arguments> statusTransitions() {
		return Stream.of(STATUSES)
				.flatMap(fromStatus -> Stream.of(STATUSES).filter(toStatus -> !fromStatus.equals(toStatus)) // only
																											// different
																											// status
																											// are
																											// paired
						.map(toStatus -> org.junit.jupiter.params.provider.Arguments.of(fromStatus, toStatus)));
	}

	@ParameterizedTest
	@MethodSource("statusTransitions")
	void testUpdateOrderStatus(String fromStatus, String toStatus) throws Exception {
		// Arrange
		Order order = new Order();
		order.setId(1L);
		order.setStatus(fromStatus);
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
	    when(orderRepository.save(any(Order.class))).thenReturn(order); // Mock save behavior

		// Act
		mockMvc.perform(put("/orders/1/status").param("status", toStatus)).andExpect(status().isOk());

		// Assert
		assertEquals(toStatus, order.getStatus());
	    verify(orderRepository).save(order); // Verify the save was called

	}

	// ************************************************************************************************************************
	// TEST CASE

}
