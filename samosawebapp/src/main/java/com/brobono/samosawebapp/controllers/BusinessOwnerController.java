package com.brobono.samosawebapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.brobono.samosawebapp.models.Order;
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
        
        List<Order> newOrders = orderService.getOrdersByStatus("NEW");
        List<Order> inProgressOrders = orderService.getOrdersByStatus("IN PROGRESS");
        List<Order> completedOrders = orderService.getOrdersByStatus("COMPLETED");

        model.addAttribute("newOrders", newOrders);
        model.addAttribute("inProgressOrders", inProgressOrders);
        model.addAttribute("completedOrders", completedOrders);
        

        return "dashboard"; // Thymeleaf template for the dashboard
    }
}
