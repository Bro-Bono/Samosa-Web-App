package com.brobono.samosawebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String submitOrder(@Valid @ModelAttribute("order") Order order, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
    	if(result.hasErrors()) {
    		// since this is returning a view, we use model attributes
            model.addAttribute("order", order);
            return "order-form"; 
    	}
        orderService.saveOrder(order);
        emailService.sendNewOrderEmail(order.getCustomerEmail(), order);
		// since this is returning a redirect, we use flash attributes
        redirectAttributes.addFlashAttribute("successMessage", "Order placed successfully!");
        return "redirect:/order-confirmation";
    }
    
    @GetMapping("/order-confirmation")
    public String showConfirmationPage() {
        return "order-confirmation";
    }
}