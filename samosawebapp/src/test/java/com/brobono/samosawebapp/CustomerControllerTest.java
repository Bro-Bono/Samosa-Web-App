package com.brobono.samosawebapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.brobono.samosawebapp.controllers.CustomerController;
import com.brobono.samosawebapp.models.Order;
import com.brobono.samosawebapp.services.EmailService;
import com.brobono.samosawebapp.services.OrderService;

import static org.mockito.Mockito.*;

@WebMvcTest(CustomerController.class) // ✅ Loads only CustomerController
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test GET request for order form
    @Test
    void testShowOrderForm() throws Exception {
        mockMvc.perform(get("/order-form"))
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(view().name("order-form")) // Expect order-form view
                .andExpect(model().attributeExists("order")); // Model should have an 'order' attribute
    }

    // ✅ Test successful order submission
    @Test
    void testSubmitOrder_Success() throws Exception {
        Order order = new Order();
        order.setCustomerName("John Doe");
        order.setCustomerEmail("test@example.com");
        order.setOrderDetails("2x Samosas");

        when(orderService.saveOrder(any(Order.class))).thenReturn(order);
        doNothing().when(emailService).sendNewOrderEmail(anyString(), any(Order.class));

        mockMvc.perform(post("/submit-order")
                .flashAttr("order", order)) // ✅ Simulates a form submission
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(view().name("order-confirmation")) // Expect confirmation page
                .andExpect(model().attributeExists("successMessage")); // Success message should exist
    }

    // ✅ Test order submission with validation error (empty order)
    @Test
    void testSubmitOrder_ValidationError() throws Exception {
        Order order = new Order(); // Missing required fields

        mockMvc.perform(post("/submit-order")
                .flashAttr("order", order))
                .andExpect(status().isOk()) // Should stay on order-form page
                .andExpect(view().name("order-form")) // Expect form page to reload
                .andExpect(model().attributeExists("order")); // The invalid order should still be in the model
    }
}
