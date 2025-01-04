package com.brobono.samosawebapp.controllers;

import org.springframework.stereotype.Controller;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {
	// Serve the Order Form for Customers
	@GetMapping("/order-form")
	public String showOrderForm() {
		return "order-form"; // Maps to src/main/resources/templates/order-form.html
	}

	// Serve the Order Confirmation Page
	@GetMapping("/order-confirmation")
    public String showOrderConfirmation() {
        return "order-confirmation"; // Maps to src/main/resources/templates/order-confirmation.html
    }
}