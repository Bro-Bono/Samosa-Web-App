package com.brobono.samosawebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.brobono.samosawebapp.services.OrderService;


@Controller
public class BusinessOwnerController {
	
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        int newOrderCount = orderService.getNewOrderCount();
        int inProgressCount = orderService.getInProgressOrderCount();
        int completedCount = orderService.getCompletedOrderCount();

        model.addAttribute("newOrderCount", newOrderCount);
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("completedCount", completedCount);

        return "dashboard"; // Thymeleaf template for the dashboard
    }
}
