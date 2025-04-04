package com.brobono.samosawebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.brobono.samosawebapp.models.Order;
import com.brobono.samosawebapp.services.EmailService;
import com.brobono.samosawebapp.services.OrderService;

import jakarta.validation.Valid;

@Controller
public class CustomerController {
	
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private EmailService emailService;
    
	// Serve the Order Form for Customers
    @GetMapping("/order-form")
    public String showOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "order-form";
    }
	// Serve the Order Confirmation Page
    @PostMapping("/submit-order")
    public String submitOrder(@Valid @ModelAttribute("order") Order order, BindingResult result, Model model) {
    	if(result.hasErrors()) {
    		//redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
            return "order-form"; 
    	}
        //redirectAttributes.addFlashAttribute("success", "Order placed successfully!");
        orderService.saveOrder(order);
        emailService.sendNewOrderEmail(order.getCustomerEmail(), order);
        model.addAttribute("successMessage", "Order placed successfully!");
        return "order-confirmation";
    }
}