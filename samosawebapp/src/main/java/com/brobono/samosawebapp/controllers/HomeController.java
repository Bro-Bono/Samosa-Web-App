package com.brobono.samosawebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
    @GetMapping("/")
    public String home() {
        return "index"; // Returns the index.html template
    }
}
