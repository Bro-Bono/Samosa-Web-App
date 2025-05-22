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

import com.brobono.samosawebapp.models.ArchivedOrder;
import com.brobono.samosawebapp.models.Order;
import com.brobono.samosawebapp.repositories.OrderRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class ArchivedOrderTests {
	@MockBean
	private OrderRepository orderRepository;

	@InjectMocks
	//does not mock the class itself; it creates a real instance and injects mocks into it.
	private OrderService orderService;

	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
	    MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void test_orderId() {
		//Create object of archivedorder in order to test 
		ArchivedOrder order = new ArchivedOrder();
		
		order.setCustomerName("John Doe");
		order.setCustomerEmail("john@example.com");
		order.setOrderDetails("2x Samosas");
		order.setStatus("NEW");
		order.setId(230L);
		
		assertNotNull(order.getId());
		assertEquals(order.getId(), 230L);
	}
	
	
	@Test
	public void test_orderCustomerName() {
		//Create object of archivedorder in order to test 
		ArchivedOrder order = new ArchivedOrder();
		
		order.setCustomerName("John Doe");
		order.setCustomerEmail("john@example.com");
		order.setOrderDetails("2x Samosas");
		order.setStatus("NEW");
		order.setId(230L);
		
		assertNotNull(order.getCustomerName());
		assertEquals(order.getCustomerName(), "John Doe");
	}
	
	@Test
	public void test_orderCustomerEmail() {
		//Create object of archivedorder in order to test 
		ArchivedOrder order = new ArchivedOrder();
		
		order.setCustomerName("John Doe");
		order.setCustomerEmail("john@example.com");
		order.setOrderDetails("2x Samosas");
		order.setStatus("NEW");
		order.setId(230L);
		
		assertNotNull(order.getCustomerEmail());
		assertEquals(order.getCustomerEmail(), "john@example.com");
	}
	
	@Test
	public void test_orderDetails() {
		//Create object of archivedorder in order to test 
		ArchivedOrder order = new ArchivedOrder();
		
		order.setCustomerName("John Doe");
		order.setCustomerEmail("john@example.com");
		order.setOrderDetails("2x Samosas");
		order.setStatus("NEW");
		order.setId(230L);
		
		assertNotNull(order.getOrderDetails());
		assertEquals(order.getOrderDetails(), "2x Samosas");
	}
	
	@Test
	public void test_orderStatus() {
		//Create object of archivedorder in order to test 
		ArchivedOrder order = new ArchivedOrder();
		
		order.setCustomerName("John Doe");
		order.setCustomerEmail("john@example.com");
		order.setOrderDetails("2x Samosas");
		order.setStatus("NEW");
		order.setId(230L);
		
		assertNotNull(order.getStatus());
		assertEquals(order.getStatus(), "NEW");
	}

}


