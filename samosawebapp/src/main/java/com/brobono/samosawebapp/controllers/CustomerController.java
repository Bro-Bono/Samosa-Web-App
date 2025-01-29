package com.brobono.samosawebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.brobono.samosawebapp.models.Order;
import com.brobono.samosawebapp.services.OrderService;

@Controller
public class CustomerController {
	
    @Autowired
    private OrderService orderService;
    
	// Serve the Order Form for Customers
    @GetMapping("/order-form")
    public String showOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "order-form";
    }
	// Serve the Order Confirmation Page
    @PostMapping("/submit-order")
    public String submitOrder(@ModelAttribute Order order) {
        orderService.saveOrder(order);
        return "order-confirmation";
    }
}